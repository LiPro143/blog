package com.blogging.payload;

import lombok.Data;

@Data
public class CommentsDto {
    private Long id;

    private String body;

    private String name;

    private String email;
}
