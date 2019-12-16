package com.xkk.service;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Splitter;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.xkk.dao.BBSMapper;
import com.xkk.dao.UserMapper;
import com.xkk.pojo.*;
import com.xkk.util.RecommendUtil;
import com.xkk.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@CacheConfig(cacheNames = "bbsCache")
public class BBSServiceImpl implements BBSService {
    @Autowired
    BBSMapper bbsMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    RecordService recordService;

    @Autowired
    MessageService messageService;

    @Cacheable(value = "getPostByUserid")
    @Override
    public List<Post> getPostByUserid(int userid) {
        //更新帖子最后被回复的时间
        Date date = new Date();
        long nowtime = date.getTime();
        List<Post> posts = bbsMapper.getPostByUserid(userid);
        for (Post post:posts) {
            post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
            post.setComment_nums(countComment(post.getPostid()));
            if (post.getLast_reply_datetime()!=null)
                post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
        }
        return posts;
    }
    @Cacheable(value = "getPostByAll")
    @Override
    public List<Post> getPostByAll(int page_start,int nums) {
        //更新帖子最后被回复的时间
        Date date = new Date();
        long nowtime = date.getTime();
        if(page_start==-1&&nums==-1){
            //加载热门帖子,不需要分页
            List<Post> posts = bbsMapper.getPostByAll();
            for (Post post:posts) {
                post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
                //post.setComment_nums(countComment(post.getPostid()));
                if (post.getLast_reply_datetime()!=null)
                    post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
            }
            return posts;
        }

        PageHelper.startPage(page_start,nums);
        List<Post> posts = bbsMapper.getPostByAll();
        for (Post post:posts) {
            post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
//            post.setComment_nums(countComment(post.getPostid()));
            if (post.getLast_reply_datetime()!=null)
                post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
        }
        return posts;
    }


    @Cacheable(value = "getPostByKeyword")
    @Override
    public List<Post> getPostByKeyword(int page_start,int nums,String keyword) {
        //更新帖子最后被回复的时间
        Date date = new Date();
        long nowtime = date.getTime();
        if(page_start==-1&&nums==-1){
            //加载热门帖子,不需要分页
            List<Post> posts = bbsMapper.getPostByKeyword(keyword);
            for (Post post:posts) {
                post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
                post.setComment_nums(countComment(post.getPostid()));
                if (post.getLast_reply_datetime()!=null)
                    post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
            }
            return posts;
        }

        PageHelper.startPage(page_start,nums);
        List<Post> posts = bbsMapper.getPostByKeyword(keyword);
        for (Post post:posts) {
            post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
            post.setComment_nums(countComment(post.getPostid()));
            if (post.getLast_reply_datetime()!=null)
                post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
        }
        return posts;
    }

    @Cacheable(value = "getPostByKeywordAndTypeid")
    @Override
    public List<Post> getPostByKeywordAndTypeid(int page_start,int nums, String keyword, int typeid) {
        //更新帖子最后被回复的时间
        Date date = new Date();
        long nowtime = date.getTime();
        if(page_start==-1&&nums==-1){
            //加载热门帖子,不需要分页
            List<Post> posts = bbsMapper.getPostByKeywordAndTypeid(keyword,typeid);
            for (Post post:posts) {
                post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
                post.setComment_nums(countComment(post.getPostid()));
                post.setType(bbsMapper.getTypeByTypeid(post.getTypeid()));
                if (post.getLast_reply_datetime()!=null)
                    post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
            }
            return posts;
        }

        PageHelper.startPage(page_start,nums);
        List<Post> posts = bbsMapper.getPostByKeywordAndTypeid(keyword,typeid);
        for (Post post:posts) {
            post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
            post.setComment_nums(countComment(post.getPostid()));
            post.setType(bbsMapper.getTypeByTypeid(post.getTypeid()));
            if (post.getLast_reply_datetime()!=null)
                post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
        }
        return posts;
    }

    @Cacheable(value = "getTypeByIdRange")
    @Override
    public List<Type> getTypeByIdRange(int st, int gt) {
        return bbsMapper.getTypeByIdRange(st,gt);
    }

    @Cacheable(value = "getALLTitleForSuggestSearch")
    @Override
    public List<String> getALLTitleForSuggestSearch() {
        return bbsMapper.getALLTitleForSuggestSearch();
    }


    @Cacheable(value = "getPostByPostid")
    @Override
    public List<Post> getPostByPostid(int postid, User user) {
        //更新帖子最后被回复的时间
        System.out.println("拿到帖子的ID"+postid);
        Date date = new Date();
        long nowtime = date.getTime();
        List<Post> posts = bbsMapper.getPostByPostid(postid);
        recordService.updatePostRecordNums(postid);
        for (Post post:posts) {
//            try {
                post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
//            }catch (Exception e){
//                System.out.println(post.getUserid());
//                e.printStackTrace();
//                continue;
//            }
//            post.setComment_nums(countComment(post.getPostid()));
                post.setType(bbsMapper.getTypeByTypeid(post.getTypeid()));
            if(user!=null){
                post.setLiked(recordService.getLikeByBelikedidAndUseridAndSource(postid,user.getUserid(),"post")==null?false:true);
            }
            if (post.getLast_reply_datetime()!=null)
                post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
        }
        return posts;
    }

    @Cacheable(value = "getPostByTypeid")
    @Override
    public List<Post> getPostByTypeid(int typeid, int page_start, int nums) {
        //更新帖子最后被回复的时间
        Date date = new Date();
        long nowtime = date.getTime();
        if(page_start==-1&&nums==-1){
            //加载热门帖子,不需要分页
            List<Post> posts = bbsMapper.getPostByTypeid(typeid);
            for (Post post:posts) {
                post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));

//                post.setComment_nums(countComment(post.getPostid()));

                //提取摘要
                if(post.getKeyword_seg()!=null ){
                    List<String> keywords = Splitter.on(",").trimResults().splitToList(post.getKeyword_seg());
                    post.setRaw_content(new ArrayList<>(keywords.subList(0,Math.min(8,keywords.size()))));
                }
//                post.setRaw_content(extractWordUniversity(StringUtil.delHtmlTag(post.getContent())+post.getTitle()));
                if (post.getLast_reply_datetime()!=null)
                    post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
            }
            return posts;
        }

        PageHelper.startPage(page_start,nums);
        List<Post> posts = bbsMapper.getPostByTypeid(typeid);
        for (Post post:posts) {
            post.setUser(userMapper.getUserByUserid(post.getUserid()).get(0));
//            post.setComment_nums(countComment(post.getPostid()));
            //提取摘要
            if(post.getKeyword_seg()!=null ){
                List<String> keywords = Splitter.on(",").trimResults().splitToList(post.getKeyword_seg());
                post.setRaw_content(new ArrayList<>(keywords.subList(0,Math.min(8,keywords.size()))));
            }
//            post.setRaw_content(extractWordUniversity(StringUtil.delHtmlTag(post.getContent())+post.getTitle()));
            if (post.getLast_reply_datetime()!=null)
                post.setHowlong_last_reply(TimeUtil.cal_howlong_last_reply(nowtime,post.getLast_reply_datetime().getTime()));
        }
        return posts;
    }

    public List<String> extractWordUniversity(String s )
    {
        List<String> u_word_list = new ArrayList<>();
        Set<String> u_word_set = new HashSet<>();
//        System.out.println(content_All.size());
        Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);
        List<Term> termList = StandardTokenizer.segment(s);
        for (Term t : termList) {
//                System.out.println(t.word+"----"+t.nature);
//                System.out.println(t.nature);
            if (t.nature == Nature.ntu || t.nature == Nature.nt ) {
//                    System.out.println(t.word+"----"+t.nature);
                u_word_set.add(t.word);
            }
        }
        u_word_list.addAll(u_word_set);
        return u_word_list;
    }


    @CacheEvict(value = {"getPostByUserid","getALLPostidForWordSeg","getPostByAll","getPostByKeyword","getPostByKeywordAndTypeid","getPostByTypeid","getAllContent"},allEntries=true)
    @Override
    public int addPost(Post post) {
        if(post.getTypeid()==21)//导师交流中，完全匿名
        {
            post.setUserid(-1);
        }
        return bbsMapper.addPost(post);
    }

    @Cacheable(value = "getCommentByPostid")
    @Override
    public List<Comment> getCommentByPostid(int postid,int page_start,int nums,User user) {
        PageHelper.startPage(page_start,nums);
        List<Comment> comments = bbsMapper.getCommentByPostid(postid);
        for (Comment comment:comments) {
            comment.setUser(userMapper.getUserByUserid(comment.getUserid()).get(0));
//            System.out.println(userMapper.getUserByUserid(comment.getUserid()).get(0).getNickname());
//            System.out.println(comment.getCommentid());
            List<Reply> replies = getReplyByCommentid(comment.getCommentid(),user);
            comment.setReply_nums(replies.size());
            comment.setReplies(replies);
            if(user!=null){
                comment.setLiked(recordService.getLikeByBelikedidAndUseridAndSource(comment.getCommentid(),user.getUserid(),"comment")==null?false:true);
            }
        }
        return comments;
    }
    @CacheEvict(value = {"getCommentByPostid","getALLPostidForWordSeg","getPostByPostid"},allEntries=true)
    @Override
    public int addComment(Comment comment) {
        //21类别【导师评价】下：让用户为-1，userid为-1时 表示匿名，所以有一个nickname为匿名的 用户 userid为-1
        Post post = getPostByPostid(comment.getPostid(),null).get(0);
        if(post.getTypeid()==21)//导师交流中，完全匿名
        {
            comment.setUserid(-1);
        }
        //发送消息
        Message message = new Message();
        message.setContent(comment.getContent());
        message.setTouserid(post.getUserid());
        message.setFromuserid(comment.getUserid());
        message.setPostid(comment.getPostid());
        messageService.addMessageByMessage(message);

        //重复评论+10
        recordService.updateScoreNums(comment.getPostid(),comment.getUserid(), RecommendUtil.SCORE_COMMENT);
        comment.setUser(userMapper.getUserByUserid(comment.getUserid()).get(0));
        emailService.sendEmail(post.getUser().getEmail(),"【小飞猫保研网】您的帖子有一条新回复！",comment.getContent(),comment.getUser(),post);
        //更新帖子最后被回复的时间
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        bbsMapper.updateLast_reply_datetimeByPostid(timeStamp,comment.getPostid());
        return bbsMapper.addComment(comment);
    }

    @Cacheable(value = "getCommentByPostid")
    @Override
    public int getPostCountByTypeid(int typeid) {
        return bbsMapper.getPostCountByTypeid(typeid);
    }

    @Cacheable(value = "getPostCountByTypeidAndKeyword")
    @Override
    public int getPostCountByTypeidAndKeyword(int typeid, String keyword) {
        return bbsMapper.getPostCountByTypeidAndKeyword(typeid,keyword);
    }

    @Cacheable(value = "getTypePostCount")
    @Override
    public List<Type> getTypePostCount(String keyword) {
        return bbsMapper.getTypePostCount(keyword);
    }

    @CacheEvict(value = {"getCommentByPostid","getALLPostidForWordSeg","getReplyByCommentid","getPostByPostid"},allEntries=true)
    @Override
    public int addReply(Reply reply) {
        //21类别【导师评价】下：让用户为-1，userid为-1时 表示匿名，所以有一个nickname为匿名的 用户 userid为-1
        Post post = getPostByPostid(reply.getPostid(),null).get(0);
        if(post.getTypeid()==21)//导师交流中，完全匿名
        {
            reply.setOther_userid(-1);
            reply.setMy_userid(-1);
        }
        reply.setOther_user(userMapper.getUserByUserid(reply.getOther_userid()).get(0));
        reply.setMy_user(userMapper.getUserByUserid(reply.getMy_userid()).get(0));

        Message message = new Message();
        message.setContent(reply.getContent());
        message.setTouserid(reply.getOther_userid());
        message.setFromuserid(reply.getMy_userid());
        message.setPostid(reply.getPostid());
        messageService.addMessageByMessage(message);
        //回复+5
        recordService.updateScoreNums(reply.getPostid(),reply.getMy_userid(), RecommendUtil.SCORE_REPLY);
        emailService.sendEmail(post.getUser().getEmail(),"【小飞猫保研网】您的帖子有一条新回复！",reply.getContent(),reply.getMy_user(),post);
        emailService.sendEmail(reply.getOther_user().getEmail(),"【小飞猫保研网】您的评论有一条新回复！",reply.getContent(),reply.getMy_user(),post);

        //更新帖子最后被回复的时间
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        bbsMapper.updateLast_reply_datetimeByPostid(timeStamp,reply.getPostid());
        return bbsMapper.addReply(reply);
    }

    @Cacheable(value = "getReplyByCommentid")
    @Override
    public List<Reply> getReplyByCommentid(int commentid,User user) {
        List<Reply> replies = bbsMapper.getReplyByCommentid(commentid);
        for (Reply reply:replies) {
            reply.setOther_user(userMapper.getUserByUserid(reply.getOther_userid()).get(0));
//            System.out.println(reply.getOther_userid());
//            System.out.println(userMapper.getUserByUserid(reply.getMy_userid()).get(0).getNickname());
//            System.out.println(userMapper.getUserByUserid(reply.getOther_userid()).get(0).getNickname());
            reply.setMy_user(userMapper.getUserByUserid(reply.getMy_userid()).get(0));
            if(user!=null){
                reply.setLiked(recordService.getLikeByBelikedidAndUseridAndSource(reply.getReplyid(),user.getUserid(),"reply")==null?false:true);
            }
//            System.out.println(userMapper.getUserByUserid(comment.getUserid()).get(0).getNickname());
        }
        return replies;
    }

    @Cacheable(value = "countComment")
    @Override
    public int countComment(int postid) {
        return bbsMapper.countComment(postid);
    }

    @Cacheable(value = "getPostCount")
    @Override
    public int getPostCount() {
        return bbsMapper.getPostCount();
    }

    @Cacheable(value = "getPostCountByKeyword")
    @Override
    public int getPostCountByKeyword(String keyword) {
        return bbsMapper.getPostCountByKeyword(keyword);
    }
}
