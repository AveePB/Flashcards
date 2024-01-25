package dev.bpeeva.flashcardsapp.util.mapper;

import dev.bpeeva.flashcardsapp.db.model.User;
import dev.bpeeva.flashcardsapp.util.dto.UserDTO;

import java.util.Optional;
import java.util.function.Function;

public class UserDTOMapper implements Function<User, Optional<UserDTO>> {

    @Override
    public Optional<UserDTO> apply(User user) {
        //Create data transfer object.
        UserDTO userDTO = new UserDTO(
                user.getUserRole(),
                user.getUsername(),
                user.getPassword()
        );

        //Check if not null.
        if (userDTO.isNotNull())
            return Optional.of(userDTO);

        return Optional.empty();
    }
}
