package ru.diary.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = {"ru.diary.mail", "ru.diary.models"})
public class ModelAndMailConfig {
}
