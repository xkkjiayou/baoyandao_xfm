package com.xkk.service;

import com.xkk.pojo.Product;
import com.xkk.pojo.Trade;

import java.util.List;

public interface PurchaseService {

    public List<Product> getProductALL();
    public Product getProductByProductId(int productid);
    public int addTrade(Trade trade);
    public int updateTradeStatus(String tradeno);
}
