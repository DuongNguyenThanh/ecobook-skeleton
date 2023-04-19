package com.example.userservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
public class RegisterResponse {

    @JsonProperty("exist_username")
    private Boolean existUsername;

    @JsonProperty("exist_phone_number")
    private Boolean existPhoneNumber;
}
