package com.seven.bit_robot.service;

import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.utils.JsonUtils;
import com.seven.bit_robot.entry.Data;
import com.seven.bit_robot.entry.BitMessage;
import com.seven.bit_robot.entry.MessageEntry;
import com.seven.bit_robot.entry.MessageRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import io.reactivex.Flowable;
@RestController
public class RobotService {
    @Value("${bit.borot.ip}")
    private String url;
    @Autowired
    private HttpService myService;
    @PostMapping("/get/name")
    public void getRequestParams(@RequestBody MessageEntry request) {
        boolean isRobot=false;
        boolean isChat=false;
        String msgapi="";
        BitMessage bitMessage =new BitMessage();
        List<BitMessage> bitMessages = request.getMessage();
        for (BitMessage m : bitMessages) {
            if("text".equals(m.getType())){
               Data msgdata= m.getData();
               if(msgdata.getText().contains("机器人")){
                   isRobot=true;
                   Data data= new Data();
                   data.setText(msgdata.getText().replaceAll("机器人","哼，我才不是笨蛋傲娇呢"));
                   bitMessage.setData(data);
               }
                if(msgdata.getText().contains("chat")){
                    isChat=true;
                    msgapi+=msgdata.getText();
                } else if(msgdata.getText().contains("评价一下if")){
                    isRobot=true;
                    Data data= new Data();
                    data.setText("玩原神玩的");
                    bitMessage.setData(data);
                }else  if(msgdata.getText().contains("if")){
                    isRobot=true;
                    Data data= new Data();
                    data.setText("if 都玩原神了大家都让让他吧！");
                    bitMessage.setData(data);
                }




            }
        }
        if(isRobot){
            String value= String.valueOf(request.getGroupID());
            bitMessage.setType(bitMessages.get(0).getType());
            MessageRequestBody requestBody=new MessageRequestBody(value, bitMessage);
            sendUrl(requestBody);
        }
        if(isChat){
            try {
               String str=  streamCallWithMessage(msgapi.replaceAll("chat",""));
                Data data= new Data();
                data.setText(str);
                bitMessage.setData(data);
                String value= String.valueOf(request.getGroupID());
                bitMessage.setType(bitMessages.get(0).getType());
                MessageRequestBody requestBody=new MessageRequestBody(value, bitMessage);
               sendUrl(requestBody);
            } catch (ApiException | NoApiKeyException | InputRequiredException e) {
                System.out.println(e.getMessage());
            }
//            try {
//                streamCallWithCallback();
//            } catch (ApiException | NoApiKeyException | InputRequiredException | InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
        }


    }
    private void sendHttp( MessageRequestBody requestBody){
        myService.sendRequestQQRobot(url+"send_group_msg",requestBody)
                .map(responseBody -> {
                    System.out.println("responseBody =================================: " + responseBody);
                    return ResponseEntity.ok(responseBody);
                })
                .onErrorResume(ex -> {
                    System.out.println("Exception:==================================== " + ex.getMessage());
                    return Mono.just(ResponseEntity.status(500).build());
                });
    }

    private void sendUrl(MessageRequestBody requestBody){
        String json=  myService.getJson(requestBody);
        myService.send(url+"send_group_msg",json);
    }




        public static String streamCallWithMessage(String msg)
                throws NoApiKeyException, ApiException, InputRequiredException {
            Generation gen = new Generation();
            StringBuilder resultStr=new StringBuilder();
            Message userMsg =
                    Message.builder().role(Role.USER.getValue()).content(msg).build();
            GenerationParam param = GenerationParam.builder()
                    .model("qwen-max")
                    .messages(Arrays.asList(userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE) // the result if message format.
                    .topP(0.8).enableSearch(true) // set streaming output
                    .incrementalOutput(true) // get streaming output incrementally
                    .apiKey("sk-60ebc8e0c2df4280bb4dafe71c97a1b2")
                    .build();
            Flowable<GenerationResult> result = gen.streamCall(param);
            StringBuilder fullContent = new StringBuilder();
            result.blockingForEach(message -> {
                fullContent.append(message.getOutput().getChoices().get(0).getMessage().getContent());
                resultStr.append(fullContent);
            });
            return fullContent.toString();
        }

        public static void streamCallWithCallback()
                throws NoApiKeyException, ApiException, InputRequiredException, InterruptedException {
            Generation gen = new Generation();
            Message userMsg =
                    Message.builder().role(Role.USER.getValue()).content("Introduce the capital of China").build();
            GenerationParam param = GenerationParam.builder()
                    .model("${modelCode}")
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)  //set result format message
                    .messages(Arrays.asList(userMsg)) // set messages
                    .topP(0.8)
                    .incrementalOutput(true) // set streaming output incrementally
                    .build();
            Semaphore semaphore = new Semaphore(0);
            StringBuilder fullContent = new StringBuilder();
            gen.streamCall(param, new ResultCallback<GenerationResult>() {

                @Override
                public void onEvent(GenerationResult message) {
                    fullContent
                            .append(message.getOutput().getChoices().get(0).getMessage().getContent());
                    System.out.println(message);
                }

                @Override
                public void onError(Exception err) {
                    System.out.println(String.format("Exception: %s", err.getMessage()));
                    semaphore.release();
                }

                @Override
                public void onComplete() {
                    System.out.println("Completed");
                    semaphore.release();
                }

            });
            semaphore.acquire();
            System.out.println("Full content: \n" + fullContent.toString());
        }



}
