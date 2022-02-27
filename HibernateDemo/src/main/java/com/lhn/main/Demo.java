/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhn.main;

import com.lhn.hibernatedemo.HibernateUtils;
import com.lhn.pojo.Category;
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
        SessionFactory f = HibernateUtils.getFactory();
        try(Session s = f.openSession()) {
            Query q = s.createQuery("From Category");
            List<Category> cates = q.getResultList();
            cates.forEach(cate -> System.out.printf("%d - %s\n", cate.getId(), cate.getName()));
        }
    }
}
