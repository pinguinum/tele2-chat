package org.tele2chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"/", "/login", "/logout"})
    public String handlePageRequest() {
        return "login";
    }

    @GetMapping("/chatPage")
    public String chatPage() {
        return "chat";
    }
}

