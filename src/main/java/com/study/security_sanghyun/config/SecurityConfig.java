package com.study.security_sanghyun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.study.security_sanghyun.config.auth.AuthFailureHandler;
import com.study.security_sanghyun.service.notice.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity	// 만들어진 걸 비활성화 시키고 우리가 만든걸 사용할 것
@Configuration	// IOC 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final CorsFilter corsFilter;
	
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean	// 들어오는 비밀번호 암호화
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();	// 중간에 정보를 낚아채는 것을 방어
		
		http.headers()
			.frameOptions()
			.disable();
		http.addFilter(corsFilter);
		
		http.authorizeRequests()
			.antMatchers("/api/v1/grant/test/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			
			.antMatchers("/api/v1/grant/test/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

			.antMatchers("/api/v1/grant/test/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			
			.antMatchers("/notice/addition", "/notice/modification/**")
			.hasRole("ADMIN")
			
			.antMatchers("/", "/index", "/mypage/**")		// 1) 우리가 지정한 요청
			.authenticated()								// 2) 인증을 거쳐라
			
			.anyRequest()									// 3) 다른 모든 요청들은
			.permitAll()									// 4) 모두 접근 권한을 부여
			.and()								
			.formLogin()									// 5) 로그인 방식은 form 로그인 사용
			.loginPage("/auth/signin")						// 6) 로그인 페이지는 해당 get 요청을 통해 접근
			.loginProcessingUrl("/auth/signin")				// 7) 로그인 요청(post요청)
			.failureHandler(new AuthFailureHandler())
			
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(principalOauth2UserService)
			.and()
			.defaultSuccessUrl("/index");		
	}
	
}
