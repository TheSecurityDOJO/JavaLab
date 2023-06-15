package com.example.demo2;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
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
			JsonNode jsonNode = mapper.readTree(inputContact);

			// Vérifier les champs obligatoires
			if (jsonNode.has("name") && jsonNode.has("email")) {
				String name = jsonNode.get("name").asText();
				String email = jsonNode.get("email").asText();
				if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
					Contact contact = mapper.readValue(inputContact, Contact.class);

					// TO DO ...

					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
