package com.example.demo2;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ContactController {
	
	@PostMapping("/contact")
	public ResponseEntity<String> Contact(@RequestBody String inputContact) throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Contact contact = mapper.readValue(inputContact, Contact.class);
			
			// TO DO ...
			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}

}
