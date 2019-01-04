package com.derteuffel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


/**
 * Created by derteuffel on 20/10/2018.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;
    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

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
                .antMatchers("/about").permitAll()
                .antMatchers("/deals").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/other/**").permitAll()
                .antMatchers("/visitor/**").permitAll()
                .antMatchers("/course/**").permitAll()
                .antMatchers("/period/**").permitAll()
                .antMatchers("/user/visitor/form").permitAll()
                .antMatchers("/user/visitor/create/post").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/validate/**").permitAll()
                .antMatchers("/school/**").permitAll()
                .antMatchers("/education/**").permitAll()
                .antMatchers("/training/**").permitAll()
                .antMatchers("/external/secure/**").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/user/create").permitAll()
                .antMatchers("/user/create/visitor").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/management/**").hasAuthority("root")
                /*.antMatchers("/user/**").permitAll()
                .antMatchers("/these/**").permitAll()
                .antMatchers("/groupe/**").permitAll()*/
                .antMatchers("/user/**").hasAnyAuthority("admin","user","root")
                .antMatchers("/these/**").hasAnyAuthority("user","admin","root")
                .antMatchers("/groupe/**").hasAnyAuthority("user","admin","root")
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/groupe/groupes")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/templates/**","/static/**", "/css/**","/scss/**", "/js/**", "/images/**","/img/**","/fonts/**", "/downloadFile/**", "/vendor/**");
    }

}
