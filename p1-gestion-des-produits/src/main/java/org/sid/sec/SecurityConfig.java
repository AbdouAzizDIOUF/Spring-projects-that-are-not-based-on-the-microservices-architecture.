package org.sid.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //BCryptPasswordEncoder bcpe = getBCPE();
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, roles as role from user_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(bCryptPasswordEncoder);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.formLogin().loginPage("/login") // personalisation ppage connexion
        http.formLogin(); // formulaire d'authenfication
        http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/user/*").permitAll();
        http.exceptionHandling().accessDeniedPage("/404"); // La page d'erreur
    }

    @Bean
    BCryptPasswordEncoder getBCPE()
    {
        return new BCryptPasswordEncoder();
    }
}
