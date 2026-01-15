package com.javarush.hibernate_final.ostapenko.hibernate.controller.workshop;

import com.javarush.hibernate_final.ostapenko.hibernate.DTO.workshop.WorkshopQuestDTO;
import com.javarush.hibernate_final.ostapenko.hibernate.model.service.workshop.WorkshopQuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ui/workshop")
public class WorkShopUIController {

    private final WorkshopQuestService workshopQuestService;

    @GetMapping
    public String workshopPage() {
        return "pages/workshop/workshop";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<WorkshopQuestDTO> getQuestsData() {
        return workshopQuestService.getAllQuests();
    }
}
