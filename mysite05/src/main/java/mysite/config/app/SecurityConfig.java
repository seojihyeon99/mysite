package mysite.config.app;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.repository.UserRepository;
import mysite.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity // DelegatingFilterProxy를 통해 Spring Security 필터 체인을 구성하고 HTTP 요청이 이 체인을 거치도록한다.
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// post, put, delete 처럼 상태를 변경하는 요청에 대해 CSRF 토큰을 요구 => 이 토큰은 HTML 폼에 hidden으로 포함되거나 헤더로 전달되어야 함 => 이 보호 설정을 끔
			.csrf(csrf -> csrf.disable())
			.formLogin(formLogin -> {
				formLogin
					.loginPage("/user/login")
					.loginProcessingUrl("/user/auth")
					.usernameParameter("email")
					.passwordParameter("password")
					.defaultSuccessUrl("/")
//					.failureUrl("/user/login?result=fail");
					.failureHandler(new AuthenticationFailureHandler() {
						@Override
						public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
								AuthenticationException exception) throws IOException, ServletException {
							request.setAttribute("email", request.getParameter("email"));
							request.setAttribute("result", "fail");
							request
								.getRequestDispatcher("/user/login")
								.forward(request, response);
						}
					});
			})
			.logout(logout -> {
				logout
					.logoutUrl("/user/logout")
					.logoutSuccessUrl("/");
			})
			.authorizeHttpRequests(authorizeRequests -> {
				/* ACL */
				authorizeRequests
					.requestMatchers(new RegexRequestMatcher("^/admin/?.*$", null))
					.hasAnyRole("ADMIN")

					.requestMatchers(new RegexRequestMatcher("^/user/update$", null))
//					.authenticated()
					.hasAnyRole("ADMIN", "USER")

					.requestMatchers(new RegexRequestMatcher("^/board/?(write|modify|delete|reply)$", null))
					.hasAnyRole("ADMIN", "USER")

					.anyRequest().permitAll();
			})
			.exceptionHandling(exceptionHandling -> {
				exceptionHandling.accessDeniedPage("/WEB-INF/views/errors/403.jsp");
				exceptionHandling.accessDeniedHandler(new AccessDeniedHandler() {
					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						response.sendRedirect(request.getContextPath());
					}
				});
			});
			
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncode) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // Authentication(인가)을 DB에서 한다.
		
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncode);
		
		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// Bcrypt는 단방향 해시 알고리즘
		return new BCryptPasswordEncoder(4 /* 4 ~ 31 */); // 해시함수 몇 번 돌릴건지(커지면 시간 오래 걸림..)
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return new UserDetailsServiceImpl(userRepository);
	}
}
