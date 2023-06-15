package com.laba4s;


import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ValidationChecker extends TimerTask {
		
	@Autowired
	DealRepository dealRepository;
	
	@Autowired
	FilesStorageService storageService;
	
	public void run() {
		try {
			//Get all pending deals
			List<Deal> deals = new ArrayList<Deal>();
			dealRepository.findAll().forEach(deals::add);
			
			
			//Check if the name of the image is equals to the Hash, if not you try to hack and we delete it!
			
			if(!deals.isEmpty()) {
				for(Deal deal : deals) {
					if(!StringUtils.isBlank(deal.getValidated())) {
						Base64 base64 = new Base64();
						String checkedString = new String(base64.encodeToString(deal.getName().getBytes()));
						if(checkedString.equals(deal.getValidated())) {
							storageService.transfertTo(deal.getName());
						}
					}
				}
			}
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
