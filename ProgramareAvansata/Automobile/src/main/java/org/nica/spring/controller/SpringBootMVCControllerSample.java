package org.nica.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Stream;

@Controller
public class SpringBootMVCControllerSample {

    @Value("${spring.application.name}")
    String appName;
    @Value("${spring.application.value}")
    String appValue;

    //http://localhost:8081
    @GetMapping("/")
    public String homePage(Model model) { //ModelMap is better
        model.addAttribute("appName", appName);
        model.addAttribute("appValue", appValue);
        model.addAttribute("salutari", Stream.of("Salut", "Noroc"));
        return "home";
    }
    @GetMapping("/index.php")
    public String homePagePhp(Model model) { //ModelMap is better
        model.addAttribute("appName", appName);
        model.addAttribute("appValue", appValue);
        return "home";
    }

    //http://localhost:8081/1
    //http://localhost:8081/1?name=bugatti&value=veyron
    //http://localhost:8081/1?value=veyron
    //http://localhost:8081/1?name=bugatti
    @GetMapping("/1")
    public String homePage(Model model, @RequestParam(value = "name", defaultValue = "BMW") String name, @RequestParam(value = "value", defaultValue = "X3") String value) { //ModelMap is better
        appName = name;
        appValue = value;
        model.addAttribute("appName", name);
        model.addAttribute("appValue", appValue);
        return "home";
    }

    //http://localhost:8081/2
    @GetMapping("/2")
    public String homePage(ModelMap model) { //Model was yet good
        appName = "Salut 1 la toti!";
        model.addAttribute("appName", appName);
        model.addAttribute("appValue", appValue);
        return "home";
    }
}
