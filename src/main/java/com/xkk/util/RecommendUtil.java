package com.xkk.util;

import com.google.common.base.Joiner;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.xkk.pojo.Post;
import com.xkk.pojo.Recommend;
import com.xkk.service.BBSService;
import com.xkk.service.RecommendService;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@EnableScheduling
public class  RecommendUtil {

    public static final int SCORE_VIEW = 1;
    public static final int SCORE_COMMENT = 10;
    public static final int SCORE_REPLY = 5;
    public static final int SCORE_LIKE = 3;

    @Autowired
    BBSService bbsService;

    @Autowired
    RecommendService recommendService;


    public static Suggester suggester = new Suggester();
    @Scheduled(fixedRate = 6000000)
    public void SuggestSearch()
    {

        List<String> titleArray = bbsService.getALLTitleForSuggestSearch();
//        suggester.removeAllSentences();

        for (String title : titleArray)
        {
            suggester.addSentence(title);
        }

//        System.out.println(suggester.suggest("发言", 5));       // 语义
//        System.out.println(suggester.suggest("危机公共", 1));   // 字符
//        System.out.println(suggester.suggest("mayun", 1));      // 拼音
    }

    //论坛推荐
    //上一次完成后，后5s进行下一次
    @Scheduled(fixedRate = 5000)
    public void getRecommendForPost() {
        try {
            System.out.println("帖子-推荐算法开始运行");
            int NEIGHBOORHOODNUM = 10;
            int RECOMMENDNUM = 20;
            String server = "212.64.58.87";
            //权重计算使用数据库中的值，也可以使用文件形式
            MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setServerName(server);
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            dataSource.setDatabaseName("baoyandao");
            dataSource.setUseSSL(false);
            dataSource.setServerTimezone("Asia/Shanghai");
            DataModel model = new MySQLJDBCDataModel(dataSource, "tbl_post_view_record", "userid", "postid", "score", "datetime");
//            //基于物品的推荐
//            ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
//            Recommender recommender = new GenericItemBasedRecommender(model,
//                     similarity);
            //基于SVD分类的推荐
            Recommender recommender = new SVDRecommender(model,
                    new ALSWRFactorizer(model, 10, 0.75, 10));
//
//            // 指定用户相似度计算方法，这里采用皮尔森相关度
//            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//            // 指定用户邻居数量，这里为10
//            UserNeighborhood neighborhood = new NearestNUserNeighborhood(NEIGHBOORHOODNUM, similarity, model);
//            // 构建基于用户的推荐系统
//            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
//            // 得到指定用户的推荐结果，这里是得到用户1的两个推荐
            LongPrimitiveIterator iterator=model.getUserIDs();
            //推荐表清空
//            recommendService.truncateRecommend();
            while(iterator.hasNext()) {
                long uid=iterator.nextLong();
//                System.out.println(uid);
                List<RecommendedItem> list=recommender.recommend(uid, RECOMMENDNUM);
//                System.out.println(list.size());
                for(RecommendedItem item:list) {
//                    System.out.println(item.getValue()+"--"+item.getItemID());
                    Recommend recommend = new Recommend();
                    recommend.setPostid((int)(item.getItemID()));
                    recommend.setUserid((int) uid);
                    recommend.setScore(item.getValue());
                    recommendService.addRecommend(recommend);
//                    //删除
//                    jedisCluster.zremrangeByRank("recommend:" + uid, Integer.MIN_VALUE, Integer.MAX_VALUE);
//                    //加入
//                    jedisCluster.zincrby("recommend:" + uid, item.getValue(), item.getItemID()+"");
                }
            }
        } catch (Exception e) {

        }
    }



    //高校推荐
    //上一次完成后，后5s进行下一次
    @Scheduled(fixedRate = 5000)
    public void getRecommendForUniversity() {
        try {
            System.out.println("高校-推荐算法开始运行");
            int NEIGHBOORHOODNUM = 10;
            int RECOMMENDNUM = 20;
            String server = "212.64.58.87";
            //权重计算使用数据库中的值，也可以使用文件形式
            MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
            dataSource.setServerName(server);
            dataSource.setUser("root");
            dataSource.setPassword("123456");
            dataSource.setDatabaseName("baoyandao");
            dataSource.setUseSSL(false);
            dataSource.setServerTimezone("Asia/Shanghai");
            DataModel model = new MySQLJDBCDataModel(dataSource, "tbl_university_view_record", "userid", "universityid", "score", "datetime");

            //基于SVD分类的推荐
            Recommender recommender = new SVDRecommender(model,
                    new ALSWRFactorizer(model, 10, 0.75, 10));
            LongPrimitiveIterator iterator=model.getUserIDs();

            while(iterator.hasNext()) {
                long uid=iterator.nextLong();
                List<RecommendedItem> list=recommender.recommend(uid, RECOMMENDNUM);
                for(RecommendedItem item:list) {
                    Recommend recommend = new Recommend();
                    recommend.setUniversityid((int)(item.getItemID()));
                    recommend.setUserid((int) uid);
                    recommend.setScore(item.getValue());
                    recommendService.addRecommendForUniversity(recommend);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //后台分词用
    @Scheduled(fixedRate = 1000)
    public void getGlobalSegmentation() {
//        System.out.println(termList);
        System.out.println("热词分割算法开始运行");
        List<Integer> allpostid = recommendService.getALLPostidForWordSeg();
        for(int id:allpostid){
            List<Post> post_contents = recommendService.getAllContent(id);
            Set<String> words = new HashSet<>();
            for(Post p : post_contents){

                words.addAll(SegStr(StringUtil.delHtmlTag(p.getContent()),Nature.ntu));
            }
            if (words.size()>0)
                recommendService.updatePostKeywordSegByPostid(Joiner.on(",").join(words),id);
        }
//        System.out.println(content_All.size());

    }
    public Set<String> SegStr(String s,Nature nature){
//            System.out.println(s);
        Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);
        Set<String> nature_s = new HashSet<>();
        List<Term> termList = segment.seg(s);
//        System.out.println(termList.toString());
        for (Term t : termList) {
//                System.out.println(t.word+"----"+t.nature);
//                System.out.println(t.nature);
            if (t.nature == nature  ) {
                nature_s.add(t.word);
//                if (t.nature == nature){
                recommendService.addHotword(t.word);
//                }
            }
        }
//        System.out.println(s);
//        System.out.println(nature_s.toString());
        return nature_s;

    }










}
//        for(Term t : termList){
////            System.out.println(t.word+"--"+t.getFrequency());
//        }
//        System.out.println(termList);
//        System.out.println(NLPTokenizer.segment("我来自中国药科大学和南京大学，你来自大连理工大学和北京科技大学 还和 中国科学院医工所？"));


//        String text = "算法工程师\n" +
//                "算法（Algorithm）是一系列解决问题的清晰指令，也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出。如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。不同的算法可能用不同的时间、空间或效率来完成同样的任务。一个算法的优劣可以用空间复杂度与时间复杂度来衡量。算法工程师就是利用算法处理事物的人。\n" +
//                "\n" +
//                "1职位简介\n" +
//                "算法工程师是一个非常高端的职位；\n" +
//                "专业要求：计算机、电子、通信、数学等相关专业；\n" +
//                "学历要求：本科及其以上的学历，大多数是硕士学历及其以上；\n" +
//                "语言要求：英语要求是熟练，基本上能阅读国外专业书刊；\n" +
//                "必须掌握计算机相关知识，熟练使用仿真工具MATLAB等，必须会一门编程语言。\n" +
//                "\n" +
//                "2研究方向\n" +
//                "视频算法工程师、图像处理算法工程师、音频算法工程师 通信基带算法工程师\n" +
//                "\n" +
//                "3目前国内外状况\n" +
//                "目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、雷达信号处理、生物医学信号处理等领域的一维信息算法处理。\n" +
//                "在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法：机器视觉成为此类算法研究的核心；另外还有2D转3D算法(2D-to-3D conversion)，去隔行算法(de-interlacing)，运动估计运动补偿算法(Motion estimation/Motion Compensation)，去噪算法(Noise Reduction)，缩放算法(scaling)，锐化处理算法(Sharpness)，超分辨率算法(Super Resolution),手势识别(gesture recognition),人脸识别(face recognition)。\n" +
//                "在通信物理层等一维信息领域目前常用的算法：无线领域的RRM、RTT，传送领域的调制解调、信道均衡、信号检测、网络优化、信号分解等。\n" +
//                "另外数据挖掘、互联网搜索算法也成为当今的热门方向。\n" +
//                "算法工程师逐渐往人工智能方向发展。";
////        List<String> phraseList = HanLP.extractPhrase(text, 10);
//        List<String> sentenceList = HanLP.extractSummary(text, 10);
//        System.out.println(sentenceList);

