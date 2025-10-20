package com.back.tasks.api.io.user;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class UserCreateRequest {
    private String name;
    private String email;
    private String password;
}
