package mysite.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class LocaleConfig {
	
	// Locale Resolver
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver("lang");
		localeResolver.setCookieHttpOnly(false); // 통신뿐만 아니라, js에서도 접근 가능하게 함
		
		return localeResolver;
	}
	
	// Message Source
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("mysite/config/web/messages/message");
		messageSource.setDefaultEncoding("utf-8");
		
		return messageSource;
	}
}
