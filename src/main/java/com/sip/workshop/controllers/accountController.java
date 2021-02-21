package com.sip.workshop.controllers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sip.workshop.entities.Role;
import com.sip.workshop.entities.User;
import com.sip.workshop.repository.userRepository;
import com.sip.workshop.repository.roleRepository;

@Controller
@RequestMapping("/account")
public class accountController {

	private final userRepository uR;
	private final roleRepository rR;
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	accountController(userRepository uR, roleRepository rR) {
		this.uR = uR;
		this.rR = rR;
	}

	@GetMapping("/list")
	public String listAccounts(Model model) {
		List<User> lu = (List<User>) uR.findAll();
		int nbr = (int) uR.count();
		if (lu.size() == 0) {
			lu = null;
		}
		model.addAttribute("users", lu);
		model.addAttribute("nbr", nbr);
		return "user/listUser";

	}

	@GetMapping("enable/{id}")
	// @ResponseBody
	public String enableUserAcount(@PathVariable("id") int id) {

		User user = uR.findById(id).orElseThrow(() -> new

		IllegalArgumentException("Invalid User Id:" + id));
		user.setActive(1);
		uR.save(user);
		return "redirect:../list";
	}

	@GetMapping("disable/{id}")
	// @ResponseBody
	public String disableUserAcount(@PathVariable("id") int id) {

		User user = uR.findById(id).orElseThrow(() -> new

		IllegalArgumentException("Invalid User Id:" + id));
		user.setActive(0);
		uR.save(user);
		return "redirect:../list";
	}

	@PostMapping("updateRole")
	public String UpdateUserRole(@RequestParam("id") int id, @RequestParam("newrole") String newRole) {
		User user = uR.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
		Role userRole = rR.findByRole(newRole);
		//HashSet<Role> H=new HashSet<Role>(Arrays.asList(userRole));
		HashSet<Role> H = (HashSet<Role>)user.getRoles();
		H.add(userRole);
		user.setRoles(H);
		uR.save(user);
		return "redirect:list";
	}

	void sendEmail(String email, boolean state) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		if(state == true)
		{
		msg.setSubject("Account Has Been Activated");
		msg.setText("Hello, Your account has been activated. " +"You can log in : http://127.0.0.1:81/login" + " \n Best Regards!");
		}
		else
		{
		msg.setSubject("Account Has Been disactivated");
		msg.setText("Hello, Your account has been disactivated.");
		}
		javaMailSender.send(msg);
		}

}
