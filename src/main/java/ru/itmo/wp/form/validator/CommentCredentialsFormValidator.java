package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.PostService;

@Component
public class CommentCredentialsFormValidator implements Validator {
    private final PostService postService;

    public CommentCredentialsFormValidator(PostService postService) {
        this.postService = postService;
    }

    public boolean supports(Class<?> clazz) {
        return CommentCredentialsFormValidator.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            CommentCredentials enterForm = (CommentCredentials) target;
            /*
            if (postService.findPost(enterForm.getLogin(), enterForm.getPassword()) == null) {
                errors.rejectValue("text", "comment.invalid-login-or-password", "invalid login or password");
            }
            */
        }
    }
}
