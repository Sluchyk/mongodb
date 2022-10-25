package com.example.mongodb.entity.dto;

import com.example.mongodb.entity.Category;
import com.example.mongodb.entity.Description;
import lombok.Data;

@Data
public class BookDtoToSave {
    private String id;
    private String name;
    private String author;
    private Category category;
    private Description description;
}
