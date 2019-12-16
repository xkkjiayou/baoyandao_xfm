package com.xkk.util;

import com.xkk.pojo.Post;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListUtil {
    public static List<Post> desc_post_by_comment_nums(List<Post> posts){
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                // 按照浏览的数量v + 评论的数量h 进行降序排列
                int o1vh = o1.getComment_nums() + o1.getViewnums();
                int o2vh = o2.getComment_nums() + o2.getViewnums();
                if (o1vh > o2vh) {
                    return -1; //升就1
                }
                if (o1vh == o2vh) {
                    return 0;
                }
                return 1;
            }
        });
        return posts;
    }

    public static List<Post> asc_post_by_comment_nums(List<Post> posts){
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                // 按照浏览的数量v + 评论的数量h 进行生序排列
                int o1vh = o1.getComment_nums() + o1.getViewnums();
                int o2vh = o2.getComment_nums() + o2.getViewnums();
                if (o1vh > o2vh) {
                    return 1; //升就1
                }
                if (o1vh == o2vh) {
                    return 0;
                }
                return -1;
            }
        });
        return posts;
    }
}
