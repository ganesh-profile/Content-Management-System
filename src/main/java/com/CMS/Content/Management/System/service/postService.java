package com.CMS.Content.Management.System.service;

import com.CMS.Content.Management.System.model.Post;

import java.util.List;
import java.util.Optional;

public interface postService {
    public abstract List<Post> getAllPosts();
    public abstract int postInsertion(Post post);
    public abstract int deletePost(int id);
    public abstract Optional<Post> getPostById(int id);
    public abstract int updatePost(Post post);
    public abstract List<Post> searchPosts(String query);

}
