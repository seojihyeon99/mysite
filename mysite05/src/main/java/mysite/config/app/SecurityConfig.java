package mysite.config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity // DelegatingFilterProxy를 통해 Spring Security 필터 체인을 구성하고 HTTP 요청이 이 체인을 거치도록한다.
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.formLogin((formLogin) -> {
				formLogin
					.loginPage("/user/login");
			})
			.authorizeHttpRequests((authorizeRequests) -> {
				/* ACL */
				authorizeRequests
					.requestMatchers(new RegexRequestMatcher("^/user/update$", null)).authenticated()
					.anyRequest().permitAll();
			});
			
		return http.build();
	}

}
