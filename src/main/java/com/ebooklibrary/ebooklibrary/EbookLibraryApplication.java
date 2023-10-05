package com.ebooklibrary.ebooklibrary;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.util.Objects;

@SpringBootApplication

public class EbookLibraryApplication {


	public static void main(String[] args) throws IOException {

//		ClassLoader classLoader = EbookLibraryApplication.class.getClassLoader();
//		File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
//		FileInputStream serviceAccount =
//				new FileInputStream(file.getAbsolutePath());
		ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
		InputStream inputStream = resource.getInputStream();

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(inputStream))
				.build();

		FirebaseApp.initializeApp(options);
		SpringApplication.run(EbookLibraryApplication.class, args);
	}



}
