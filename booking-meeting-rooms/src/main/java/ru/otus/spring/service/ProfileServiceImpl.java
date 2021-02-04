package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Profile;
import ru.otus.spring.dto.ProfileDto;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.ProfileRepository;
import ru.otus.spring.security.AuthUserDetails;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final BookingMapper mapper;

    @Override
    public ProfileDto getProfile(AuthUserDetails authUserDetails) {
        Profile profile = profileRepository.findByLoginEquals(authUserDetails.getUsername())
                .orElseThrow(ApplicationException::new);
        return mapper.toProfileDto(profile);
    }

    @Override
    public ProfileDto createProfile(AuthUserDetails authUserDetails) {
        Profile profile = profileRepository.findByLoginEquals(authUserDetails.getUsername())
                .orElseGet(() ->
                        profileRepository.save(Profile.builder()
                                .login(authUserDetails.getUsername())
                                .build())
                );
        return mapper.toProfileDto(profile);
    }

    @Override
    public void updateProfile(ProfileDto updateRequest, AuthUserDetails authUserDetails) {
        Profile profile = profileRepository.findByLoginEquals(authUserDetails.getUsername())
                .orElseThrow(ApplicationException::new);
        profile.setEmail(updateRequest.getEmail());
        profile.setMobilePhone(updateRequest.getMobilePhone());
        profile.setEmailNotify(updateRequest.isEmailNotify());
        profile.setPhoneNotify(updateRequest.isPhoneNotify());
        profileRepository.save(profile);
    }
}
