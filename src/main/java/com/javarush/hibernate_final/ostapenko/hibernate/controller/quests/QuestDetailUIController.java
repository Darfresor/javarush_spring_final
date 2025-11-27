package com.javarush.hibernate_final.ostapenko.hibernate.controller.quests;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.QuestTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.QuestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("ui/quests/quest")
public class QuestDetailUIController {
    private final QuestService questService;

    public QuestDetailUIController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping("{id}")
    public String questDetail(
            Model model,
            @PathVariable
            Long id
    ){
        if (id != -1) {
            QuestTo quest = questService.findById(id);
            model.addAttribute("quest",quest);
        }

        return "pages/quest_detail";
    }
}
