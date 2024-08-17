package com.meheedihasaan.springbootcache.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateBookRequest {

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Author is required.")
    private String author;

    @NotBlank(message = "ISBN is required.")
    private String isbn;
}
