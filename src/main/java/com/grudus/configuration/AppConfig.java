package com.grudus.configuration;

import com.grudus.help.LoginHelp;
import com.grudus.help.MessageHelp;
import com.grudus.help.WaitingUsersHelp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public LoginHelp login() {
        return new LoginHelp();
    }

    @Bean
    public WaitingUsersHelp waitingUsersHelp() {return new WaitingUsersHelp();}

    @Bean
    public MessageHelp messageHelp() {return new MessageHelp();}


}
