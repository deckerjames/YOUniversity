package com.libertymutual.goforcode.youniversity.services;

import org.springframework.security.core.Authentication;

import com.libertymutual.goforcode.youniversity.models.User;
import com.libertymutual.goforcode.youniversity.models.UserUpdateInfoDto;

public interface UpdateUserInterface {

    User updateUser(Authentication auth, UserUpdateInfoDto user);

}
