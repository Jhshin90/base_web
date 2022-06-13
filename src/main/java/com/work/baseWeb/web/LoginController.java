package com.work.baseWeb.web;

import com.work.baseWeb.entity.user.User;
import com.work.baseWeb.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public String getLoginPage(Model model,
							   @RequestParam(value = "error", required = false) String error,
							   @RequestParam(value = "exception", required = false) String exception) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "login/login";
	}

	@GetMapping("/login/join")
	public String join() {
		return "login/join";
	}

	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행 : " + user);
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		loginRepository.save(user);
		return "redirect:/";
	}
}
