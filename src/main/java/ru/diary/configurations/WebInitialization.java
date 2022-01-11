package ru.diary.configurations;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.diary.configurations.security.config.SecurityConfig;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;

public class WebInitialization extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppDatabaseConfig.class, SecurityConfig.class, ModelAndMailConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ControllersConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/diary/API/*"};
    }


    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new CharacterEncodingFilter(StandardCharsets.UTF_8.toString())
        };
    }
}
