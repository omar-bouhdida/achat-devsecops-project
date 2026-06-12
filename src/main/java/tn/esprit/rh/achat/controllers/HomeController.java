package tn.esprit.rh.achat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
        public String home() {
         return "Application Running";
        }
}