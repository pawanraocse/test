package com.practise.test.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserObject {
    @JsonProperty("UserName")
    private String userName;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("Email")
    private String email;

}
