package com.blogging.repository;

import com.blogging.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    //create custom methods
    List<Comments>findByPostId(long postId);

    //this method will be used as SQL query
}
