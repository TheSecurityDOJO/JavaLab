package com.laba4s;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  private final Path root = Paths.get("/opt/app/static/validated");
  private final Path rootTmp = Paths.get("/opt/app/tmp");

  @Override
  public void init() {
    try {
      Files.createDirectory(root);
      Files.createDirectory(rootTmp);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file) throws Exception{
	  String canonical = ((this.rootTmp.resolve(file.getOriginalFilename())).toFile()).getCanonicalPath();
	  if(canonical.startsWith(this.rootTmp.toString())) {
		  Files.copy(file.getInputStream(), this.rootTmp.resolve(file.getOriginalFilename()));
	  }
  }
  
  @Override
  public void transfertTo(String file) {
	  try {
		  if(Files.exists(this.rootTmp.resolve(file)) && !Files.exists(this.root.resolve(file))){
			  Files.move(this.rootTmp.resolve(file), this.root.resolve(file));
		  }
	  }catch( Exception e) {
		  throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	  }
  }
    

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
    FileSystemUtils.deleteRecursively(rootTmp.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }
}
