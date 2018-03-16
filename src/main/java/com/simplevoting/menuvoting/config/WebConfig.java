package com.simplevoting.menuvoting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("#{systemEnvironment['MENUVOTE_ROOT']}")
    private String rootFolder;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(rootFolder + "/config/messages/app");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieAndHeadersLocaleResolver localeResolver = new CookieAndHeadersLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en"));
        localeResolver.setCookieName("localeCookie");
        localeResolver.setCookieMaxAge(4800);
        return localeResolver;
    }

    public class CookieAndHeadersLocaleResolver extends CookieLocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            String acceptLanguage = request.getHeader("Accept-Language");
            if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
                return super.determineDefaultLocale(request);
            }
            return request.getLocale();
        }
    }
}
