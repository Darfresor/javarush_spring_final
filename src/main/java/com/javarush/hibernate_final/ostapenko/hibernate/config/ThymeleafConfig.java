package com.javarush.hibernate_final.ostapenko.hibernate.config;

import com.javarush.hibernate_final.ostapenko.hibernate.security.jwt.SecurityDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {

    // Переименуйте bean с "securityDialect" на "jwtSecurityDialect"
    @Bean(name = "jwtSecurityDialect")
    public SecurityDialect jwtSecurityDialect() {
        return new SecurityDialect();
    }

    @Bean
    public SpringTemplateEngine templateEngine(
            ITemplateResolver templateResolver,
            SecurityDialect jwtSecurityDialect) {  // Spring найдет bean по типу

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Добавляем оба диалекта
        templateEngine.addDialect(jwtSecurityDialect);

        return templateEngine;
    }
}