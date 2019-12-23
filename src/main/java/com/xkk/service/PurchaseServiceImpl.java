package com.xkk.service;

import com.xkk.dao.PurchaseMapper;
import com.xkk.pojo.Product;
import com.xkk.pojo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseMapper purchaseMapper;

    @Override
    public List<Product> getProductALL() {
        List<Product> products = purchaseMapper.getProductALL();
        for (Product p:products) {
            System.out.println(p.getImages());
            p.setImages_array(Arrays.asList( p.getImages().split(",")));
        }
        return products;
    }

    @Override
    public Product getProductByProductId(int productid) {
        return purchaseMapper.getProductByProductId(productid);
    }

    @Override
    public int addTrade(Trade trade) {
        return purchaseMapper.addTrade(trade);
    }

    @Override
    public int updateTradeStatus(String tradeno) {
        return purchaseMapper.updateTradeStatus(tradeno);
    }
}
