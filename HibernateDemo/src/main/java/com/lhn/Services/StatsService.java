/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhn.Services;

import com.lhn.hibernatedemo.HibernateUtils;
import com.lhn.pojo.Category;
import com.lhn.pojo.OrderDetail;
import com.lhn.pojo.Product;
import com.lhn.pojo.SaleOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public class StatsService {
    private static SessionFactory sessionFactory = HibernateUtils.getFactory();
    private static final SimpleDateFormat F = new SimpleDateFormat("dd/MM/yyyy");
     
    public List<Object[]> cateStats(){
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = builder.createQuery(Object[].class);
            
            Root rootC = q.from(Category.class);
            Root rootP = q.from(Product.class);
            
            q.where(builder.equal(rootP.get("category"), rootC.get("id")));
            q.multiselect(rootC.get("id"), rootC.get("name"), builder.count(rootP.get("id")));
            q.groupBy(rootC.get("id"));
            
            return session.createQuery(q).getResultList();
        }
    }
    
    public List<Object[]> getProdRevenue(Map<String, String> map) throws ParseException{
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = builder.createQuery(Object[].class);
            
            Root rootP = q.from(Product.class);
            Root rootD = q.from(OrderDetail.class);
            Root rootS = q.from(SaleOrder.class);
            
            List<Predicate> pre = new ArrayList<>();
            pre.add(builder.equal(rootD.get("productId"), rootP.get("id")));
            pre.add(builder.equal(rootD.get("orderId"), rootS.get("id")));
            
            if(!map.isEmpty()){
                if(map.containsKey("kw"))
                    pre.add(builder.like(rootP.get("name").as(String.class),String.format("%%%s%%", map.get("kw"))));
                
                if(map.containsKey("fromPrice"))
                    pre.add(builder.greaterThanOrEqualTo(rootP.get("price").as(Long.class),Long.parseLong(map.get("fromPrice"))));
              
                if(map.containsKey("toPrice"))
                    pre.add(builder.lessThanOrEqualTo(rootP.get("price").as(Long.class),Long.parseLong(map.get("toPrice"))));
                
                if(map.containsKey("fromDate"))
                    pre.add(builder.greaterThanOrEqualTo(rootS.get("createdDate").as(Date.class), F.parse(map.get("fromDate"))));
                
                if(map.containsKey("toDate"))
                    pre.add(builder.lessThanOrEqualTo(rootS.get("createdDate").as(Date.class), F.parse(map.get("toDate"))));
            }
            
            q.where(pre.toArray(new Predicate[] {}));
            q.multiselect(rootP.get("id"), rootP.get("name"),rootP.get("price"),
                    builder.sum(builder.prod(rootD.get("unitPrice"), rootD.get("num"))));
            q.groupBy(rootP.get("id"));
            
            return session.createQuery(q).getResultList();
        }
    }
        
    public List<Object[]> getProdRevenuebyMonth(String year){
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = builder.createQuery(Object[].class);
            
            Root rootD = q.from(OrderDetail.class);
            Root rootS = q.from(SaleOrder.class);

            List<Predicate> pre = new ArrayList<>();
            pre.add(builder.equal(rootD.get("orderId"), rootS.get("id")));
            pre.add(builder.equal(builder.function("YEAR", Integer.class, rootS.get("createdDate")), year));

            q.where(pre.toArray(new Predicate[] {}));
            q.multiselect(builder.function("MONTH", Integer.class, rootS.get("createdDate")),builder.sum(builder.prod(rootD.get("unitPrice"), rootD.get("num"))));
            q.groupBy(builder.function("MONTH", Integer.class, rootS.get("createdDate")));

            return session.createQuery(q).getResultList();
        }
    }
    
    public List<Object[]> getProdRevenuebyQuarter(String year){
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = builder.createQuery(Object[].class);
            
            Root rootD = q.from(OrderDetail.class);
            Root rootS = q.from(SaleOrder.class);

            List<Predicate> pre = new ArrayList<>();
            pre.add(builder.equal(rootD.get("orderId"), rootS.get("id")));
            pre.add(builder.equal(builder.function("YEAR", Integer.class, rootS.get("createdDate")), year));

            q.where(pre.toArray(new Predicate[] {}));
            q.multiselect(builder.function("QUARTER", Integer.class, rootS.get("createdDate")),builder.sum(builder.prod(rootD.get("unitPrice"), rootD.get("num"))));
            q.groupBy(builder.function("QUARTER", Integer.class, rootS.get("createdDate")));

            return session.createQuery(q).getResultList();
        }
    }
}
