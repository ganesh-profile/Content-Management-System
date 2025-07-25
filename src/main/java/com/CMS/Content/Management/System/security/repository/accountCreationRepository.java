package com.CMS.Content.Management.System.security.repository;

import com.CMS.Content.Management.System.security.user.userDetailModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface accountCreationRepository {
    public abstract int createUserAccount(userDetailModel userDetailModel);
    public abstract int createAdminAccount(userDetailModel userDetailModel);
}
