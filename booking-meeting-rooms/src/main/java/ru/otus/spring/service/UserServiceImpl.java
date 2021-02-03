package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Profile;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserRole;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.user.ProfileRepository;
import ru.otus.spring.repository.user.RoleRepository;
import ru.otus.spring.repository.user.UserRepository;

import java.util.Set;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookingMapper mapper;

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::toUserDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode("guest"));
        UserRole userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(ApplicationException::new);
        user.setRoles(Set.of(userRole));
        User userNew = userRepository.save(user);
        profileRepository.save(Profile.builder()
                .user(userNew)
                .build());
        return mapper.toUserDto(userNew);
    }

    @Override
    public void lockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setLocked(true);
        userRepository.save(user);
    }

    @Override
    public void unlockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setLocked(false);
        userRepository.save(user);
    }

    @Override
    public void enableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void editUser(UserDto user) {
        User userOld = userRepository.findById(user.getId())
                .orElseThrow(ApplicationException::new);
        userOld.setFio(user.getFio());
        userOld.setLogin(user.getLogin());
        userRepository.save(userOld);
    }
}
