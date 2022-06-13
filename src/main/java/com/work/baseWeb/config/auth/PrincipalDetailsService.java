package com.work.baseWeb.config.auth;


import com.work.baseWeb.entity.user.User;
import com.work.baseWeb.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = loginRepository.findByUsername(username);
		if(user == null) {
			return null;
		}
		return new PrincipalDetails(user);
	}

}
