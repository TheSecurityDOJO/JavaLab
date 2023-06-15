package com.laba4s;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import pojo.DealPojo;


@Controller
public class DealController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FilesStorageService storageService;
	
	@Autowired
	DealRepository dealRepository;
	    
	private static final String PATTERN = "([^\\s]+(\\.(?i)(jpg|png|jpeg))$)";
	
	 @GetMapping("/api/deals")
	  public ResponseEntity<List<DealPojo>> getAllDeals(HttpServletRequest request, HttpServletResponse response) {


		 HttpSession session = request.getSession();
		 String sessionId = session.getId();

		 
		 
		 try {
	      List<DealPojo> deals = new ArrayList<DealPojo>();
	      List<DealPojo> dealsValidated = new ArrayList<DealPojo>() {
	    	  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
	    		  add(new DealPojo("Extented warranty for Apple products","https://www.fr.sogeti.com/","mac.jpg","bWFjLmpwZw=="));
	    		  add(new DealPojo("Flash sales","https://www.fr.sogeti.com/","nike.jpg","bmlrZS5qcGc="));
	    		  add(new DealPojo("20% discount","https://www.fr.sogeti.com/","lave.jpg","bGF2ZS5qcGc="));
	    		  add(new DealPojo("Buy one get two","https://www.fr.sogeti.com/","montre.jpg","bW9udHJlLmpwZw=="));
	    	  }
	      };
	      

	      dealRepository.customFind(sessionId).forEach(deals::add);      
	      

	      if (!deals.isEmpty()) {
	    	  for(DealPojo d : deals) {
		    	  if(!StringUtils.isBlank(d.getValidated())) {
		    		  Base64 base64 = new Base64();
			    	  String checkedString = new String(base64.encodeToString(d.getName().getBytes()));
			    	  if(checkedString.equals(d.getValidated())) {
							dealsValidated.add(d);
						}
		    	  } 
		      }
	      }
	      return new ResponseEntity<>(dealsValidated, HttpStatus.OK);
	    } catch (Exception e) {
	    	System.out.println(e);
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	 
	 @GetMapping("/api/pendingDeals")
	  public ResponseEntity<List<DealPojo>> getPendingDeals(HttpServletRequest request) {


		 HttpSession session = request.getSession();
		 String sessionId = session.getId();

		 
		 
		 try {
	      List<DealPojo> deals = new ArrayList<DealPojo>();
	      List<DealPojo> dealsValidated = new ArrayList<DealPojo>();
	      

	      dealRepository.customFind(sessionId).forEach(deals::add);      
	      

	      if (!deals.isEmpty()) {
	    	  for(DealPojo d : deals) {
	    		  Base64 base64 = new Base64();
	    		  String checkedString = new String(base64.encodeToString(d.getName().getBytes()));
	    		  	if(!checkedString.equals(d.getValidated())) {
	    		  		dealsValidated.add(d);
	    		  		}
	    	  		}
	    	  }
	      return new ResponseEntity<>(dealsValidated, HttpStatus.OK);
	    } catch (Exception e) {
	    	System.out.println(e);
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	 

	 
	  @PostMapping(value = "/api/upload", consumes = {"multipart/form-data"})
	  public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("infos") Deal deal, HttpServletRequest request){
		  		  
		  try {
			  Pattern pattern = Pattern.compile(PATTERN);
			  Matcher matcher = pattern.matcher(file.getOriginalFilename());
			    if(!matcher.matches()) {
			    	return new ResponseEntity<>("Error!",HttpStatus.BAD_REQUEST);
			    }
			    
			    HttpSession session = request.getSession();
				String sessionId = session.getId();
				 
				 if(!StringUtils.isBlank(sessionId)) {
					 storageService.save(file);
					 deal.setName(file.getOriginalFilename());
					 deal.setSession(sessionId);
					 dealRepository.save(deal);
				 }
			    			    
			    return new ResponseEntity<>("Deal saved!",HttpStatus.OK);
		  }catch (Exception e) {
			  return new ResponseEntity<>(ExceptionUtils.getStackTrace(e),HttpStatus.resolve(500));
		  }
	  }
	  

    

}
