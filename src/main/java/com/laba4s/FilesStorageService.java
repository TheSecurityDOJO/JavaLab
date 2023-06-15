package com.laba4s;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	  public void init();

	  public void save(MultipartFile file) throws Exception;
	  
	  public void transfertTo(String file);

	  public Resource load(String filename);

	  public void deleteAll();

	  public Stream<Path> loadAll();
	}
