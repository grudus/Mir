package com.grudus.configuration;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

import java.net.UnknownHostException;

@Configuration
public class AppConfig {
//
//    @Bean
//    public Mongo mongo() throws UnknownHostException {
//        return new Mongo("localhost");
//    }
//
//    @Bean
//    public static MongoTemplate mongoTemplate() {
//        return new MongoTemplate()
//    }

    @Bean
    public static AsyncSupportConfigurer  asyncSupportConfigurer() {
        AsyncSupportConfigurer asyncSupportConfigurer = new AsyncSupportConfigurer();
        asyncSupportConfigurer.setDefaultTimeout(3 * 1000L);
        return asyncSupportConfigurer;
    }

}
