package ru.otus.spring.audit;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.audit.BookingAudit;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.dto.UserRoleDto;
import ru.otus.spring.repository.audit.BookingAuditRepository;
import ru.otus.spring.security.AuthUserDetails;

import java.security.Principal;
import java.util.stream.Collectors;

/**
 * @author MTronina
 */
@Aspect
@Component
@RequiredArgsConstructor
public class BookingMeetingRoomsAudit {

    private final BookingAuditRepository bookingAuditRepository;

    @AfterReturning(
            pointcut = "execution(* ru.otus.spring.repository.BookingRepository.save(..))",
            returning = "booking"
    )
    public void auditAfterReturning(JoinPoint joinPoint, Booking booking) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();

        BookingAudit bookingAudit = BookingAudit.builder()
                .bookingId(booking.getId())
                .room(booking.getRoom())
                .beginDate(booking.getBeginDate())
                .endDate(booking.getEndDate())
                .createDate(booking.getCreateDate())
                .updateDate(booking.getUpdateDate())
                .deleteDate(booking.getDeleteDate())
                .user(UserDto.builder()
                        .fio(userDetails.getFio())
                        .login(userDetails.getUsername())
                        .roles(userDetails.getAuthorities().stream()
                                .map(authority -> UserRoleDto.builder()
                                        .role(authority.getAuthority())
                                        .build())
                                .collect(Collectors.toSet()))
                        .build())
                .build();
        bookingAuditRepository.save(bookingAudit);
    }
}
