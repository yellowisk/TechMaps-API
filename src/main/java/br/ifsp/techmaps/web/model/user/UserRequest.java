package br.ifsp.techmaps.web.model.user;

import br.ifsp.techmaps.domain.entities.user.User;

public record UserRequest(String username, String email, String password) {

    public UserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User toUser() {
        return User.createFromUser(username, email, password);
    }

}
