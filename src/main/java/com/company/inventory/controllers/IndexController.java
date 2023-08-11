package com.company.inventory.controllers;



import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {
	
	@GetMapping({"/", "", "/index"})
	public String helloWorld() {
		System.out.print("Version: " + SpringVersion.getVersion());
		return "hello world";
	}
}
