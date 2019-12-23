package com.xkk.dao;

import com.xkk.pojo.Product;
import com.xkk.pojo.Trade;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PurchaseMapper {

    @Select("SELECT * FROM tbl_product ")
    public List<Product> getProductALL();
    @Select("SELECT * FROM tbl_product WHERE productid=#{productid} ")
    public Product getProductByProductId( int productid);

    @Insert("INSERT INTO tbl_trade(tradeno,userid,productid,price) VALUES(#{trade.tradeno},#{trade.userid},#{trade.productid},#{trade.price})")
    public int addTrade(@Param("trade") Trade trade);

    @Update("UPDATE tbl_trade SET status=1 WHERE tradeno=#{tradeno}")
    public int updateTradeStatus(String tradeno);

    @Select("SELECT * FROM tbl_trade WHERE productid=#{productid} AND userid=#{userid} AND status=1")
    public Trade getTradeByProductid(@Param("productid") int productid,@Param("userid") int userid);


}
