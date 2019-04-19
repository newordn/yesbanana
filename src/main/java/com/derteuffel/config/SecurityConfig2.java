package com.derteuffel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Created by derteuffel on 10/04/2019.
 */
@Configuration
@Order(2)
public   class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;
    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    public SecurityConfig2() {
        super();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(bCryptPasswordEncoder)
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/deals").permitAll()
                .antMatchers("/other/**").permitAll()
                .antMatchers("/visitor/**").permitAll()
                .antMatchers("/course/**").permitAll()
                .antMatchers("/period/**").permitAll()
                .antMatchers("/event/**").permitAll()
                .antMatchers("/payment/**").permitAll()
                .antMatchers("/user/visitor/form").permitAll()
                .antMatchers("/user/visitor/buy/form").permitAll()
                .antMatchers("/login/visitor").permitAll()
                .antMatchers("/login_visitor").permitAll()
                .antMatchers("/user/visitor/buy/save").permitAll()
                .antMatchers("/user/visitor/create/post").permitAll()
                .antMatchers("/validate/**").permitAll()
                .antMatchers("/external/secure/**").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/user/create").permitAll()
                .antMatchers("/user/create/visitor").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/user/**").hasAuthority( "VISITOR")
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login/visitor")
                .loginProcessingUrl("/login_visitor")
                .failureUrl("/login/visitor?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("name")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/templates/**","/static/**", "/css/**", "/connexion/**","/scss/**", "/js/**", "/images/**","/img/**","/fonts/**", "/downloadFile/**", "/vendor/**");
    }

}