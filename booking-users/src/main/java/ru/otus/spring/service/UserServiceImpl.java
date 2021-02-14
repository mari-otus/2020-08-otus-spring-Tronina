package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserRole;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.UserMapper;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::toUserDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode("guest"));
        UserRole userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(ApplicationException::new);
        user.setRoles(Set.of(userRole));
        User userNew = userRepository.save(user);
        return mapper.toUserDto(userNew);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void lockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setLocked(true);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void unlockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setLocked(false);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void enableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void disableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ApplicationException::new);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void editUser(UserDto user) {
        User userOld = userRepository.findById(user.getId())
                .orElseThrow(ApplicationException::new);
        userOld.setFio(user.getFio());
        userOld.setLogin(user.getLogin());
        userRepository.save(userOld);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(ApplicationException::new);
        final UserDto userDto = mapper.toUserDto(user);
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
