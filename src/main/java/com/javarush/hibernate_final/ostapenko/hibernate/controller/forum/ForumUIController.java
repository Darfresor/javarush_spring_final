package com.javarush.hibernate_final.ostapenko.hibernate.controller.forum;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.SubTopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.DTO.TopicWithCountTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.SubTopic;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Topic;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.SubTopicService;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui/forum")
public class ForumUIController {
    private final TopicService topicService;
    private final SubTopicService subTopicService;

    @Autowired
    public ForumUIController(TopicService topicService, SubTopicService subTopicService) {
        this.topicService = topicService;
        this.subTopicService = subTopicService;
    }
    @GetMapping("/topics")
    public String showTopics(
            Model model,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size
    ){
        Page<TopicWithCountTo> topicPage = topicService.getTopicsWithCount(page,size);
        model.addAttribute("topicPage", topicPage);
        model.addAttribute("topics", topicPage.getContent()); // список топиков
        model.addAttribute("currentPage", topicPage.getNumber());
        model.addAttribute("totalPages", topicPage.getTotalPages());
        model.addAttribute("totalItems", topicPage.getTotalElements());
        return "pages/topics";
    }
    @GetMapping("/topic/{id}")
    public String showTopicDetail(
            Model model,
            @PathVariable
            Long id,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "2")
            int size
    ){
        Page<SubTopicWithCountTo> subTopicPage = subTopicService.getSubTopics(id, page, size);
        model.addAttribute("subTopicPage", subTopicPage);
        model.addAttribute("subTopics", subTopicPage.getContent()); // список топиков
        model.addAttribute("currentPage", subTopicPage.getNumber());
        model.addAttribute("totalPages", subTopicPage.getTotalPages());
        model.addAttribute("totalItems", subTopicPage.getTotalElements());
        return "pages/topic_detail";
    }
}
