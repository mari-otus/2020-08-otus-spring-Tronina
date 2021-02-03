package ru.otus.spring.service;

import ru.otus.spring.dto.ProfileDto;
import ru.otus.spring.security.AuthUserDetails;

/**
 * @author MTronina
 */
public interface ProfileService {

    ProfileDto getProfile(AuthUserDetails authUserDetails);

    void updateProfile(ProfileDto updateRequest, AuthUserDetails authUserDetails);
}
