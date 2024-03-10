package fplhn.udpm.identity.infrastructure.security.oauth2;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.exception.OAuth2AuthenticationProcessingException;
import fplhn.udpm.identity.infrastructure.security.UserPrincipal;
import fplhn.udpm.identity.infrastructure.security.oauth2.user.OAuth2UserInfo;
import fplhn.udpm.identity.infrastructure.security.oauth2.user.OAuth2UserInfoFactory;
import fplhn.udpm.identity.infrastructure.security.repository.RoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffModuleRoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final StaffAuthRepository staffAuthRepository;

    private final StudentAuthRepository studentAuthRepository;

    private final RoleAuthRepository roleAuthRepository;

    private final StaffModuleRoleAuthRepository staffModuleRoleAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Student> studentOptional = studentAuthRepository.findByEmailFpt(oAuth2UserInfo.getEmail());
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            Student student1 = (Student) updateExistingUser(student, oAuth2UserInfo);
            return UserPrincipal.create(student1);
        }

        Optional<Staff> staffOptional = staffAuthRepository.findByAccountFE(oAuth2UserInfo.getEmail());
        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            Staff staff1 = (Staff) updateExistingUser(staff, oAuth2UserInfo);
            return UserPrincipal.create(staff1);
        }
        Object user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        if (user instanceof Staff staff) {
            return UserPrincipal.create(staff);
        } else if (user instanceof Student student) {
            return UserPrincipal.create(student);
        } else {
            throw new OAuth2AuthenticationProcessingException("Invalid user type");
        }
    }

    private Object registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        String email = oAuth2UserInfo.getEmail();
        if (email.matches(".*ph\\d{5}@fpt.edu.vn$") || email.endsWith("@gmail.com")) {
            Student student = new Student();
            student.setEmailFpt(email);
            student.setHoTen(oAuth2UserInfo.getName());
            student.setAvatar(oAuth2UserInfo.getImageUrl());
            return studentAuthRepository.save(student);
        } else if (email.endsWith("@fpt.edu.vn") || email.endsWith("@fe.edu.vn")) {
            Staff staff = new Staff();
            if (email.endsWith("@fpt.edu.vn")) {
                staff.setAccountFPT(email);
            } else {
                staff.setAccountFE(email);
            }
            staff.setAvatar(oAuth2UserInfo.getImageUrl());
            staff.setFullName(oAuth2UserInfo.getName());
            return staffAuthRepository.save(staff);
        } else {
            throw new OAuth2AuthenticationProcessingException("Invalid email format");
        }
    }

    private Object updateExistingUser(Student existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setHoTen(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        return studentAuthRepository.save(existingUser);
    }

    private Object updateExistingUser(Staff existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFullName(oAuth2UserInfo.getName());
        existingUser.setAvatar(oAuth2UserInfo.getImageUrl());
        return staffAuthRepository.save(existingUser);
    }

    private Role createRoleStaff() {
        Role role = new Role();
        role.setMa("STAFF");
        role.setTen("Nhân viên");
        return roleAuthRepository.save(role);
    }

}
