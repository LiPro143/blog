package com.blogging.service.impl;

import com.blogging.entity.Comments;
import com.blogging.entity.Post;
import com.blogging.exception.ResourcesNotFoundException;
import com.blogging.payload.CommentsDto;
import com.blogging.repository.CommentsRepository;
import com.blogging.repository.PostRepository;
import com.blogging.service.CommentsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {

    private PostRepository postRepository;
    private CommentsRepository commentsRepository;

    public CommentsServiceImpl(PostRepository postRepository, CommentsRepository commentsRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
    }

    @Override
    public CommentsDto createComment(long postId, CommentsDto commentsDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("post not found with id:" + postId)
        );
        Comments comments = mapToEntity(commentsDto);
        comments.setPost(post);
        Comments newComment = commentsRepository.save(comments);
        CommentsDto dto = mapToDto(newComment);
        return dto;
    }

    @Override
    public List<CommentsDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("the post is not found with id:" + postId)
        );
        List<Comments> comments = commentsRepository.findByPostId(postId);
       return comments.stream().map(cmnt -> mapToDto(cmnt)).collect(Collectors.toList()) ;
    }

    @Override
    public CommentsDto getCommentsById(Long postId, Long commentsId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("the post is not found with id:" + postId)
        );
        Comments comments = commentsRepository.findById(commentsId).orElseThrow(
                () -> new ResourcesNotFoundException("comment not found with this id:" + commentsId)
        );
        return mapToDto(comments);
    }

    @Override
    public CommentsDto updateCommentById(long postId, long id, CommentsDto commentsDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("post not found with id:" + postId)
        );
        Comments comments = commentsRepository.findById(id).orElseThrow(
                () -> new ResourcesNotFoundException("comment not found with id:" + id)
        );
         comments.setName(commentsDto.getName());
         comments.setEmail(commentsDto.getEmail());
         comments.setBody(commentsDto.getBody());
        Comments comments1 = commentsRepository.save(comments);
        CommentsDto commentsDto1 = mapToDto(comments1);
        return commentsDto1;
    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("post not found with id:"+postId)
        );
        Comments comments = commentsRepository.findById(id).orElseThrow(
                () -> new ResourcesNotFoundException("comment not found with id:" + id)
        );
        commentsRepository.deleteById(id);
    }


    private CommentsDto mapToDto(Comments comments){
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(comments.getId());
        commentsDto.setName(comments.getName());
        commentsDto.setEmail(comments.getEmail());
        commentsDto.setBody(comments.getBody());
        return commentsDto;
    }
    private Comments mapToEntity(CommentsDto commentsDto){
        Comments comments = new Comments();
        comments.setId(commentsDto.getId());
        comments.setName(commentsDto.getName());
        comments.setEmail(commentsDto.getEmail());
        comments.setBody(commentsDto.getBody());
        return comments;
    }
}
