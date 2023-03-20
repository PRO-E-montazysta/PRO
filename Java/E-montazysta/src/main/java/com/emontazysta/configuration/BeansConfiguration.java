package com.emontazysta.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Configuration
public class BeansConfiguration {

//    @PersistenceContext
//    ApplicationContext applicationContext;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public EntityManager entityManager(){
//        EntityManagerFactory emf = (EntityManagerFactory)
//                applicationContext.getBean("entityManagerFactory", javax.persistence.EntityManagerFactory.class);
//        EntityManager em = emf.createEntityManager();
//        return em;
//    }
}
