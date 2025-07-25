package com.CMS.Content.Management.System.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionhandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionhandler.class);

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<Object> handleFileUploadException(FileUploadException exception, WebRequest request){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "File Upload Error");
        body.put("message", exception.getMessage());
        LOGGER.error("File upload failed: {}", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException ex, WebRequest webRequest){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "File Upload Error");
        body.put("message", ex.getMessage());
        LOGGER.error("File upload failed: {}", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthenticationException(AuthenticationException ex, Model model, RedirectAttributes redirectAttributes){
        LOGGER.error("Authentication failed : {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", "Authentication failed: "+ ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAuthorizationException(AuthorizationException ex, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.error("Authorization failed: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", "Authorization failed: " + ex.getMessage());
        return "redirect:/access-denied";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        LOGGER.error("An unexpected error occurred: {}", ex.getMessage());
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return "error/500";
    }


}
