package cl.inventario.sb.app.Inventarioproductos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {
	
	@GetMapping({"/", "", "/index"})
	public String helloWorld() {
		
		return "hello world";
	}
}
