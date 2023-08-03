package com.blogging.controller;

import com.blogging.payload.CommentsDto;
import com.blogging.service.CommentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentsController {
    private CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentsDto> createComment(@PathVariable(value =
            "postId") long postId, @RequestBody CommentsDto commentsDto){
        return new ResponseEntity<>(commentsService.createComment(postId,
                commentsDto), HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentsDto> getCommentsByPost(@PathVariable(value = "postId") Long postId){
       return commentsService.getCommentsByPostId(postId);
    }
    //http://localhost:8080/api/posts/3/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentsDto> getCommentsById(@PathVariable(value = "postId") Long postid, @PathVariable(value = "id") Long commentsId){
        CommentsDto commentsDto = commentsService.getCommentsById(postid, commentsId);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/3/comments/1
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentsDto>updateComment(
            @PathVariable(value = "postId")long postId,
            @PathVariable(value = "id") long id,
            @RequestBody CommentsDto commentsDto){
        CommentsDto commentsDto1 = commentsService.updateCommentById(postId, id, commentsDto);
        return new ResponseEntity<>(commentsDto1, HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long id){
        commentsService.deleteCommentById(postId, id);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }
}



