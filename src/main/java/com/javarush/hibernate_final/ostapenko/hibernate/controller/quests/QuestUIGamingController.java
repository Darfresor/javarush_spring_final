package com.javarush.hibernate_final.ostapenko.hibernate.controller.quests;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.QuestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
        Optional<Quest> optionalQuest = questService.getQuestByIdWithStage(id);
        if(optionalQuest.isPresent()){
           Quest  quest = optionalQuest.get();
            System.out.println(quest.getStageList());
        }
        return "pages/quest_gaming";
    }
}
