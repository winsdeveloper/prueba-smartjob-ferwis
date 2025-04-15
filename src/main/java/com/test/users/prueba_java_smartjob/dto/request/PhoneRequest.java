package com.test.users.prueba_java_smartjob.dto.request;

public record PhoneRequest(
    String number,
    String cityCode,
    String countryCode
) {}
