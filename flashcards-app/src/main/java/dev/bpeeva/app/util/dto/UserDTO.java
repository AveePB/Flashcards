package dev.bpeeva.app.util.dto;

import lombok.Builder;

@Builder
public record UserDTO(String username, String password) {
    //...
}
