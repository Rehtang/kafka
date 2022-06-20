package ru.rehtang.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rehtang.kafka.dto.Address;
import ru.rehtang.kafka.dto.UserDto;

@RestController
@RequestMapping("msg")
public class MsgController {
  @Autowired private KafkaTemplate<Long, UserDto> kafkaTemplate;

  @PostMapping
  public void sendMsg(Long msgId, UserDto msg) {
    msg.setAddress(new Address("Rus", "Msk", "Bunina", 1L, 1L));
    ListenableFuture<SendResult<Long,UserDto>> future = kafkaTemplate.send("msg", msgId, msg);
    future.addCallback(System.out::println, System.err::println);
    kafkaTemplate.flush();
  }
}
