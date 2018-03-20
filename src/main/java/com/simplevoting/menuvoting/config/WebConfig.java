package com.simplevoting.menuvoting.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/app");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {

        /*CookieAndHeadersLocaleResolver localeResolver = new CookieAndHeadersLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en"));
        localeResolver.setCookieName("localeCookie");
        localeResolver.setCookieMaxAge(4800);*/
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        return localeResolver;
    }

   /* public class CookieAndHeadersLocaleResolver extends CookieLocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            String acceptLanguage = request.getHeader("Accept-Language");
            if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
                return super.determineDefaultLocale(request);
            }
            return request.getLocale();
        }
    }*/
}
