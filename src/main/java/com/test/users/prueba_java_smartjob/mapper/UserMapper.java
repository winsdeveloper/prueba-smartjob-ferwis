package com.test.users.prueba_java_smartjob.mapper;

import com.test.users.prueba_java_smartjob.dto.request.PhoneRequest;
import com.test.users.prueba_java_smartjob.dto.request.UserRequest;
import com.test.users.prueba_java_smartjob.dto.response.PhoneResponse;
import com.test.users.prueba_java_smartjob.dto.response.UserResponse;
import com.test.users.prueba_java_smartjob.entity.Phone;
import com.test.users.prueba_java_smartjob.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toEntity(UserRequest userRequest);
  UserResponse toResponse(User user);
  Phone toPhoneEntity(PhoneRequest phoneRequest);
  PhoneResponse toPhoneResponse(Phone phone);

  default List<Phone> toPhoneEntities(List<PhoneRequest> phoneRequests) {
    return phoneRequests.stream()
        .map(this::toPhoneEntity)
        .collect(Collectors.toList());
  }

  default List<PhoneResponse> toPhoneResponses(List<Phone> phones) {
    return phones.stream()
        .map(this::toPhoneResponse)
        .collect(Collectors.toList());
  }
}
