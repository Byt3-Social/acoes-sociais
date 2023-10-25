package com.byt3social.acoessociais.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PDSignTokenDTO(
        @JsonProperty("access_token")
        String accessToken
) {
}
