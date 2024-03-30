package com.dev.app.util.token;

import lombok.Builder;

@Builder
public record JsonWebToken(String bearer) {
    //...
}
