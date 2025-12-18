package com.javarush.hibernate_final.ostapenko.hibernate.controller.forum;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Topic;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui/forum")
public class ForumUIController {
    private final TopicService topicService;

    @Autowired
    public ForumUIController(TopicService topicService) {
        this.topicService = topicService;
    }
    @GetMapping("/topics")
    public String showTopics(
            Model model,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size
    ){
        Page<Topic> topicPage = topicService.getTopics(page,size);
        model.addAttribute("topicPage", topicPage);
        model.addAttribute("topics", topicPage.getContent()); // список топиков
        model.addAttribute("currentPage", topicPage.getNumber());
        model.addAttribute("totalPages", topicPage.getTotalPages());
        model.addAttribute("totalItems", topicPage.getTotalElements());
        return "pages/topics";
    }
}
