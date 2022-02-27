/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhn.main;

import com.lhn.hibernatedemo.HibernateUtils;
import com.lhn.pojo.Category;
import com.lhn.pojo.Product;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Admin
 */
public class Demo {
    public static void main(String[] args) {
        ProductService a = new ProductService();
        BigDecimal startPrice = new BigDecimal(20000000);
        BigDecimal endPrice = new BigDecimal(30000000);
        
        System.out.println(a.productFinding(2));
    }
}
