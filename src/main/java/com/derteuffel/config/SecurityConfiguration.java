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
                .antMatchers("/deals").permitAll()
                .antMatchers("/other/**").permitAll()
                .antMatchers("/visitor/**").permitAll()
                .antMatchers("/course/**").permitAll()
                .antMatchers("/period/**").permitAll()
                .antMatchers("/event/**").permitAll()
                .antMatchers("/payment/**").permitAll()
                .antMatchers("/user/visitor/form").permitAll()
                .antMatchers("/user/visitor/create/post").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/validate/**").permitAll()
                .antMatchers("/external/secure/**").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/user/create").permitAll()
                .antMatchers("/user/create/visitor").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/groupe/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .antMatchers("/management/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER")
                .antMatchers("/these/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .antMatchers("/period/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .antMatchers("/event/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .antMatchers("/course/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .antMatchers("/bibliotheque/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .antMatchers("/bibliography/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .antMatchers("/user/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER")
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/groupe/groupes")
                .usernameParameter("name")
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
                .antMatchers("/resources/**", "/templates/**","/static/**", "/css/**", "/connexion/**","/scss/**", "/js/**", "/images/**","/img/**","/fonts/**", "/downloadFile/**", "/vendor/**");
    }

}
