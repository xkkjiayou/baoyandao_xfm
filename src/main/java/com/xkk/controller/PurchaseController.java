package com.xkk.controller;

import com.xkk.pojo.Product;
import com.xkk.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;


    @GetMapping(value ="/resume_center")
    public String resume_center_index(ModelMap map){
        List<Product> products = purchaseService.getProductALL();
        map.put("products",products);
        return "resume_center";
    }
}
