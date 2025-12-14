package com.javarush.hibernate_final.ostapenko.hibernate.controller.quests;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.StageTo;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.StageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("ui/quests/quest")
public class QuestUIGamingController {
    private final StageService stageService;

    public QuestUIGamingController(StageService stageService) {
        this.stageService = stageService;
    }

    @RequestMapping("/start")
    public String startGaming(
            Model model,
            @RequestParam
            Long id
    ){
        StageTo stage = stageService.getRootStageOfQuest(id);
        model.addAttribute("stage",stage);;
        return "pages/quest_gaming";
    };
    @RequestMapping("/gaming")
    public String questGaming(
            Model model,
            @RequestParam
            Long id
    ){
        StageTo stage = stageService.getStageById(id);
        model.addAttribute("stage",stage);
        System.out.println(stage);
        return "pages/quest_gaming";
    }
}
