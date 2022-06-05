package karazin.yarovoy.db.lab4.blog.controllers;

import karazin.yarovoy.db.lab4.blog.entities.Post;
import karazin.yarovoy.db.lab4.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Контроллер для "post" entity
 * */

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    /**
     * Get-request
     * Авторизация пользователя (spring security provided)
     *
     * Вернуть страничку security.html
     *
     * */

    @GetMapping("/login")
    public String loginPage() {
        return "security";
    }

    /**
     * Get-request
     * Получить коллекцию постов
     *
     * Внедрение аутентифицированного пользователя в
     * метод-обработчик Spring MVC (@AuthenticationPrincipal)
     *
     * */

    @GetMapping("/main")
    public String blogMain(@AuthenticationPrincipal Principal principal,
                           Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("authorized", Objects.nonNull(principal));
        model.addAttribute("posts", posts);
        return "list-post";
    }



    /**
     * Get-request
     * Отобразить пост полностью при его выборе в списках
     *
     * Находим model в db по id (pk) (если сущ-ет) и работаем
     *
     * Внедрение аутентифицированного пользователя в
     * метод-обработчик Spring MVC (@AuthenticationPrincipal)
     *
     * Переадресация на страницу about-post.html
     *
     * */


    @GetMapping("/main/{post_id}")
    public String blogDetails(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(value = "post_id") long post_id,
            Model model) {

        if (!postRepository.existsById(post_id)) {
            return "redirect:/main";
        }

        model.addAttribute("authorized", Objects.nonNull(principal));
        Optional<Post> post = postRepository.findById(post_id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "about-post";
    }

    /**
     * Get-request
     * Вернуть страничку customize-post.html для правки поста
     * Править пост (get mapping) (if exists)
     *
     * Находим model в db по id (pk) (если сущ-ет) и работаем
     *
     * Внедрение аутентифицированного пользователя в
     * метод-обработчик Spring MVC (@AuthenticationPrincipal)
     *
     * Переадресация на страницу customize-post (если успешно) иначе
     * переадресация на главную страницу
     *
     * */

    @GetMapping("/main/{post_id}/customize")
    public String blogEdit(
            @AuthenticationPrincipal Principal principal,
            @PathVariable(value = "post_id") long post_id,
            Model model) {
        if (!postRepository.existsById(post_id)) {
            return "redirect:/main";
        }
        model.addAttribute("authorized", Objects.nonNull(principal));
        Optional<Post> post = postRepository.findById(post_id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "customize-post";
    }

    /**
     * Post-request
     * Править пост
     *
     * Update model в репозитории (post_text, username ONLY)
     *
     * Переадресация на страницу customize-post (если успешно) иначе
     * переадресация на главную страницу
     *
     * */


    @PostMapping("/main/{post_id}/customize")
    public String blogPostUpdate(
            @PathVariable(value = "post_id") long post_id,
            @RequestParam(required=false,name="username") String username,
            @RequestParam(required=false,name="post_text") String post_text,
            Model model) {

        Post post = postRepository.findById(post_id).orElseThrow();
        post.setUsername(username);
        post.setPost_text(post_text);
        postRepository.save(post);
        return "redirect:/main";
    }

    /**
     * Post-request
     * Удалить пост
     *
     * Удалить пост в репозитории по id (pk)
     * переадресация на главную страницу
     *
     * */

    @PostMapping("/main/{post_id}/delete")
    public String blogPostRemove(
            @PathVariable(value = "post_id") long post_id,
            Model model) {
        Post post = postRepository.findById(post_id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/main";
    }
    /**
     * Get-request
     * Вернуть страничку add-post.html для добавления постов
     *
     * Внедрение аутентифицированного пользователя в
     * метод-обработчик Spring MVC (@AuthenticationPrincipal)
     * */

    @GetMapping("/main/add")
    public String blogAdd(@AuthenticationPrincipal Principal principal, Model model) {
        model.addAttribute("authorized", Objects.nonNull(principal));

        return "add-post";
    }

    /**
     * Post-request
     * Создание модели и сохранение ее в репозитории
     *
     * Автоматическое присваивание текущей даты и времени
     * в поле модели
     *
     * Переадресация на главную страницу
     *
     * */

    @PostMapping("/main/add")
    public String blogPostAdd(

            @RequestParam String username,
            @RequestParam String post_text ){



        Post post = new Post(LocalDateTime.now(), username, post_text);

        postRepository.save(post);
        return "redirect:/main";
    }

}
