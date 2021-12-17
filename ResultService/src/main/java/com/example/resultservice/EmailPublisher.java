package com.example.resultservice;

import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/sendemail")
    public String sendEmail(@RequestBody Result result) {
        String patient_email = result.getEmail();
        String patient_name = result.getFirstName() + " " + result.getLastName();
        String content_email = "เรียน " + patient_name + "\nผลการประเมินกลุ่มอาการที่พบบ่อยในผู้สูงอายุ เป็นดังนี้ \n" + result.getResult();

        String emailForSend = patient_email + "," + content_email;

        rabbitTemplate.convertAndSend("ECGADirect","email", emailForSend);

        return "send to :" + patient_email;

    }
}
