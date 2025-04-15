package com.test.users.prueba_java_smartjob.service;

import com.test.users.prueba_java_smartjob.dto.ApiResponse;
import com.test.users.prueba_java_smartjob.dto.request.UserRequest;
import com.test.users.prueba_java_smartjob.dto.response.UserResponse;
import reactor.core.publisher.Mono;

public interface IUserService {
  Mono<ApiResponse<UserResponse>> createUser(UserRequest userRequest);
}
