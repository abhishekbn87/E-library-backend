package com.ebooklibrary.ebooklibrary.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String bookId;
    private String bookName;
    private String author;
    private String bookDescription;
    private String downloadLink;
    private String bookArt;
}
