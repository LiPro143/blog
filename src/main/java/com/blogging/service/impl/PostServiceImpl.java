package com.blogging.service.impl;

import com.blogging.entity.Post;
import com.blogging.exception.ResourcesNotFoundException;
import com.blogging.payload.PostDto;
import com.blogging.repository.PostRepository;
import com.blogging.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        //dto to entity id is not compulsory
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepository.save(post);

        //entity to dto id is compulsory
        PostDto dto=new PostDto();
        dto.setId(newPost.getId());
        dto.setTitle(newPost.getTitle());
        dto.setDescription(newPost.getDescription());
        dto.setContent(newPost.getContent());

        return dto;
    }

    @Override
    public List<PostDto> listAllPosts(int pageNo, int pazeSize, String sortBy, String sortDir) {

       Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        //covert the string into sort
        //Sort sort = Sort.by(sortBy);

        Pageable pageable = PageRequest.of(pageNo, pazeSize, sort);
        Page<Post> listOfPosts = postRepository.findAll(pageable);

        //getting the page content into list
        List<Post> posts = listOfPosts.getContent();

        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post= postRepository.findById(id).orElseThrow(
                ()->new ResourcesNotFoundException("Post not found with id:"+id)
                //whenever we want throw custom exceptions we can use lamdas expression
        );
           return mapToDto(post);

    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        //always we need check that the post exists or not
        Post post= postRepository.findById(id).orElseThrow(
                ()->new ResourcesNotFoundException("Post not found with id:"+id)
        );
        Post newPost = mapToEntity(postDto);
        newPost.setId(id);
        Post updatedPost = postRepository.save(newPost);
        PostDto dto = mapToDto(updatedPost);
        return dto;
    }

    @Override
    public void deletePost(long id) {
        Post post= postRepository.findById(id).orElseThrow(
                ()->new ResourcesNotFoundException("Post not found with id:"+id)
        );
        postRepository.deleteById(id);
    }

    //convert entity to dto
    PostDto mapToDto(Post post){
        //conversion using modelMapper
        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;

    }

    //convert dto to entity
    Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;

    }
}
