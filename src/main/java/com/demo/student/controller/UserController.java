package com.demo.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.student.dto.User;
import com.demo.student.model.LoginUserBean;
import com.demo.student.model.UserBean;
import com.demo.student.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView setupUserPage() {
		return new ModelAndView("sign-up", "user", new UserBean());
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") @Validated UserBean userBean, BindingResult bindingResult,
			HttpSession session, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return "sign-up";
		}
		if (!userBean.getUserPassword().equals(userBean.getUserConfirmPassword())) {
			String cpwdError = "The password confirmation does not match.";
			System.out.println(cpwdError);
			model.addAttribute("confirmPw", cpwdError);
			return "sign-up";
		}
		User dto = new User();
		dto.setName(userBean.getUserName());
		dto.setEmail(userBean.getUserEmail());
		dto.setPassword(userBean.getUserPassword());
		dto.setRole(userBean.getUserRole());

		User user = userService.addUser(dto);
		if (user == null) {
			model.addAttribute("error", "Insertion Fail!");
			return "sign-up";
		}
		User loginUser = (User) session.getAttribute("loginUser");
		session.setAttribute("loginUser", user);
		return "redirect:/displayUser";
	}

	@RequestMapping(value = "/displayUser", method = RequestMethod.GET)
	public String displayUser(ModelMap model, HttpServletRequest req) {

		List<User> list = userService.selectAll();

		model.addAttribute("list", list);

//		model.addAttribute("error",req.getParameter("error"));
//		model.addAttribute("msg", req.getParameter("msg"));
		return "display-user";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.GET)
	public ModelAndView setupUpdateUser(@RequestParam("id") int id, ModelMap model) {
		User user = userService.findById(id);
		UserBean userBean = new UserBean();
		if (user != null) {
			userBean.setUserId(user.getId());
			userBean.setUserCode(user.getCode());
			userBean.setUserName(user.getName());
			userBean.setUserEmail(user.getEmail());
			userBean.setUserPassword(user.getPassword());
			userBean.setUserConfirmPassword(user.getPassword());
			userBean.setUserRole(user.getRole());
		}

		return new ModelAndView("update-user", "userBean", userBean);
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userBean") @Validated UserBean userBean, BindingResult bindingResult,
			HttpSession session, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "update-user";
		}

		if (!userBean.getUserPassword().equals(userBean.getUserConfirmPassword())) {
			String cpwdError = "The password confirmation does not match.";
			System.out.println(cpwdError);
			model.addAttribute("confirmPw", cpwdError);
			return "update-user";
		} else {

			User user = new User();
			user.setId(userBean.getUserId());
			user.setCode(userBean.getUserCode());
			user.setName(userBean.getUserName());
			user.setEmail(userBean.getUserEmail());
			user.setPassword(userBean.getUserPassword());
			user.setRole(userBean.getUserRole());
			User updatedUser = userService.updateUser(user);
			if (updatedUser == null) {
				model.addAttribute("error", "Update Fail");
				return "update-user";
			}
			// if loginuser , update
			User loginUser = (User) session.getAttribute("loginUser");
			if (loginUser.getCode().equals(user.getCode())) {
				session.setAttribute("loginUser", user);
			}
			return "redirect:displayUser";

		}
	}

//
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("id") int id, ModelMap model, HttpSession session, RedirectAttributes ra) {
		User user = userService.findById(id);

		// if loginuser , will not delete
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser != null && user.getRole().equals("Admin")) {
			ra.addFlashAttribute("error", "Can't delete Admin Account.");
//			model.addAttribute("error", "Can't delete Admin Account.");
			return "redirect:/displayUser";
		} else {
			userService.deleteUser(id);
			model.addAttribute("msg", "Successfully Deleted.");
			return "redirect:/displayUser";
		}

	}

	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	public String searchUser(@RequestParam String code, @RequestParam String name, ModelMap model) {
		List<User> list = userService.searchUser(code, name);
		model.addAttribute("code", code);
		model.addAttribute("name", name);
		if (list.size() < 0) {
			model.addAttribute("msg", "There is no user with this data");
		} else {
			model.addAttribute("list", list);
		}
		return "display-user";
	}

}
