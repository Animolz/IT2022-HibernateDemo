/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhn.main;

import com.lhn.Services.ProductService;
import com.lhn.Services.StatsService;
import com.lhn.hibernatedemo.HibernateUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Admin
 */
public class Demo {
    public static void main(String[] args) throws ParseException {
//        ProductService a = new ProductService();
//        BigDecimal startPrice = new BigDecimal(20000000);
//        BigDecimal endPrice = new BigDecimal(30000000);
//        
//        System.out.println(a.productFinding(2));

        Map<String, String> map = new HashMap<>();
//        map.put("fromPrice", "10000000");
//        map.put("toPrice", "30000000");
        map.put("fromDate", "01/01/2020");
        map.put("toDate", "01/03/2020");
        StatsService stats = new StatsService();
        List<Object[]> result = stats.getProdRevenuebyQuarter("2020");
        
        result.forEach(stat -> System.out.printf("%s - %d\n", stat[0],stat[1]));
    }
}
