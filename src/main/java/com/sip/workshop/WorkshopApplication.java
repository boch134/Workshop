package com.sip.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


import com.sip.workshop.controllers.articleController;

import java.io.File;
@SpringBootApplication
public class WorkshopApplication extends SpringBootServletInitializer {

	public static void main(String[] args)  {
		new File(articleController.uploadDirectory).mkdir();
		SpringApplication.run(WorkshopApplication.class, args);
	}

}
