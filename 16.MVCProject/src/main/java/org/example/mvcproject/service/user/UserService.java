package org.example.mvcproject.service.user;

import org.example.mvcproject.models.dto.user.UserRegisterDto;

public interface UserService {

    boolean register(UserRegisterDto userRegisterDto);
}
