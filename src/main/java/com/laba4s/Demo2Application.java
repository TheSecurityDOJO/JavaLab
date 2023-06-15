package com.laba4s;


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
		
		
		//Call the validator checker every 10 secondes
		Timer timer = new Timer();
		timer.schedule(validationChecker, 0, 10000);
	}

}


