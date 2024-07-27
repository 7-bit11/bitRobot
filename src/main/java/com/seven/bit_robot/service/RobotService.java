package com.seven.bit_robot.service;

import com.seven.bit_robot.entry.MessageEntry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RobotService {
    @PostMapping("/get/name")
    public String getRequestParams(@RequestBody MessageEntry request) {

        return request.toString();
    }

}
