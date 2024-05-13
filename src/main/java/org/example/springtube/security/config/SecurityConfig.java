package org.example.springtube.security.config;//package org.example.springtube.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/", "/signIn", "/signUp", "/forgotPassword", "/resetPassword", "/static/**").permitAll()
//                .antMatchers("/springtube").permitAll()
//            //   .antMatchers("/**").authenticated()
//         ///       .antMatchers("/profile", "/channel", "/like","/dislike" ,"/admin/**").authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/signIn")
//                .usernameParameter("email")
//                .defaultSuccessUrl("/springtube", true)
//                .failureUrl("/signIn?error")
//                .permitAll()
//                .and()
//                .rememberMe()
//                .key("uniqueAndSecret")
//                .tokenValiditySeconds(86400) // 24 hours
//                .tokenRepository(persistentTokenRepository())
//                .and()
//                .logout()
//                .permitAll();
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/signIn", "/signUp", "/forgotPassword", "/resetPassword", "/static/**").permitAll()
                .antMatchers("/springtube").permitAll()
                .and()
                .formLogin()
                .loginPage("/signIn")
                .usernameParameter("email")
                .defaultSuccessUrl("/springtube", true)
                .failureUrl("/error")
                .permitAll()
                .and()
                .rememberMe()
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400) // 24 hours
                .tokenRepository(persistentTokenRepository())
                .and()
                .logout()
                .permitAll();
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}