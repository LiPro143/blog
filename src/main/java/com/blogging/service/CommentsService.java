package com.blogging.service;

import com.blogging.entity.Comments;
import com.blogging.payload.CommentsDto;

import java.util.List;

public interface CommentsService {
   CommentsDto createComment(long postId, CommentsDto commentsDto);
   List<CommentsDto> getCommentsByPostId(long postId);
  CommentsDto getCommentsById(Long postId, Long commentsId);


    CommentsDto updateCommentById(long postId, long id, CommentsDto commentsDto);

    void deleteCommentById(long postId, long id);
}
