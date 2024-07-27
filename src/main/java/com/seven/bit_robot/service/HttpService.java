package com.seven.bit_robot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seven.bit_robot.entry.MessageRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
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

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<MessageRequestBody> sendRequestQQRobot(String httpUrl, MessageRequestBody requestBody) {
        String url = httpUrl;
        return webClientBuilder.build()
                .post()
                .uri(url)
                .body(Mono.just(requestBody),MessageRequestBody.class)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    // Handle error status
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.out.println("Error response: ============================" + errorBody);
                                return Mono.error(new RuntimeException("Request failed with status: " + clientResponse.statusCode()));
                            });
                })
                .bodyToMono(MessageRequestBody.class)
                .doOnSuccess(response -> {
                    System.out.println("Request was successful=======================: " + response);
                })
                .doOnError(error -> {
                    System.out.println("Request failed:============================ " + error.getMessage());
                });
    }

    String getJson(MessageRequestBody data){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    void send(String httpUrl,String json){
        // 创建HttpEntity，包括JSON数据和请求头
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        // 发送POST请求
        ResponseEntity<String> response = restTemplate.postForEntity(httpUrl, entity, String.class);
        // 输出响应
        System.out.println(response.getBody());
    }
}