package com.CMS.Content.Management.System.security.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface useDetailRepository {
    public abstract Optional<userDetailModel> loginProcess(@Param(name = "username") String username);

}
