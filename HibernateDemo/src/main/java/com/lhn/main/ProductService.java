/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhn.main;

import com.lhn.hibernatedemo.HibernateUtils;
import com.lhn.pojo.Product;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Admin
 */
public class ProductService {
    SessionFactory sessionFactory = HibernateUtils.getFactory();
    
    public List<Product> productFinding(String kw){
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);
            
            if(!kw.isEmpty()) {
                Predicate p1 = builder.like(root.get("name").as(String.class), String.format("%%%s%%",kw));
                Predicate p2 = builder.like(root.get("description").as(String.class), String.format("%%%s%%",kw));
                query = query.where(builder.or(p1,p2));
            }
           
            List<Product> pros = session.createQuery(query).getResultList();
            return pros;
        }
    }
    public List<Product> productFinding(BigDecimal startPrice, BigDecimal endPrice){
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);

            if(startPrice != null && endPrice != null) {
                Predicate p1 = builder.between(root.get("price").as(BigDecimal.class), startPrice, endPrice);
                query = query.where(p1);
            }

            List<Product> pros = session.createQuery(query).getResultList();
            return pros;
        }
    }
    public List<Product> productFinding(int cateId){
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);

            if(0 < cateId) {
                query = query.where(builder.equal(root.get("category"), cateId));
            }

            List<Product> pros = session.createQuery(query).getResultList();
            return pros;
        }
    }
}
