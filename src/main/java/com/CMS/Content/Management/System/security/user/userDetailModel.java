package com.CMS.Content.Management.System.security.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Getter
@Setter
public class userDetailModel implements UserDetails {

    private static  final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean enabled;
    private String authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        if (authority == null || authority.isEmpty()){
            return Collections.emptyList();
        }
        return Arrays.stream(authority.split(" "))
                .map(role -> role.startsWith("ROLE_") ? role :"ROLE_" + role)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;

    }
    @Override
    public boolean isCredentialsNonExpired(){
        return  true;
    }
    @Override
    public boolean isEnabled(){
        return  true;
    }
}
