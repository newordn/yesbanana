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
@Order(1)
public class SecurityConfig1 extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;
    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    public SecurityConfig1() {
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
                .antMatchers("/mobile/**").permitAll()
                .antMatchers("/deals").permitAll()
                .antMatchers("/about/**").permitAll()
                .antMatchers("/visitor/**").permitAll()
                .antMatchers("/course/**").permitAll()
                .antMatchers("/period/**").permitAll()
                .antMatchers("/reservation/**").permitAll()
                .antMatchers("/event/**").permitAll()
                .antMatchers("/payment/**").permitAll()
                .antMatchers("/user/visitor/form").permitAll()
                .antMatchers("/user/visitor/buy/form").permitAll()
                .antMatchers("/login/visitor").permitAll()
                .antMatchers("/login_visitor").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/user/visitor/buy/save").permitAll()
                .antMatchers("/user/visitor/create/post").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/validate/**").permitAll()
                .antMatchers("/external/secure/**").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/user/create").permitAll()
                .antMatchers("/user/create/visitor").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/groupe/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER","VISITOR","LIVRE")
                .antMatchers("/management**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","LIVRE")
                .antMatchers("/these/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER","LIVRE")
                .antMatchers("/bibliotheque/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER","")
                .antMatchers("/bibliography/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER","lIVRE")
                .antMatchers("/user/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER","LIVRE")
                .antMatchers("/colonie/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER","LIVRE")
                .antMatchers("/primaire/**").hasAnyAuthority( "ROOT_MASTER","ROOT","ADMIN_MASTER","ADMIN","USER","LIVRE")
                .anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/groupe/groupes")
                .usernameParameter("name")
                .passwordParameter("password")
                .and().logout().logoutUrl("/perform_logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/templates/**","/static/**", "/css/**", "/new/**", "/connexion/**","/scss/**", "/js/**", "/images/**","/img/**","/fonts/**", "/downloadFile/**", "/vendor/**");
    }
}

