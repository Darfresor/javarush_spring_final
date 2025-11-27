package com.javarush.hibernate_final.ostapenko.hibernate.controller.quests;

import com.javarush.hibernate_final.ostapenko.hibernate.model.service.QuestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("ui/quests/quest")
public class QuestUIGamingController {
    private final QuestService questService;

    public QuestUIGamingController(QuestService questService) {
        this.questService = questService;
    }

    @RequestMapping("/start")
    public String questGaming(
            Model model,
            @RequestParam
            Long id
    ){

        return "pages/quest_gaming";
    }
}
