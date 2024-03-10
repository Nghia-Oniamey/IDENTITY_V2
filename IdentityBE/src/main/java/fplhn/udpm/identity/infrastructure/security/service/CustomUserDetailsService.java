package fplhn.udpm.identity.infrastructure.security.service;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.security.UserPrincipal;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StaffAuthRepository staffRepository;

    private final StudentAuthRepository studentRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<Staff> staffFpt = staffRepository.findByAccountFPT(email);
        if (staffFpt.isPresent()) {
            return UserPrincipal.create(staffFpt.get());
        }

        Optional<Staff> staffFe = staffRepository.findByAccountFE(email);
        if (staffFe.isPresent()) {
            return UserPrincipal.create(staffFe.get());
        }

        if (studentRepository.findByEmailFpt(email).isPresent()) {
            return UserPrincipal.create(studentRepository.findByEmailFpt(email).get());
        }

        throw new UsernameNotFoundException("User not found with email : " + email);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isPresent()) {
            return UserPrincipal.create(staff.get());
        }

        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            return UserPrincipal.create(student.get());
        }

        throw new UsernameNotFoundException("User not found with id : " + id);
    }
}