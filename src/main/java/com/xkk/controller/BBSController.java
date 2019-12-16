package com.xkk.controller;

import com.xkk.pojo.*;
import com.xkk.service.*;
import com.xkk.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BBSController {

    @Autowired
    BBSService bbsService;
    @Autowired
    RecordService recordService;
    @Autowired
    RecommendService recommendService;
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @GetMapping(value = {"/bbs/{page}/{nums}/{cur_typeid}","/bbs"} )
    public String bbs(@PathVariable(name="page", required = false) Integer page,
                      @PathVariable(name="nums", required = false) Integer nums,
                      @PathVariable(name="cur_typeid", required = false) Integer cur_typeid,
                      HttpSession session,
                      ModelMap map){
        //Integet是类型,可以有空指针NULL,int不行 int是基本类型 没有null
        page = page==null?1:page;
        nums = nums==null?1:nums;
        cur_typeid = cur_typeid==null?1:cur_typeid;

        nums = PageVariableUtil.NUMS;
        List<Post> posts = bbsService.getPostByTypeid(cur_typeid,page,nums);
//        List<Type> types = bbsService.getTypeByIdRange(0,50);//bbs可以用来展示0-50的话题
        map.put("posts", posts);
//        map.put("types", types);//所有的话题列表

        //这里不需要展示全部，只做分类展示即可
        List<Type> types = bbsService.getTypePostCount("%");
        map.put("types", types);//所有的话题列表



        map.put("cur_typeid", cur_typeid);//当前是哪个话题
        //帖子总数
        int count = bbsService.getPostCountByTypeid(cur_typeid);
        int pagetotal = count%nums==0?count/nums:count/nums+1;
        List<Integer> page_indexes = PageVariableUtil.Get_Page_indexes(page,pagetotal);
        map.put("page_indexes", page_indexes);
        map.put("page_start", page);//当前页

        //推荐算法部分
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user!=null){
            List<Post> recommends = recommendService.getRecommendsByUserid(user.getUserid());
            if(recommends.size()>0)
            {
                map.put("recommend_posts", recommends);
            }
        }
        List<String> hot_words = recommendService.getAllHotword();
        map.put("hot_words",hot_words);
        //拿到所有的帖子进行评论排序,热门帖子双-1
        List<Post> hot_posts = bbsService.getPostByAll(-1,-1);
        hot_posts = ListUtil.desc_post_by_comment_nums(hot_posts);
        map.put("hot_posts",hot_posts);
        return "bbs";
    }

    @GetMapping(value = {"/post/{postid}/{page}/{nums}","/post/{postid}","/post/{postid}/{messageid}"})
    public String post_detail(@PathVariable(name="postid", required = false) int postid,
                              @PathVariable(name="messageid", required = false) Integer messageid,
                              ModelMap map,
                              HttpSession session,
                              @PathVariable(name="page", required = false) Integer page,
                              @PathVariable(name="nums", required = false) Integer nums){
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(messageid!=null){
            //只对单条消息进行更新，而不是对一个帖子下的所有消息进行更新
            messageService.updateMessageStateByMessageid(messageid);
            if(user!=null){
                user = userService.getUserByUserid(user.getUserid()).get(0);
                int count = messageService.getMessageCountByTouserid(user.getUserid());
                user.setMessage_nums(count);
                session.setAttribute(SessionUtil.USER_SESSION_KRY,user);
            }
        }
        page = page==null?1:page;
        nums = nums==null?1:nums;
        nums = PageVariableUtil.NUMS;

        List<Post> posts = bbsService.getPostByPostid(postid,user);
        List<Comment> comments = bbsService.getCommentByPostid(postid,page,nums,user);
        //拿到所有的帖子进行评论排序,热门帖子双-1
        int count = bbsService.countComment(postid);
        int pagetotal = count%nums==0?count/nums:count/nums+1;
        List<Integer> page_indexes = PageVariableUtil.Get_Page_indexes(page,pagetotal);
        map.put("page_indexes", page_indexes);
        map.put("page_start", page);//当前页

        List<Post> hot_posts = bbsService.getPostByAll(-1,-1);
        hot_posts = ListUtil.desc_post_by_comment_nums(hot_posts);
        map.put("hot_posts",hot_posts);
        Post post = posts.get(0);
        post.setComments(comments);
        map.put("post",post);
        List<String> hot_words = recommendService.getAllHotword();
        map.put("hot_words",hot_words);

//        进行数据记录--记录帖子的浏览行为
//        recordService.updatePostRecordNums(postid);
        if(user!=null){
            recordService.addPostRecord(postid,user.getUserid());
            List<Post> recommends = recommendService.getRecommendsByUserid(user.getUserid());
            if(recommends.size()>0)
            {
                map.put("recommend_posts", recommends);
            }
        }else{
            recordService.updatePostRecordNums(postid);
        }

        return "post";
    }

    @RequestMapping(value="/bbs_publish",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject publish(@RequestParam("publish_title") String publish_title,
                              @RequestParam("publish_content") String publish_content,//html的内容
                              @RequestParam("type_id") int type_id,
                              HttpSession session) {
        Map<String,String> map=new HashMap<String, String>();
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            map.put("status", "error");
            map.put("message", "用户未登录");
            map.put("error_message","小同学，发帖前要登录哦！");
            JSONObject json = JSONObject.fromObject(map);
            return json;
        }
        Post post = new Post();
        post.setTypeid(type_id);
        post.setContent(publish_content);
        post.setTitle(publish_title);
        post.setUserid(user.getUserid());
        bbsService.addPost(post);
        map.put("status", "success");
        map.put("message", ""+post.getPostid());
        JSONObject json = JSONObject.fromObject(map);
//        System.out.println(json.toString());
        return json;
    }

//    发表评论
    @RequestMapping(value="/bbs_comment",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject comment(@RequestParam("postid") int postid, @RequestParam("comment_content") String comment_content, HttpSession session) {
        Map<String,String> map=new HashMap<String, String>();
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            map.put("status", "erroe");
            map.put("message", "用户未登录");
            map.put("error_message","小同学，讨论前要登录哦！");
            JSONObject json = JSONObject.fromObject(map);
            return json;
        }
        Comment comment = new Comment();
        comment.setContent(comment_content);
        comment.setPostid(postid);
        comment.setUserid(user.getUserid());
        bbsService.addComment(comment);
        System.out.println("----------------==============");
        map.put("status", "success");
        map.put("message", ""+comment.getCommentid());
        JSONObject json = JSONObject.fromObject(map);
//        System.out.println(json.toString());
        return json;
    }

    //回复评论
    @RequestMapping(value="/bbs_reply",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject reply(@RequestParam("postid") int postid,
                            @RequestParam("reply_content") String reply_content,
                            @RequestParam("other_userid") int other_userid,
                            @RequestParam("commentid") int commentid,
                            HttpSession session) {
        Map<String,String> map=new HashMap<String, String>();
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            map.put("status", "error");
            map.put("message", "用户未登录");
            map.put("error_message","小同学，回复前要登录哦！");
            JSONObject json = JSONObject.fromObject(map);
            return json;
        }
        Reply reply = new Reply();
        reply.setCommentid(commentid);
        reply.setPostid(postid);
        reply.setOther_userid(other_userid);
        reply.setMy_userid(user.getUserid());
        reply.setMy_user(user);
        reply.setOther_user(userService.getUserByUserid(reply.getOther_userid()).get(0));
        reply.setContent(reply_content);
        bbsService.addReply(reply);
        int new_replyid = reply.getReplyid();
        map.put("status", "success");

        String json_ajax_append = "\n" +
                "                                            <!--回复item-->\n" +
                "                                            <div id=\"mao_commentid_"+reply.getCommentid()+"_replyid_"+new_replyid+  "\" class=\"reply-box js-sub-cmt-list\" style=\"\"  >\n" +
                "                                                <div id=\"jsCpn_51_component_3\" class=\" \">\n" +
                "                                                    <div class=\"js-tips\" style=\"display: none;\"></div>\n" +
                "                                                    <div class=\"js-content\" style=\"\">\n" +
                "                                                        <div class=\"reply-list js-list\">\n" +
                "                                                            <div id=\"jsCpn_110_component_0\" class=\" reply-list-item js-copy-mark\" >\n" +
                "                                                                <div class=\"reply-main clearfix\">\n" +
                "                                                                    <div class=\"reply-person\" style=\"margin-right:5px;\">\n" +
                "                                                                        <a href=\"/profile/63\" target=\"_blank\" data-card-uid=\"63\" class=\"js-copy-tip level-color-8\" data-card-index=\"54\">\n" +
                "                                                                            <span>\n" +
                "                                                                                <a href=\"/view_user_info/"+reply.getMy_userid()+"\"><span >"+reply.getMy_user().getNickname()+"</span></a>\n" +
                "                                                                                回复\n" +
                "                                                                                <a href=\"/view_user_info/"+reply.getOther_userid()+"\"><span >"+reply.getOther_user().getNickname()+"</span></a>\n" +
                "                                                                                <!--                                                                               加楼主标识-->\n" +
                "                                                                                :\n" +
                "                                                                            </span>\n" +
                "                                                                        </a>\n" +
                "                                                                    </div>\n" +
                "                                                                    <div class=\"reply-content\">\n" +
                "                                                                        <p >"+reply_content+"</p>\n" +
                "                                                                    </div>\n" +
                "                                                                </div>\n" +
                "                                                                <div class=\"answer-legend reply-info js-sub-action\">\n" +
                "                                                                    <span class=\"reply-time\" >刚刚回复</span>\n" +
                "                                                                    <a \n" +
                "                                                                           data-toggle=\"collapse\"\n" +
                "                                                                           href=\"#collapse_commentid_"+reply.getCommentid()+"_replyid_"+reply.getReplyid()+"\"  class=\"reply-answer js-cmt-action\" >回复</a>\n" +
                "                                                                </div>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "<!--                                                回复编辑框-->\n" +
                "                                                <div class=\"js-reply-box  panel-collapse collapse\"  " +
                " id=\"collapse_commentid_"+reply.getCommentid()+"_replyid_"+new_replyid+  "\" >\n" +
                "                                                    <div id=\"jsCpn_52_component_0\" class=\" reply-editbox clearfix\" style=\"margin-top:10px;\">\n" +
                "                                                        <form class=\"subscribe-wthree pt-2\">\n" +
                "                                                            <div class=\"d-flex subscribe-wthree-field\" >\n" +
                "                                                                <input id=\"input_commentid_"+reply.getCommentid()+"_replyid_"+new_replyid+"\" class=\"form-control\" type=\"text\" placeholder=\"发表你的看法\" name=\"reply_content\" required=\"\">\n" +
                "                                                                <button class=\"btn form-control\" style=\"width: 20%;\" type=\"button\" id=\"reply\" onclick=\"f_reply("+reply.getCommentid()+","+reply.getMy_userid()+","+new_replyid+")\">回复</button>\n" +
                "                                                            </div>\n" +
                "                                                        </form>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </div>";


        map.put("message", json_ajax_append);
        map.put("new_replyid", Integer.toString(new_replyid));
        JSONObject json = JSONObject.fromObject(map);
//        System.out.println(json.toString());
        return json;
    }


    //搜索
    @GetMapping(value = {"/bbs_search/{page}/{nums}/{cur_typeid}","/bbs_search","/bbs_search/{cur_typeid}"} )
    public String bbs_search(@PathVariable(name="page", required = false) Integer page,
                             @PathVariable(name="nums", required = false) Integer nums,
                             @PathVariable(name="cur_typeid", required = false) Integer cur_typeid,
                             ModelMap map,
                             HttpSession session,
                             @RequestParam("keyword") String keyword){
        //Integet是类型,可以有空指针NULL,int不行 int是基本类型 没有null
        page = page==null?1:page;
        nums = nums==null?1:nums;
        nums = PageVariableUtil.NUMS;
        cur_typeid = cur_typeid==null?-1:cur_typeid;//-1 表示搜索全部帖子，再全部tab下
        //搜索全部帖子
        if(cur_typeid==-1) {
            List<Post> posts = bbsService.getPostByKeyword(page, nums, keyword);
            map.put("posts", posts);
            //帖子总数
            int count = bbsService.getPostCountByKeyword(keyword);
            int pagetotal = count % nums == 0 ? count / nums : count / nums + 1;
            List<Type> types = bbsService.getTypePostCount(keyword);
            //加一个全部分组
            Type type = new Type();
            type.setTypename("全部");
            type.setTypeid(-1);
            int total_nums = 0;
            for (Type type1:types){
                total_nums+=type1.getNums();
            }
            type.setNums(total_nums);
            types.add(type);
            //结束加一个全部分组
            map.put("types", types);//所有的话题列表
            List<Integer> page_indexes = PageVariableUtil.Get_Page_indexes(page, pagetotal);
            map.put("page_indexes", page_indexes);
            map.put("page_start", page);//当前页
            map.put("cur_typeid", -1);//当前是哪个话题
        }
        //固定主题搜索帖子
        if(cur_typeid!=-1) {
            List<Post> posts = bbsService.getPostByKeywordAndTypeid(page, nums, keyword,cur_typeid);
            map.put("posts", posts);
            //帖子总数
            int count = bbsService.getPostCountByTypeidAndKeyword(cur_typeid,keyword);
            int pagetotal = count % nums == 0 ? count / nums : count / nums + 1;
            List<Integer> page_indexes = PageVariableUtil.Get_Page_indexes(page, pagetotal);
            //加一个全部分组
            List<Type> types = bbsService.getTypePostCount(keyword);
            Type type = new Type();
            type.setTypename("全部");
            type.setTypeid(-1);
            int total_nums = 0;
            for (Type type1:types){
                total_nums+=type1.getNums();
            }
            type.setNums(total_nums);
            types.add(type);
            map.put("types", types);//所有的话题列表
            //结束加一个全部分组
            map.put("page_indexes", page_indexes);
            map.put("page_start", page);//当前页
            map.put("cur_typeid", cur_typeid);//当前是哪个话题
        }

        //推荐算法部分
        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user!=null){
            List<Post> recommends = recommendService.getRecommendsByUserid(user.getUserid());
            if(recommends.size()>0)
            {
                map.put("recommend_posts", recommends);
            }
        }
        //热词推荐
        List<String> hot_words = recommendService.getAllHotword();
        map.put("hot_words",hot_words);
        //拿到所有的帖子进行评论排序,热门帖子双-1
        List<Post> hot_posts = bbsService.getPostByAll(-1,-1);
        hot_posts = ListUtil.desc_post_by_comment_nums(hot_posts);
        map.put("hot_posts",hot_posts);
        map.put("keyword",keyword);
        return "bbs_search";
    }

    //点赞接口
    @RequestMapping(value="/update_like",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update_like(@RequestParam("like_source") String like_source,//点赞的来源，是post 还是reply 还是comment
                              @RequestParam("like_id") int like_id,
                              HttpSession session,
                              ModelMap map
                              ){

        User user = (User)session.getAttribute(SessionUtil.USER_SESSION_KRY);
        if(user==null){
            map.put("status", "error");
            map.put("message", "用户未登录");
            map.put("error_message","小同学，点赞前要登录哦！");
            JSONObject json = JSONObject.fromObject(map);
            return json;
        }

        String tbl_name = "";
        String tbl_id_name = "";
        switch (like_source){
            case "reply":tbl_name="tbl_reply";tbl_id_name="replyid";break;
            case "comment":tbl_name="tbl_comment";tbl_id_name="commentid";break;
            case "post":tbl_name="tbl_post";tbl_id_name="postid";break;
            default:
                map.put("status", "error");
                map.put("message", "小同学，你当前的操作不在点赞范围内");
                JSONObject json = JSONObject.fromObject(map);
                return json;
        }
        int rs = recordService.updateLike(tbl_name,tbl_id_name,like_id,user.getUserid());
        if(rs>0){

            map.put("status", "success");
            map.put("message", "点赞成功");
            JSONObject json = JSONObject.fromObject(map);
            return json;
        }else{
            map.put("status", "error");
            map.put("message", "已点赞");
            JSONObject json = JSONObject.fromObject(map);
            return json;
        }

    }


    //    发表评论
    @RequestMapping(value="/getsuggestsearch",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject getsuggestsearch(@RequestParam("keyword") String keyword, HttpSession session) {
        Map<String,List<String>> map=new HashMap<String, List<String>>();
        List<String> s = RecommendUtil.suggester.suggest(keyword,5);
//        map.put("status", "success");
        map.put("suggestsearch", s);
        JSONObject json = JSONObject.fromObject(map);
//        System.out.println(json.toString());
        return json;
    }


}