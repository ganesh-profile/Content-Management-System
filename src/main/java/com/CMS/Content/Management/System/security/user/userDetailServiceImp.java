package com.CMS.Content.Management.System.security.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class userDetailServiceImp implements UserDetailsService {

    private final userDetailRepository useDetailRepository;

    Logger logger = LoggerFactory.getLogger(userDetailServiceImp.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        userDetailModel userDetailModel = useDetailRepository.loginProcess(username).orElseThrow(() -> {
            logger.error("User not found with username; {} ", username);
            return new UsernameNotFoundException("User not found with username: " + username);
        });

        return User.withUsername(userDetailModel.getUsername())
                .password(userDetailModel.getPassword())
                .authorities(userDetailModel.getAuthorities())
                .accountExpired(!userDetailModel.isEnabled())
                .accountLocked(false)
                .build();
    }

    public String getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( authentication != null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();
            if (principal instanceof userDetailModel){
                return ((userDetailModel) principal).getUsername();

            }else{
                return principal.toString();
            }
        }
        return null;
    }
}
