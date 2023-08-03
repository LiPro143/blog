package com.blogging.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min=2, message = "title should be more than 2 character")
    private String title;
    private String description;
    private String content;
}
