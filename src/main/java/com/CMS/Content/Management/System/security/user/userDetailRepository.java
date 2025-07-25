package com.CMS.Content.Management.System.security.user;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;


@Mapper
public interface userDetailRepository {
    public abstract Optional<userDetailModel> loginProcess(@Param("username") String username);
}