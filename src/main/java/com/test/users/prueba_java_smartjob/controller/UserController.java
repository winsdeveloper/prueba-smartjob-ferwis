package com.test.users.prueba_java_smartjob.controller;


import com.test.users.prueba_java_smartjob.dto.ApiResponse;
import com.test.users.prueba_java_smartjob.dto.request.UserRequest;
import com.test.users.prueba_java_smartjob.dto.response.UserResponse;
import com.test.users.prueba_java_smartjob.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

  private final IUserService iUserService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody UserRequest request) {
    log.info("registerUser started, request: {}", request);
    return iUserService.createUser(request);
  }

}
