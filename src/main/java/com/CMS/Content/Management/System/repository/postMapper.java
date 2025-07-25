package com.CMS.Content.Management.System.repository;

import com.CMS.Content.Management.System.model.Post;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface postMapper {
    public abstract List<Post> getAllPosts();
}
