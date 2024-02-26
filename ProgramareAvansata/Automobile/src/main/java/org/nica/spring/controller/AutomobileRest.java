package org.nica.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutomobileRest {

    //http://localhost:8080/doauto
    //http://localhost:8080/doauto?marca=Bugatti&model=Veyron
    @SuppressWarnings("SpellCheckingInspection")
    @GetMapping("/doauto")
    public String doAuto(@RequestParam(value = "marca", defaultValue = "BMW") String marca, @RequestParam(value = "model", defaultValue = "X3") String model) {
        return String.format("Automobil %s %s!", marca, model);
    }

}
