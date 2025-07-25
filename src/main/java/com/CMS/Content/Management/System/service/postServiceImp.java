package com.CMS.Content.Management.System.service;

import com.CMS.Content.Management.System.model.Post;
import com.CMS.Content.Management.System.repository.postMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class postServiceImp implements postService {

    private postMapper postMapper;

    @Override
    public int postInsertion(Post post){
        return postMapper.postInsertion(post);
    }

    @Override
    public List<Post> getAllPosts(){
        return postMapper.getAllPosts();
    }

    @Override
    public Optional<Post> getPostById(int id){
        return postMapper.getPostById(id);
    }

    @Override
    public int deletePost(int id){
        return postMapper.deletePost(id);
    }

    @Override
    public int updatePost(Post post){
        return postMapper.updatePost(post);
    }

    @Override
    public List<Post> searchPosts(String query){
        return postMapper.searchPosts(query);
    }


}
