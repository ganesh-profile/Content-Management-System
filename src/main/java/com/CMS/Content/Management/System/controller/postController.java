package com.CMS.Content.Management.System.controller;

import com.CMS.Content.Management.System.model.Post;
import com.CMS.Content.Management.System.security.user.userDetailServiceImp;
import com.CMS.Content.Management.System.service.postServiceImp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class postController {

    private final postServiceImp postServiceImp;

    private final userDetailServiceImp userDetailServiceImp;

    private Logger LOGGER = LoggerFactory.getLogger(postController.class);

    public ModelAndView getAllPosts(){
        ModelAndView modelAndView = new ModelAndView();
        List<Post> posts = postServiceImp.getAllPosts();
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName("posts");
        String curr_user = userDetailServiceImp.getCurrentUser();
        System.out.println("current User" + curr_user);

        LOGGER.info("Scratching around the collections all posts");
        return modelAndView;
    }

}
