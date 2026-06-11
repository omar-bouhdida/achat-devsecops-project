package tn.esprit.rh.achat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
            <html>
            <body style="font-family: Arial; text-align:center; margin-top:100px;">
                <h1>🚀 Application Running</h1>
                <h2>Spring Boot + Docker + MySQL</h2>
                <p>The application is successfully deployed in Docker.</p>
            </body>
            </html>
            """;
    }
}