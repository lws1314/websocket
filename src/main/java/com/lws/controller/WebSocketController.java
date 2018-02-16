package com.lws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebSocketController {
    private Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    @RequestMapping("web")
    public String websocket(){
        return "main";
    }



}
