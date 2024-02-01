package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.validator.CommentCredentialsFormValidator;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page{

    private final PostService postService;
    private final CommentService commentService;
    private final CommentCredentialsFormValidator commentCredentialsFormValidator;

    public PostPage(PostService postService, CommentService commentService, CommentCredentialsFormValidator commentCredentialsFormValidator) {
        this.postService = postService;
        this.commentService = commentService;
        this.commentCredentialsFormValidator = commentCredentialsFormValidator;
    }

    /*
    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(commentCredentialsFormValidator);
    }
     */

    @GetMapping("/post/{id}")
    public String postGet(Model model, @PathVariable("id") String id) {
        try {
            model.addAttribute("post",postService.findPost(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            return "PostPage";
        }
        model.addAttribute("form",new CommentCredentials());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String postPost(Model model, @PathVariable("id") long id, @Valid @ModelAttribute("form") CommentCredentials form,
                       BindingResult bindingResult,
                       HttpSession httpSession) {
        model.addAttribute("post",postService.findPost(id));
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        try {
            Post post = postService.findPost(id);
            postService.writeComment(post, commentService.save(form,post,getUser(httpSession)));
            putMessage(httpSession, "The comment has been added!");
        } catch (NumberFormatException e) {
            return "PostPage";
        }

        return "redirect:/";
    }
}
