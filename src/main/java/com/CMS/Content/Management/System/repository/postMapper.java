package com.CMS.Content.Management.System.repository;

import com.CMS.Content.Management.System.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Optional;

@Mapper
public interface postMapper {
    public abstract List<Post> getAllPosts();
    public abstract int postInsertion(Post post);
    public abstract Optional<Post> getPostById(int id);
    public abstract int deletePost(int id);
    public abstract int updatePost(Post post);
    public abstract List<Post> searchPosts(@Param("query") String query);

}
