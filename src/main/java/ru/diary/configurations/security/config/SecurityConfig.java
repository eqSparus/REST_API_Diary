package ru.diary.configurations.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@EnableWebSecurity
@PropertySource(value = "classpath:/security.properties", encoding = "UTF-8")
@ComponentScan(basePackages = "ru.diary.configurations.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Environment environment;
    Filter filter;
    Filter encodingFilter;
    AuthenticationProvider provider;


    @Autowired
    public SecurityConfig(
            Environment environment,
            @Qualifier("tokenAuthenticationFilter") Filter filter,
            @Qualifier("encodingFilterConfig") Filter encodingFilter,
            AuthenticationProvider provider) {
        this.environment = environment;
        this.filter = filter;
        this.encodingFilter = encodingFilter;
        this.provider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String urlConfirm = environment.getProperty("path.confirm");
        String urlLogin = environment.getProperty("path.login");
        String urlRegistration = environment.getProperty("path.registration");
        String urlResetPass = environment.getProperty("path.reset_pass");
        String urlUpdatePass = environment.getProperty("path.update_pass");

        http
                .addFilterBefore(encodingFilter, ChannelProcessingFilter.class)
                .addFilterBefore(filter, BasicAuthenticationFilter.class)
                .authenticationProvider(provider)
                .authorizeRequests()
                .antMatchers(urlUpdatePass).authenticated()
                .antMatchers(urlLogin, urlRegistration, urlConfirm, urlResetPass).permitAll();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
