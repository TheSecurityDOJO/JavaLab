package com.example.demo2;



import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletResponse;


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


@Controller
public class DealController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FilesStorageService storageService;
	
	
	@Autowired
	DealRepository dealRepository;
    
	 @GetMapping("/deals")
	  public ResponseEntity<List<Deal>> getAllDeals(HttpServletResponse response) {
	    
		 try {
	      List<Deal> deals = new ArrayList<Deal>();
	      List<Deal> dealsValidated = new ArrayList<Deal>();
	      
	      dealRepository.findAll().forEach(deals::add);      
	      

	      if (deals.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      
	      for(Deal d : deals) {
	    	  if(!StringUtils.isBlank(d.getValidated())) {
	    		  Base64 base64 = new Base64();
		    	  String checkedString = new String(base64.encodeToString(d.getName().getBytes()));
		    	  if(checkedString.equals(d.getValidated())) {
						dealsValidated.add(d);
					}
	    	  }
	    	  
	      }
	      return new ResponseEntity<>(dealsValidated, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	 

	 
	  @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
	  public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("infos") Deal deal){
		  try {
			  String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			    if(!extension.equals("jpg")) {
			    	return new ResponseEntity<>("Error!",HttpStatus.BAD_REQUEST);
			    }
			    
			    storageService.save(file);
			    deal.setName(file.getOriginalFilename());
			    dealRepository.save(deal);

			    
			    return new ResponseEntity<>("Deal saved!",HttpStatus.OK);
		  }catch (Exception e) {
			  return new ResponseEntity<>(ExceptionUtils.getStackTrace(e),HttpStatus.resolve(500));
		  }
	  }
	  

    

}
