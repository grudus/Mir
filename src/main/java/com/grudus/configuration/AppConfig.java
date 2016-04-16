package com.grudus.configuration;

import com.grudus.help.Login;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import sun.security.provider.MD5;

import java.net.UnknownHostException;

@Configuration
public class AppConfig {

    @Bean
    public Login login() {
        return new Login();
    }


}
