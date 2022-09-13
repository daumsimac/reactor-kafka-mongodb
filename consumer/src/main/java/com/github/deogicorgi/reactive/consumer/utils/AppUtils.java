package com.github.deogicorgi.reactive.consumer.utils;

import com.github.deogicorgi.reactive.consumer.config.model.Message;
import com.github.deogicorgi.reactive.consumer.config.model.MessageDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static MessageDto entityToDto(Message message){
        MessageDto messageDto = new MessageDto();
        BeanUtils.copyProperties(message, messageDto);
        return messageDto;
    }
    public static Message dtoToEntity(MessageDto msg){
        Message message = new Message();
        BeanUtils.copyProperties(msg, message);
        return message;
    }
}
