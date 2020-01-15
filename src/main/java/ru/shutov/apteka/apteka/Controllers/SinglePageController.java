package ru.shutov.apteka.apteka.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SinglePageController {
    @GetMapping
    public String main(Model model) {
        model.addAttribute("isDevMode", true);
        return "index";
    }
}
