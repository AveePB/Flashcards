package com.dev.app.util.dto;

import lombok.Builder;

@Builder
public record UserDTO(String username, String password) {

    public boolean isAllSet() {
        if (username == null) return false;

        if (password == null) return false;

        return true;
    }
}
