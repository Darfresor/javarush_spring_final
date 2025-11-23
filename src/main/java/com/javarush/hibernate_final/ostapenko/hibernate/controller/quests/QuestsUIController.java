package com.javarush.hibernate_final.ostapenko.hibernate.controller.quests;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestsUIController {
    private final QuestService questService;

    public QuestsUIController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping("/quests")
    public String quests(
            Model model,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "4")
            int size
    ) {
        Page<Quest> questsPage = questService.getQuests(page, size);
        model.addAttribute("questsPage", questsPage);
        model.addAttribute("quests", questsPage.getContent()); // список квестов
        model.addAttribute("currentPage", questsPage.getNumber());
        model.addAttribute("totalPages", questsPage.getTotalPages());
        model.addAttribute("totalItems", questsPage.getTotalElements());
        return "pages/quests";
    }
}
