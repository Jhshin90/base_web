package com.work.baseWeb.config;

import com.work.baseWeb.common.handler.AuthFailureHandler;
import com.work.baseWeb.common.handler.AuthSucessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final AuthSucessHandler authSucessHandler;
	private final AuthFailureHandler authFailureHandler;


	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
				.authorizeRequests() // 요청 URL에 따라 접근 권한을 설정
				.antMatchers("/","/login/**","/js/**","/css/**","/image/**").permitAll() // 해당 경로들은 접근을 허용
				.anyRequest() // 다른 모든 요청은
				.authenticated() // 인증된 유저만 접근을 허용
		.and()
				.formLogin() // 로그인 폼은
				.loginPage("/login") // 해당 주소로 로그인 페이지를 호출한다.
				.loginProcessingUrl("/loginProc")
				.successHandler(authSucessHandler) // 성공시 요청을 처리할 핸들러
				.failureHandler(authFailureHandler) // 실패시 요청을 처리할 핸들러
		.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
				.logoutSuccessUrl("/login") // 성공시 리턴 URL
				.invalidateHttpSession(true) // 인증정보를 지우하고 세션을 무효화
				.deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
				.permitAll()
		.and()
				.sessionManagement()
				.maximumSessions(1) // 세션 최대 허용 수 1, -1인 경우 무제한 세션 허용
				.maxSessionsPreventsLogin(false) // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
				.expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired")  // 세션이 만료된 경우 이동 할 페이지를 지정
		.and()
				.and().rememberMe() // 로그인 유지
				.alwaysRemember(false) // 항상 기억할 것인지 여부
				.tokenValiditySeconds(43200) // in seconds, 12시간 유지
				.rememberMeParameter("remember-me");
	}
}





