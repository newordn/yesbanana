package com.derteuffel.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by derteuffel on 20/10/2018.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer  {

    /*@Bean
    public StrongPasswordEncryptor strongPasswordEncryptor(){
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor;
    }*/

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

            return bCryptPasswordEncoder;




    }
}
