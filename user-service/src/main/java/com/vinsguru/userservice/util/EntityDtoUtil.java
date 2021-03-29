package com.vinsguru.userservice.util;

import com.vinsguru.userservice.dto.TransactionRequest;
import com.vinsguru.userservice.dto.TransactionResponse;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.entity.User;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static User toEntity(UserDto dto){
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static TransactionResponse toResponse(TransactionRequest request, TransactionStatus status){
        TransactionResponse response = new TransactionResponse();
        response.setAmount(request.getAmount());
        response.setType(request.getType());
        response.setUserId(request.getUserId());
        response.setStatus(status);
        return response;
    }


}
