package com.work.baseWeb.common.handler;

import com.work.baseWeb.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final LoginRepository loginRepository;
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        loginRepository.updateUserLastLogin(authentication.getName(), LocalDateTime.now());
        setDefaultTargetUrl("/board/list");
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
