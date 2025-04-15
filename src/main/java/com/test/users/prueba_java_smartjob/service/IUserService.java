package com.test.users.prueba_java_smartjob.service;

import com.test.users.prueba_java_smartjob.dto.ApiResponse;
import com.test.users.prueba_java_smartjob.dto.request.UserRequest;
import reactor.core.publisher.Mono;

public interface IUserService {
  Mono<ApiResponse<?>> createUser(UserRequest userRequest);
}
