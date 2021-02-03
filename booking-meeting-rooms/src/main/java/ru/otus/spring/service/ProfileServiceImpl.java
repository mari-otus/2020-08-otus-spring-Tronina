package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Profile;
import ru.otus.spring.domain.User;
import ru.otus.spring.dto.ProfileDto;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.user.ProfileRepository;
import ru.otus.spring.repository.user.UserRepository;
import ru.otus.spring.security.AuthUserDetails;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final BookingMapper mapper;

    @Override
    public ProfileDto getProfile(AuthUserDetails authUserDetails) {
        User user = userRepository.findByLogin(authUserDetails.getUsername())
                .orElseThrow(ApplicationException::new);
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ApplicationException::new);
        return mapper.toProfileDto(profile);
    }

    @Override
    public void updateProfile(ProfileDto updateRequest, AuthUserDetails authUserDetails) {
        User user = userRepository.findByLogin(authUserDetails.getUsername())
                .orElseThrow(ApplicationException::new);
        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(ApplicationException::new);
        profile.setEmail(updateRequest.getEmail());
        profile.setMobilePhone(updateRequest.getMobilePhone());
        profile.setEmailNotify(updateRequest.isEmailNotify());
        profile.setPhoneNotify(updateRequest.isPhoneNotify());
        profileRepository.save(profile);
    }
}
