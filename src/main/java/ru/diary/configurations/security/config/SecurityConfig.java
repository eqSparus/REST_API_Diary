package ru.diary.configurations.security.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableWebSecurity
@PropertySource(value = "classpath:/security.properties", encoding = "UTF-8")
@ComponentScan(basePackages = "ru.diary.configurations.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Environment environment;
    Filter filter;


    @Autowired
    public SecurityConfig(
            Environment environment,
            @Qualifier("jwtTokenAuthenticationFilter") GenericFilterBean filter) {
        this.environment = environment;
        this.filter = filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        var urlConfirm = environment.getProperty("path.confirm");
        var urlLogin = environment.getProperty("path.login");
        var urlRegistration = environment.getProperty("path.registration");
        var urlResetPass = environment.getProperty("path.reset_pass");
        var urlUpdatePass = environment.getProperty("path.update_pass");
        var urlCrud = environment.getProperty("path.crud");
        var urlData = environment.getProperty("path.data");
        var urlUpdate = environment.getProperty("path.update");

        http
                .addFilterBefore(filter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(urlUpdatePass).authenticated()
                .antMatchers(urlCrud, urlData, urlUpdate).authenticated()
                .antMatchers(urlLogin, urlRegistration, urlConfirm, urlResetPass).permitAll();

        http.csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
