package com.demo.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.demo.student.dto.User;
import com.demo.student.model.LoginUserBean;
import com.demo.student.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		return new ModelAndView("login", "loginUserBean", new LoginUserBean());

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("loginUserBean") @Validated LoginUserBean loginUserBean,
			BindingResult bindingResult, HttpSession session, ModelMap model) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getErrorCount());
			model.addAttribute("msg", "Login Error");
			return "login";
		}
		User user = userService.findByCode(loginUserBean.getUserCode());
		if (user == null) {
			model.addAttribute("msg", "There is no user with this name.");
			model.addAttribute("user", loginUserBean);
			return "login";
		}
		if (!user.getPassword().equals(loginUserBean.getUserPassword())) {
			model.addAttribute("msg", "Please Check Your Password!");
			model.addAttribute("user", loginUserBean);
			return "login";
			
		}
		session.setAttribute("loginUser", user);
		return "redirect:/displayUser";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.setAttribute("loginUser", null);
		return "redirect:/";
	}

}
