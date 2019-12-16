package com.xkk.util;

import java.util.ArrayList;
import java.util.List;

public class PageVariableUtil {
    public static int NUMS = 32;
    public static int PAGE_RANGE = 4;

    //当前在 page_start页,围绕他生成周围的Range页
    public static List<Integer> Get_Page_indexes(int page_start,int pagetotal){
        List<Integer> page_indexes = new ArrayList<>();
        for(int i = PageVariableUtil.PAGE_RANGE+1; i>=0; i--){
            if(page_start-i>=1){
                page_indexes.add(page_start-i);
            }
        }
        for(int i = 1; i< PageVariableUtil.PAGE_RANGE+1; i++){
            if(page_start+i<=pagetotal){
                page_indexes.add(page_start+i);
            }
        }
        return  page_indexes;
    }

}
