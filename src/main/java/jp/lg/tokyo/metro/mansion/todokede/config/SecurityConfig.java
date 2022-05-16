/*
 * @(#) SecurityConfig.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.request.RequestContextListener;

import jp.lg.tokyo.metro.mansion.todokede.filter.CustomOncePerRequestFilter;
import jp.lg.tokyo.metro.mansion.todokede.security.am.AMAuthenticationFailedHandler;
import jp.lg.tokyo.metro.mansion.todokede.security.am.AMAuthenticationSuccessHandler;
import jp.lg.tokyo.metro.mansion.todokede.security.am.AMLogoutSuccessHander;
import jp.lg.tokyo.metro.mansion.todokede.security.am.AMUserDetailService;
import jp.lg.tokyo.metro.mansion.todokede.security.gs.GSAuthenticationFailedHandler;
import jp.lg.tokyo.metro.mansion.todokede.security.gs.GSAuthenticationSuccessHandler;
import jp.lg.tokyo.metro.mansion.todokede.security.gs.GSLogoutSuccessHander;
import jp.lg.tokyo.metro.mansion.todokede.security.gs.GSUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Configuration
    @Order(2)
    public static class ApartmentManagerSecurityConfig extends WebSecurityConfigurerAdapter {

        public ApartmentManagerSecurityConfig() {
            super();
        }

        @Bean
        public AMAuthenticationSuccessHandler amAuthenticationSuccessHandler() {
            return new AMAuthenticationSuccessHandler();
        }

        @Bean
        public AMAuthenticationFailedHandler amAuthenticationFailedHandler() {
            return new AMAuthenticationFailedHandler();
        }

        @Bean
        public AMUserDetailService amUserDetailService() {
            return new AMUserDetailService();
        }

        @Bean
        public AMLogoutSuccessHander amLogoutSuccessHandler() {
            return new AMLogoutSuccessHander();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(amUserDetailService()).passwordEncoder(passwordEncoder());
        }

        @Bean("amRedirectInvalidSessionStrategy")
        public RedirectInvalidSessionStrategy amRedirectInvalidSessionStrategy() {
            return new RedirectInvalidSessionStrategy();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/", "/GAA0110", "/MAA0110", "/MCA0110", "/SAA0110", "/SCA0110", "/css/**", "/js/**", "/lib/**", "/images/**", "/contents/**").permitAll()
                    .antMatchers("/MC**", "/MB**", "/ME**").hasRole("MANSION")
                    .antMatchers("/MD**", "/SB**").authenticated()
                    .and().formLogin()
                    .loginPage("/MAA0110")
                    .loginProcessingUrl("/am/login")
                    .successHandler(amAuthenticationSuccessHandler())
                    .failureHandler(amAuthenticationFailedHandler())
                    .usernameParameter("txtLoginId")
                    .passwordParameter("pwdPassword")
                    .and().exceptionHandling().accessDeniedPage("/MAA0110")
                    .and()
                    .logout()
                    .logoutUrl("/am/logout").logoutSuccessUrl("/MAA0110")
                    .invalidateHttpSession(false)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(amLogoutSuccessHandler())
                    .and()
                    .sessionManagement().maximumSessions(1).and().invalidSessionStrategy(amRedirectInvalidSessionStrategy());
            http.addFilterBefore(new CustomOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    @Order(1)
    public static class GovernmentStaffSecurityConfig extends WebSecurityConfigurerAdapter {

        public GovernmentStaffSecurityConfig() {
            super();
        }

        @Bean
        public GSUserDetailService gsUserDetailService() {
            return new GSUserDetailService();
        }

        @Bean
        public GSAuthenticationSuccessHandler gsAuthenticationSuccessHandler() {
            return new GSAuthenticationSuccessHandler();
        }

        @Bean
        public GSAuthenticationFailedHandler gsAuthenticationFailedHandler() {
            return new GSAuthenticationFailedHandler();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(gsUserDetailService()).passwordEncoder(passwordEncoder());
        }

        @Bean("gsRedirectInvalidSessionStrategy")
        public RedirectInvalidSessionStrategy gsRedirectInvalidSessionStrategy() {
            return new RedirectInvalidSessionStrategy();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/gs/**").authorizeRequests()
                    .antMatchers("/", "/GAA0110", "/MCA0110", "/SAA0110", "/SCA0110", "/css/**", "/js/**", "/lib/**", "/images/**", "/contents/**").permitAll()
                    .antMatchers("/gs/**").hasRole("CITY")
                    .antMatchers("/GB**", "/GD**", "/GE**", "/GF**", "/GG**", "/GI**", "/GJ**", "/GK**", "/ZAA0150/**").hasRole("CITY")
                    .antMatchers("/GL**").hasRole("TOKYO")
                    .antMatchers("/GC**", "/AA**", "/AB**").hasRole("MAINTENANCE")
                    .antMatchers("/MD**", "/SB**").authenticated()
                    .and().formLogin()
                    .loginProcessingUrl("/gs/login")
                    .loginPage("/GAA0110")
                    .successHandler(gsAuthenticationSuccessHandler())
                    .failureHandler(gsAuthenticationFailedHandler())
                    .usernameParameter("txtLoginId")
                    .passwordParameter("pwdPassword")
                    .and().exceptionHandling().accessDeniedPage("/GAA0110")
                    .and()
                    .logout()
                    .logoutUrl("/gs/logout")
                    .logoutSuccessHandler(gsLogoutSuccessHandler())
                    .invalidateHttpSession(false)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/GAA0110")
                    .and().sessionManagement().maximumSessions(1).and().invalidSessionStrategy(gsRedirectInvalidSessionStrategy());
            http.addFilterBefore(new CustomOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        @Bean
        public GSLogoutSuccessHander gsLogoutSuccessHandler() {
            return new GSLogoutSuccessHander();
        }
    }
}
