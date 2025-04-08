package com.gymmanagement.backend.security;

import com.gymmanagement.backend.model.Customer;
import com.gymmanagement.backend.model.StaffMember;
import com.gymmanagement.backend.repository.CustomerRepository;
import com.gymmanagement.backend.repository.StaffMemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String CUSTOMER_ROLE = "CUSTOMER";

    private final StaffMemberRepository staffMemberRepository;
    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(StaffMemberRepository staffMemberRepository,
                                    CustomerRepository customerRepository) {
        this.staffMemberRepository = staffMemberRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<StaffMember> staffOpt = staffMemberRepository.findByEmail(email);

        if (staffOpt.isPresent()) {
            StaffMember staff = staffOpt.get();
            return buildUserDetails(
                    staff.getEmail(),
                    staff.getPassword(),
                    getStaffAuthorities(staff)
            );
        }

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return buildUserDetails(
                customer.getEmail(),
                customer.getPassword(),
                getCustomerAuthorities()
        );
    }

    private Collection<? extends GrantedAuthority> getStaffAuthorities(StaffMember staff) {
        String role = staff.getStaffType() != null ?
                staff.getStaffType().name() :
                "STAFF";
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role));
    }

    private Collection<? extends GrantedAuthority> getCustomerAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + CUSTOMER_ROLE));
    }

    private UserDetails buildUserDetails(String username, String password,
                                         Collection<? extends GrantedAuthority> authorities) {
        return User.builder()
                .username(username)
                .password(password)
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
