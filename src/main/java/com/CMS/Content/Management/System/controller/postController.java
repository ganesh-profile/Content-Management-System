package com.CMS.Content.Management.System.controller;

import com.CMS.Content.Management.System.exception.FileUploadException;
import com.CMS.Content.Management.System.exception.PostNotFoundException;
import com.CMS.Content.Management.System.filehandler.multiPartFileHandler;
import com.CMS.Content.Management.System.model.Post;
import com.CMS.Content.Management.System.security.user.userDetailServiceImp;
import com.CMS.Content.Management.System.service.postServiceImp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class postController {

    private final postServiceImp postServiceImp;

    private final multiPartFileHandler multiPartFileHandler;

    private final userDetailServiceImp userDetailServiceImp;

    private Logger LOGGER = LoggerFactory.getLogger(postController.class);

    @RequestMapping( value = {"/posts", "/"}, method = RequestMethod.GET)
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

    @RequestMapping( value = "/createNewPost" , method = RequestMethod.GET)
    public ModelAndView insertIntoPosts() {
        ModelAndView modelAndView = new ModelAndView();
        Post post = new Post();
        modelAndView.addObject("post", post);
        modelAndView.setViewName("createNewPost");
        return modelAndView;
    }

    @RequestMapping( value = "/saveNewPosts", method = RequestMethod.POST)
    public ModelAndView saveNewPost(@ModelAttribute Post postToBeSaved, RedirectAttributes redirectAttributes, @RequestParam("imageFile")MultipartFile multipartFile){

        ModelAndView modelAndView = new ModelAndView();

        String imgUrl = multiPartFileHandler.saveFile(multipartFile);

        if( imgUrl == null){
            throw new FileUploadException("Error uploading image file.");
        }
        postToBeSaved.setImageURL(imgUrl);

        int result = postServiceImp.postInsertion(postToBeSaved);

        if (result > 0){
            redirectAttributes.addFlashAttribute("SuccessMessage", "Post Created unblemishedly");
            LOGGER.info("Post created : {}" + postToBeSaved.getId());
        }else{
            redirectAttributes.addFlashAttribute("clutteredMessage", "Post failed to be created successfully");
            LOGGER.error("Post failed to be created successfully : {}" + postToBeSaved.getId());

        }
        modelAndView.setViewName("redirect:/posts");
        return modelAndView;
    }

    @RequestMapping(value = "/posts/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePost(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();

        Optional<Post> op_Post = postServiceImp.getPostById(id);

        if (!op_Post.isPresent()){
            throw new PostNotFoundException("Post not found with ID; " + id);
        }

        int result = postServiceImp.deletePost(id);
        if(result > 0){
            redirectAttributes.addFlashAttribute("SuccessMessage", "Post Deleted Successfully");
            LOGGER.info("Post Deleted Successfully : {}", id);
        }else{
            redirectAttributes.addFlashAttribute("clutteredMessage", "Post failed to be deleted successfully");
            LOGGER.error("Post failed to be Deleted successfully: {}",id);
        }

        modelAndView.setViewName("redirect:/posts");
        return modelAndView;

    }

    @RequestMapping( value = "/posts/edit/{id}" , method = RequestMethod.GET)
    public ModelAndView editPost(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();

        Post postToBeUpdated =  postServiceImp.getPostById(id).orElseThrow(() -> new PostNotFoundException("Post with ID " + id + " not found."));

        modelAndView.addObject("post", postToBeUpdated);
        modelAndView.setViewName("updatePost");
        return modelAndView;
    }

    @RequestMapping( value = "/update" , method = RequestMethod.POST)
    public ModelAndView updatePost(@ModelAttribute Post post , RedirectAttributes redirectAttributes , @RequestParam("imageFile") MultipartFile multipartFile ) {
        ModelAndView modelAndView = new ModelAndView();

        if( !multipartFile.isEmpty()) {
            String imgUrl = multiPartFileHandler.saveFile(multipartFile);
            if( imgUrl != null ) {
                post.setImageURL(imgUrl);
            }
            else {
                redirectAttributes.addFlashAttribute("clutteredMessage", "Failed to upload image file");
                LOGGER.warn("Failed to upload new image for post: {}", post.getId());
                modelAndView.setViewName("redirect:/posts");
                return modelAndView;
            }
        }

        int result =  postServiceImp.updatePost(post);
        if( result > 0 ) {
            redirectAttributes.addFlashAttribute("SuccessMessage", "Posts Updated Successfully!" );
            LOGGER.info("Posts Updated Successfully : {}" , post.getId());
        }
        else {
            redirectAttributes.addFlashAttribute("clutteredMessage", "Posts slugishingly Failed to update" );
            LOGGER.error("Posts slugishingly Failed to update : {}" ,post.getId());
        }

        modelAndView.setViewName("redirect:/posts");
        return modelAndView;
    }

    @RequestMapping(value = "/posts/search" , method = RequestMethod.GET)
    public ModelAndView searchPosts(@RequestParam(name = "query") String query) {
        ModelAndView modelAndView = new ModelAndView();
        List<Post> filteredPosts = postServiceImp.searchPosts(query);

        // two enrapturing options => for filtering out and searching lists of items
        // directly embadded with Mybatis (dynamic logic) to perform filtering in db, for better sustainable optimization( good for large datasets)
//	  List<Post> current_posts = postServiceImp.getAllPosts();
//	  List<Post> filteredPosts = current_posts.stream().filter(post -> post.getTitle().toLowerCase().contains(query) ||
//			  															String.valueOf(post.getId()).toLowerCase().contains(query) ||
//			  															post.getContent().toLowerCase().contains(query))
//			  															.collect(Collectors.toList());
        modelAndView.addObject("posts", filteredPosts);
        modelAndView.setViewName("posts");
        return modelAndView;
    }

}
