package com.seven.bit_robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService {

    @Autowired
    private RestTemplate restTemplate;

    public String sendGetRequest(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    public String sendPostRequest(String url, Object requestBody) {
        return restTemplate.postForObject(url, requestBody, String.class);
    }
}