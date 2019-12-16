package com.xkk.service;

import com.xkk.dao.BBSMapper;
import com.xkk.dao.UserMapper;
import com.xkk.pojo.Message;
import com.xkk.pojo.User;
import com.xkk.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BBSMapper bbsMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    BBSService bbsService;

    @Cacheable(value = "getUserByUserid")
    @Override
    public List<User> getUserByUserid(int userid) {

        return userMapper.getUserByUserid(userid);
    }

    @Cacheable(value = "getUserByUsername_Password")
    @Override
    public List<User> getUserByUsername_Password(User user) {
        return userMapper.getUserByUsername_Password(user);
    }

    @CacheEvict
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @CacheEvict(value = {"getUserByUserid","getUserByUsername_Password"},allEntries = true)
    @Override
    public int updateTouxiang(User user) {
        return userMapper.updateTouxiang(user);
    }

    @Cacheable(value = "ToMeMessage")
    @Override
    public List<Message> ToMeMessage(int userid) {
        //找到我被回复的消息==回复我的消息
        List<Message> messages = messageService.getMessagesByTouserid(userid);

        for(Message message : messages){
            message.setPost(bbsService.getPostByPostid(message.getPostid(),null).get(0));
            message.setFromuser(getUserByUserid(message.getFromuserid()).get(0));
            String content = StringUtil.delHtmlTag(message.getContent());
            message.setContent(content.substring(0,content.length()>10?10:content.length())+"...");
        }
//
//        List<Reply> replies = bbsMapper.getReplyByOtherUserid(userid);
//        List<Post> posts = bbsMapper.getPostByUserid(userid);
//        List<Message> messages = new ArrayList<>();
//        //建立帖子下 评论消息
//        for (Post post:posts) {
//            //找到我发的帖子下，别人的评论
//            List<Comment> comments = bbsMapper.getCommentByPostid(post.getPostid());
//            for (Comment comment:comments) {
//                if(comment.getUserid()==userid){
//                    continue;
//                    //自己回复自己的帖子，就不要显示了
//                }
//                Message message = new Message();
//                String content = StringUtil.delHtmlTag(comment.getContent());
//                message.setContent(content.substring(0,content.length()>10?10:content.length())+"...");
//                message.setPostid(comment.getPostid());
//                message.setPost(post);
//                message.setFromuserid(comment.getUserid());
//                message.setFromuser(getUserByUserid(comment.getUserid()).get(0));
//                message.setDatetime(comment.getDatetime());
//                messages.add(message);
//            }
//        }
//
//        for (Reply reply:replies) {
//            if(reply.getMy_userid() == userid){
//                continue;
//                //自己回复自己的帖子，就不要显示了
//            }
//            Message message = new Message();
//            String content = StringUtil.delHtmlTag(reply.getContent());
//            message.setContent(content.substring(0,content.length()>10?10:content.length())+"...");
//            message.setFromuserid(reply.getMy_userid());//别人的id
//            message.setFromuser(getUserByUserid(reply.getMy_userid()).get(0));
//            message.setPost(bbsMapper.getPostByPostid(reply.getPostid()).get(0));
//            message.setPostid(reply.getPostid());
//            message.setDatetime(reply.getDatetime());
//            messages.add(message);
//        }

        return messages;
    }


    //这里是查看 userid主动回复的消息
    @Cacheable(value = "ToActiveMeMessage")
    @Override
    public List<Message> ToActiveMeMessage(int userid) {

        List<Message> messages = messageService.getMessagesByFromuserid(userid);

        for(Message message : messages){
            message.setPost(bbsService.getPostByPostid(message.getPostid(),null).get(0));
            message.setFromuser(getUserByUserid(userid).get(0));
            String content = StringUtil.delHtmlTag(message.getContent());
            message.setContent(content.substring(0,content.length()>10?10:content.length())+"...");
        }
//
//        //找到我被回复的消息==回复我的消息
//        List<Reply> replies = bbsMapper.getReplyByMyUserid(userid);
//        List<Post> posts = bbsMapper.getPostByUserid(userid);
//        List<Comment> comments = bbsMapper.getCommentByUserid(userid);
//        List<Message> messages = new ArrayList<>();
//        //建立所有帖子的消息
//        for (Post post:posts) {
//            Message message = new Message();
////            post = bbsMapper.getPostByPostid(post.getPostid()).get(0);
//            message.setDatetime(post.getDatetime());
//            message.setPostid(post.getPostid());
//            message.setPost(post);
//            message.setFromuser(getUserByUserid(userid).get(0));
//            message.setFromuserid(userid);
//            String content = StringUtil.delHtmlTag(post.getContent());
//            message.setContent(content.substring(0,content.length()>10?10:content.length())+"...");
////            System.out.println(post.getContent());
//            messages.add(message);
//        }
//
//        for (Comment comment :comments){
//            Message message = new Message();
//            message.setDatetime(comment.getDatetime());
//            message.setPostid(comment.getPostid());
//            message.setPost(bbsMapper.getPostByPostid(comment.getPostid()).get(0));
//            message.setFromuser(getUserByUserid(userid).get(0));
//            message.setFromuserid(userid);
//
//            String content = StringUtil.delHtmlTag(comment.getContent());
//            message.setContent(content.substring(0,content.length()>10?10:content.length())+"...");
////            message.setContent(StringUtil.delHtmlTag(comment.getContent()));
//            messages.add(message);
//        }
//
//
//        for (Reply reply:replies) {
//            Message message = new Message();
//
//            String content = StringUtil.delHtmlTag(reply.getContent());
//            message.setContent(content.substring(0,content.length()>10?10:content.length())+"...");
////            message.setContent(StringUtil.delHtmlTag(reply.getContent()).substring(0,StringUtil.delHtmlTag(reply.getContent()).length()>12?12:StringUtil.delHtmlTag(reply.getContent()).length())+"...");
//            message.setFromuserid(reply.getMy_userid());//别人的id
//            message.setFromuser(getUserByUserid(reply.getMy_userid()).get(0));
//            message.setPost(bbsMapper.getPostByPostid(reply.getPostid()).get(0));
//            message.setPostid(reply.getPostid());
//            message.setDatetime(reply.getDatetime());
//            messages.add(message);
//        }
        return messages;
    }

    @Cacheable(value = "getUserByUsername")
    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }


}
