package dev.bpeeva.flashcardsapp.util.dto;

import dev.bpeeva.flashcardsapp.db.constant.UserRole;

public record UserDTO(
        UserRole userRole,
        String username,
        String password
) {

    /**
     * Checks if all object parameters aren't null.
     * @return the boolean value.
     */
    public boolean isNotNull() {

        //Check username.
        if (this.username == null)
            return false;

        //Check password.
        if (this.password == null)
            return false;

        return true;
    }

}
