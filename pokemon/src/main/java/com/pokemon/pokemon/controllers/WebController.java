package com.pokemon.pokemon.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/{path:^(?!api|static|index\\.html|favicon\\.ico|manifest\\.json).*$}")
    public String redirect() {
        return "forward:/index.html";
    }
}
