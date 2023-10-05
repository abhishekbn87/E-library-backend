package com.ebooklibrary.ebooklibrary.services;

import com.ebooklibrary.ebooklibrary.Entities.Author;
import com.ebooklibrary.ebooklibrary.Entities.Book;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class BookService {

    public Book GetBook(String bookId) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection("books").document(bookId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        if(documentSnapshot.exists())
            return documentSnapshot.toObject(Book.class);
        return null;
    }

    public List<Book> GetAllBooks() throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = dbFireStore.collection("books").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Book> books = new ArrayList<Book>();
        for(DocumentSnapshot document: documents)
            books.add(document.toObject(Book.class));
        return books;

    }

    public Author GetAuthorMetadata(String author){
        String apiUrl = "https://en.wikipedia.org/w/api.php";
        String charset = "UTF-8";
        try {
            String query = String.format("action=query&format=json&prop=extracts|pageimages&exintro=true&explaintext=true&titles=%s",
                    URLEncoder.encode(author, charset));

            URL url = new URL(apiUrl + "?" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse JSON response to get the summary and thumbnail image URL
                    String jsonString = response.toString();
                    int startIndexSummary = jsonString.indexOf("\"extract\":\"");
                    int endIndexSummary = jsonString.indexOf("\"}", startIndexSummary);
                    int startIndexImage = jsonString.indexOf("\"thumbnail\":{\"source\":\"");
                    int endIndexImage = jsonString.indexOf("\"", startIndexImage + 23);

                    if (startIndexSummary >= 0 && endIndexSummary > startIndexSummary
                            && startIndexImage >= 0 && endIndexImage > startIndexImage + 23) {
                        String summary = jsonString.substring(startIndexSummary + 11, endIndexSummary);
                        String imageUrl = jsonString.substring(startIndexImage + 23, endIndexImage);
                        return new Author(summary, imageUrl.replace("50px", "300px"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public String AddBook(Book book) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = dbFireStore.collection("books").document(book.getBookId()).set(book);
        return future.get().getUpdateTime().toString();

    }
}
