package org.nica.spring.tmp;

import org.nica.spring.tmp.AutoModel;

//@Controller
public class SimpleController {
    //@Value("${spring.application.name}")
    String appName;

    //@GetMapping("/")
    public String homePage(AutoModel model) {
        model.addAttribute("appName", appName);
        return "home";
    }
}