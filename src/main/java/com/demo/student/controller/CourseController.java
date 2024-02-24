package com.demo.student.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.student.dto.Course;
import com.demo.student.dto.Student;
import com.demo.student.model.CourseBean;
import com.demo.student.service.CourseService;
import com.demo.student.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CourseController {
	@Autowired
	CourseService courseService;
	
	@Autowired
	StudentService studentService;

	@RequestMapping(value = "/addCourse", method = RequestMethod.GET)
	public ModelAndView setupAddCourse() {
		
		return new ModelAndView("add-course","course",new CourseBean());
	}

	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
	public String addCourse(@ModelAttribute("course") @Validated CourseBean courseBean,BindingResult bindingResult ,ModelMap model) {
		if(bindingResult.hasErrors()) {
			return "add-course";
		}
		Course newCourse = new Course();
		newCourse.setName(courseBean.getName());
		Course course = courseService.findByName(courseBean.getName());
		if(course != null) {
			model.addAttribute("msg","Course already exists!");
			return "add-course";
		}
		course = courseService.addCourse(newCourse);
		return "redirect:/displayCourse";
		
	}
	
	@RequestMapping(value="displayCourse",method=RequestMethod.GET)
	public String displayCourse(ModelMap model, RedirectAttributes ra) {
		List<Course> courses = courseService.findAll();
		
		model.addAttribute("courses",courses);
		
//		model.addAttribute("error",);
		return "display-course";
	}
	
	@RequestMapping(value="deleteCourse",method = RequestMethod.GET)
	public String deleteCourse(@RequestParam("id") int id,ModelMap model,RedirectAttributes ra) {
		Course course = courseService.findById(id);
		List<Student> s = studentService.searchStudent("", "", course);
		if(s.size() <= 0) {
			courseService.deleteCourse(id);
		}else {
			ra.addFlashAttribute("error","Can't delete this course.");
		}
		
		return "redirect:/displayCourse";
	}
	
	@RequestMapping(value="searchCourse", method=RequestMethod.POST)
	public String searchCourse(@RequestParam("code") String code, @RequestParam("name") String name,ModelMap model) {
		List<Course> list = new ArrayList<>();
		list = courseService.searchCourse(code, name);
		if (list.size() < 0) {
			model.addAttribute("msg", "There is no user with this data");
		} else {
			model.addAttribute("courses", list);
		}
		return "display-course";
	}

}
