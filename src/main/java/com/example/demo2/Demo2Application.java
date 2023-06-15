package com.example.demo2;


import java.util.Timer;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Demo2Application implements CommandLineRunner {
	  @Resource
	  FilesStorageService storageService;
	  
	  @Autowired
	  ValidationChecker validationChecker;
	  
	  @Autowired
	  DealRepository dealRepository;
	  

	public static void main(String[] args)  {
		SpringApplication.run(Demo2Application.class, args);
	}
	
	@Override
	public void run(String... arg) throws Exception {
		
		//Delete all entries in database
		dealRepository.deleteAll();
		
		//Create entries for the "template" of the website
		dealRepository.save(new Deal("Extented warranty for Apple products","https://www.apple.com","mac.jpg","bWFjLmpwZw=="));
		dealRepository.save(new Deal("Flash sales","https://www.nike.com","nike.jpg","bmlrZS5qcGc="));
		dealRepository.save(new Deal("20% discount","https://www.darty.com","lave.jpg","bGF2ZS5qcGc="));
		dealRepository.save(new Deal("Buy one get two","https://www.amazon.com","montre.jpg","bW9udHJlLmpwZw=="));
		
		//Call the validator checker every 10 secondes
		Timer timer = new Timer();
		timer.schedule(validationChecker, 0, 10000);
	}

}


