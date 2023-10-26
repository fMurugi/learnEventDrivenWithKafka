package com.fiona.emailservice.kafka;

import com.fiona.basedomains.dto.OrderEvent;
import com.fiona.emailservice.Model.MailStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;
    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEvent orderEvent){
        logger.info(String.format("Order event recieved in email service =>%s",orderEvent.toString()));

      try{
          //send email to customer
          //create mail struvture
          MailStructure mailStructure = new MailStructure();
          mailStructure.setSubject("Order from us update");
          mailStructure.setMessage("Your order is being processed please wait ,we will update you ");
//          mailStructure.setOrderEvent(orderEvent);
          sendMail(mailStructure);
          logger.info("----------- SENT email");

      }catch (Exception ex){
          System.out.println(ex.getMessage());
      }
    }

    public void sendMail(MailStructure mailStructure){
        System.out.println("-----creating the email");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo("murugifiona28@gmail.com");

        javaMailSender.send(simpleMailMessage);
    }
}
