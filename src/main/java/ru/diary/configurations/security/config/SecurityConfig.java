package ru.diary.configurations.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.diary.models.Role;

import javax.servlet.Filter;

@EnableWebSecurity
@PropertySource(value = "classpath:/security.properties", encoding = "UTF-8")
@ComponentScan(basePackages = "ru.diary.configurations.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Environment environment;
    Filter filter;
    AuthenticationProvider provider;

    @Autowired
    public SecurityConfig(
            Environment environment,
            @Qualifier("tokenAuthenticationFilter") Filter filter,
            AuthenticationProvider provider) {
        this.environment = environment;
        this.filter = filter;
        this.provider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String urlStop = environment.getProperty("path.stop");
        String urlLogin = environment.getProperty("path.login");
        String urlRegistration = environment.getProperty("path.registration");

        http
                .addFilterBefore(filter, BasicAuthenticationFilter.class)
                .authenticationProvider(provider)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, urlStop).hasRole(Role.USER.name())
                .antMatchers(HttpMethod.POST, urlLogin, urlRegistration).permitAll();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
