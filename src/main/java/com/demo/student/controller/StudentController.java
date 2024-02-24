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

import com.demo.student.dto.Course;
import com.demo.student.dto.Student;
import com.demo.student.model.StudentBean;
import com.demo.student.service.CourseService;
import com.demo.student.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	CourseService courseService;
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.GET)
	public ModelAndView setupAddStudent(ModelMap model) {
		
		StudentBean studentBean = new StudentBean();
		
		return new ModelAndView("add-student","student",studentBean);
	}
//	
	@RequestMapping(value = "/addStudent", method=RequestMethod.POST)
	public String addStudent(@ModelAttribute("student") @Validated StudentBean studentBean,BindingResult bindingResult ,ModelMap model) {
		if(bindingResult.hasErrors()) {
			return "add-studetalia@gmail.comnt";
		}
		
		//add all to student
		Student stu = new Student();
		stu.setName(studentBean.getName());
		stu.setDob(studentBean.getDob());
		stu.setGender(studentBean.getGender());
		stu.setPhone(studentBean.getPhone());
		stu.setEducation(studentBean.getEducation());

		List<Course> courseList = new ArrayList<>();
	
		for(String courseName : studentBean.getCourses()) {
			Course course = courseService.findByName(courseName);
			if(course != null) {
				courseList.add(course);
			}
		}
		stu.setCourses(courseList);
		Student student = studentService.addStudent(stu);
		return "redirect:/displayStudent";

	}
	
	@RequestMapping(value = "/displayStudent", method = RequestMethod.GET)
	public String displayStudent(ModelMap model,HttpServletRequest req) {
		
		List<Student> studentList = studentService.selectAll();
		
		model.addAttribute("stuList",studentList);
		
		model.addAttribute("error",req.getParameter("error"));
		return "display-student";
	}
	
	@RequestMapping(value ="/updateStudent", method = RequestMethod.GET)
	public ModelAndView setupUpdateStudent(@RequestParam("id") int id) {
		
		Student student = studentService.findById(id);
		List<String> stuCourseNames = new ArrayList<>();
		if(student.getCourses() != null) {
			for(Course course : student.getCourses()) {
				stuCourseNames.add(course.getName());
			}
		}
		
		//add to stubean
		StudentBean studentBean = new StudentBean();
		studentBean.setId(student.getId());
		studentBean.setCode(student.getCode());
		studentBean.setName(student.getName());
		studentBean.setDob(student.getDob());
		studentBean.setGender(student.getGender());
		studentBean.setEducation(student.getEducation());
		studentBean.setPhone(student.getPhone());
		studentBean.setCourses(stuCourseNames);

		return new ModelAndView("update-student","studentBean",studentBean);
	}
	
	@RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
	public String updateStudent(@ModelAttribute("studentBean") @Validated StudentBean studentBean, BindingResult bindingResult, ModelMap model ) {
		
		if(bindingResult.hasErrors()) {
			return "update-student";
		}
		
		List<Course> courseList = new ArrayList<>();
	
		for(String courseName : studentBean.getCourses()) {
			Course course = courseService.findByName(courseName);
			courseList.add(course);
		}
		
		//add to stu 
		Student stu = new Student();
		stu.setId(studentBean.getId());
		stu.setCode(studentBean.getCode());
		stu.setName(studentBean.getName());
		stu.setDob(studentBean.getDob());
		stu.setGender(studentBean.getGender());
		stu.setPhone(studentBean.getPhone());
		stu.setEducation(studentBean.getEducation());
		stu.setCourses(courseList);
		
		//student.setCourses(courseList);
		Student student = studentService.updateStudent(stu);
		if(student == null) {
			System.out.println("st null");
			return "update-student";
		}
		
		return "redirect:/displayStudent";
	
	}
	
	@RequestMapping(value = "deleteStudent" , method = RequestMethod.GET)
	public String deleteStudent(@RequestParam("id") int id,ModelMap model) {
		Student stu = studentService.findById(id);
		if(stu != null) {
			studentService.deleteStudent(id);
		}
		return "redirect:/displayStudent";
	}
	
	@RequestMapping(value = "searchStudent", method = RequestMethod.POST)
	public String searchStudent(@RequestParam("code") String code,@RequestParam("name")String name, @RequestParam("courseName")String courseName,ModelMap model ) {
		if(code.isEmpty() && name.isEmpty() && courseName.isEmpty()) {
			model.addAttribute("stuList",studentService.findAll());
			return "display-student";
		}
		Course course = courseService.findByName(courseName);
		List<Student> studentList = studentService.searchStudent(code, name, course);
		
		model.addAttribute("stuList",studentList);
		return "display-student";
	}
	
	@ModelAttribute("courseNameList")
	public List<String> getCourseList(){
		List<String> courseNameList = new ArrayList<>();
		List<Course> list = courseService.findAll();
		
		for(Course course : list) {
			courseNameList.add(course.getName());
		}
		return courseNameList;
	}
	
	

	@ModelAttribute("eduList")
	List<String> getEduList(){
		List<String> eduList = new ArrayList<>();
		eduList.add("Bachelor of Information Technology");
		eduList.add("Diploma in IT");
		eduList.add("Bachelor of Computer Science");
		
		return eduList;
	}
	
	

}
