package com.example.Student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Student.entity.Student;
import com.example.Student.repo.StudentRepository;



@Controller
public class StudentController {
	
	@Autowired
	StudentRepository srepo;
	
	@PostMapping("/saveStudent")
	public String saveStudent(Student s)
	{
		srepo.save(s);
		
		return "redirect:/viewStudents";
		
	}
	
	   @GetMapping("/viewStudents")
	    public String viewStudents(@RequestParam(defaultValue = "0") int page, Model model) {

	        int size = 5; 

	        Page<Student> studpage = srepo.findAll(PageRequest.of(page, size));

	        model.addAttribute("students", studpage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", studpage.getTotalPages());

	        return "view";
	    }
	
	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable int id)
	{
		srepo.deleteById(id);
		return  "redirect:/viewStudents";
		
	}
	
	
	@GetMapping("/edit/{id}")
	public String editStudent(@PathVariable int id,Model model)
	{
		Student s=srepo.findById(id).get();
		model.addAttribute("student",s);
		return "edit";
		
	}
	
	
	
	
	@PostMapping("/updateStudent")
	public String updateStudent(Student s)
	{
		srepo.save(s);
		return "redirect:/viewStudents";
		
	}
	
	
	@GetMapping("/search")
    public String searchStudent(@RequestParam String keyword, Model model) {

        List<Student> list =
            srepo.findByNameContainingOrEmailContainingOrCourseContaining(
                keyword, keyword, keyword);

        model.addAttribute("students", list);
        return "view";
    }
 
}
