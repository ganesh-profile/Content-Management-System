package com.CMS.Content.Management.System.service;

import com.CMS.Content.Management.System.model.Post;
import com.CMS.Content.Management.System.repository.postMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class postServiceImp implements postService {

    private postMapper postMapper;

    @Override
    public List<Post> getAllPosts(){
        return postMapper.getAllPosts();
    }


}
