/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhn.hibernatedemo;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.cfg.Environment;

/**
 *
 * @author Admin
 */
public class HibernateUtils {
    private static final SessionFactory factory;
    
    static{
        Configuration conf = new Configuration();
        Properties pros = new Properties();
        pros.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        pros.put(Environment.URL, "jdbc:mysql://localhost:3306/saledb");
        pros.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        pros.put(Environment.USER, "root");
        pros.put(Environment.PASS, "12345678");
        pros.put(Environment.SHOW_SQL, "true");
        
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        factory = conf.buildSessionFactory(registry);
    }
    
    /**
     * @return the factory
     */
    public static SessionFactory getFactory() {
        return factory;
    }
}
