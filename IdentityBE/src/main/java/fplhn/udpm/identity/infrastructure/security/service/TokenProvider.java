package fplhn.udpm.identity.infrastructure.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fplhn.udpm.identity.entity.AccessToken;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.security.UserPrincipal;
import fplhn.udpm.identity.infrastructure.security.repository.AccessTokenAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffModuleRoleAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
import fplhn.udpm.identity.infrastructure.security.response.TokenSubjectResponse;
import fplhn.udpm.identity.util.DateTimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TokenProvider {

    @Setter
    private SecretKey tokenSecret;

    @Value("${app.jwt.token-validity-in-seconds}")
    private long tokenExpirationMsec;

    private StudentAuthRepository studentRepository;

    private StaffAuthRepository staffRepository;

    private StaffModuleRoleAuthRepository staffModuleRoleRepository;

    private AccessTokenAuthRepository accessTokenRepository;

    @Autowired
    public void setStaffModuleRoleRepository(StaffModuleRoleAuthRepository staffModuleRoleRepository) {
        this.staffModuleRoleRepository = staffModuleRoleRepository;
    }

    @Autowired
    public void setStaffRepository(StaffAuthRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Autowired
    public void setStudentRepository(StudentAuthRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setAccessTokenRepository(AccessTokenAuthRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public String createToken(Authentication authentication, String host) throws BadRequestException, JsonProcessingException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Object user = getCurrentUserLogin(userPrincipal.getEmail());
        if (user == null) {
            throw new BadRequestException("User not found");
        }

        Long isTokenRevoke = accessTokenRepository.isRevoked(userPrincipal.getId());

        if (isTokenRevoke != null) {
            return accessTokenRepository.getAccessTokenByUserId(userPrincipal.getId());
        }

        TokenSubjectResponse tokenSubjectResponse = null;
        if (user instanceof Student) {
            tokenSubjectResponse = new TokenSubjectResponse();
            tokenSubjectResponse.setFullName(((Student) user).getHoTen());
            tokenSubjectResponse.setUserId(((Student) user).getId());
            tokenSubjectResponse.setUserCode(((Student) user).getMaSinhVien());
            tokenSubjectResponse.setRolesCode(Collections.singletonList("STUDENT"));
            tokenSubjectResponse.setRolesName(Collections.singletonList("Sinh viên"));
            tokenSubjectResponse.setHost(host);
            tokenSubjectResponse.setEmail(((Student) user).getEmailFpt());
            DepartmentCampus departmentCampus = ((Student) user).getDepartmentCampus();
            if (departmentCampus != null) {
                tokenSubjectResponse.setTrainingFacilityCode(departmentCampus.getCampus().getMa());
                tokenSubjectResponse.setSubjectCode(departmentCampus.getDepartment().getMa());
            } else {
                tokenSubjectResponse.setTrainingFacilityCode(null);
                tokenSubjectResponse.setSubjectCode(null);
            }
        }

        if (user instanceof Staff) {
            tokenSubjectResponse = new TokenSubjectResponse();
            tokenSubjectResponse.setFullName(((Staff) user).getFullName());
            tokenSubjectResponse.setUserId(((Staff) user).getId());
            tokenSubjectResponse.setUserCode(((Staff) user).getStaffCode());
            tokenSubjectResponse.setEmail(((Staff) user).getAccountFPT());
            DepartmentCampus departmentCampus = ((Staff) user).getDepartmentCampus();
            if (departmentCampus != null) {
                tokenSubjectResponse.setTrainingFacilityCode(departmentCampus.getCampus().getMa());
                tokenSubjectResponse.setSubjectCode(departmentCampus.getDepartment().getMa());
            } else {
                tokenSubjectResponse.setTrainingFacilityCode(null);
                tokenSubjectResponse.setSubjectCode(null);
            }
            List<String> rolesCode = staffModuleRoleRepository.getListRoleCodeByUserId(((Staff) user).getId());
            if (rolesCode.isEmpty()) {
                tokenSubjectResponse.setRolesCode(Collections.singletonList("STAFF"));
                tokenSubjectResponse.setRolesName(Collections.singletonList("Nhân viên"));
            } else {
                tokenSubjectResponse.setRolesCode(rolesCode);
                tokenSubjectResponse.setRolesName(staffModuleRoleRepository.getListRoleNameByUserId(((Staff) user).getId()));
            }
            tokenSubjectResponse.setHost(host);
        }
        assert tokenSubjectResponse != null;
        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() * 1000 + tokenExpirationMsec);
        JwtBuilder builder = Jwts.builder();
        Map<String, Object> claims = getBodyClaims(tokenSubjectResponse);
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        builder.setClaims(claims);
        builder.setIssuedAt(now);
        builder.setExpiration(expiryDate);
        builder.signWith(tokenSecret);
        String accessToken = builder.compact();
        Optional<AccessToken> accessTokenOptional = accessTokenRepository.findByUserId(userPrincipal.getId());

        if (accessTokenOptional.isPresent()) {
            accessTokenOptional.get().setAccessToken(accessToken);
            accessTokenOptional.get().setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiryDate));
            accessTokenRepository.save(accessTokenOptional.get());
            return accessToken;
        }
        AccessToken token = new AccessToken();
        token.setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiryDate));
        token.setAccessToken(accessToken);
        token.setUserId(userPrincipal.getId());
        AccessToken accessTokenSaved = accessTokenRepository.save(token);
        return accessTokenSaved.getAccessToken();
    }

    public String createToken(Long userId, String host) throws BadRequestException, JsonProcessingException {
        Optional<Staff> staffOptional = staffRepository.findById(userId);
        Optional<Student> studentOptional = studentRepository.findById(userId);
        if (staffOptional.isEmpty() && studentOptional.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        Long isTokenRevoke = accessTokenRepository.isRevoked(userId);

        if (isTokenRevoke != null) {
            return accessTokenRepository.getAccessTokenByUserId(userId);
        }

        TokenSubjectResponse tokenSubjectResponse = null;
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            tokenSubjectResponse = new TokenSubjectResponse();
            tokenSubjectResponse.setFullName(student.getHoTen());
            tokenSubjectResponse.setUserId(student.getId());
            tokenSubjectResponse.setUserCode(student.getMaSinhVien());
            tokenSubjectResponse.setRolesCode(Collections.singletonList("STUDENT"));
            tokenSubjectResponse.setRolesName(Collections.singletonList("Sinh viên"));
            tokenSubjectResponse.setHost(host);
            tokenSubjectResponse.setEmail(student.getEmailFpt());
            tokenSubjectResponse.setTrainingFacilityCode(student.getDepartmentCampus().getCampus().getMa());
            tokenSubjectResponse.setSubjectCode(student.getDepartmentCampus().getDepartment().getMa());
        }

        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            tokenSubjectResponse = new TokenSubjectResponse();
            tokenSubjectResponse.setFullName(staff.getFullName());
            tokenSubjectResponse.setUserId(staff.getId());
            tokenSubjectResponse.setUserCode(staff.getStaffCode());
            tokenSubjectResponse.setEmail(staff.getAccountFPT());
            tokenSubjectResponse.setTrainingFacilityCode(staff.getDepartmentCampus().getCampus().getMa());
            tokenSubjectResponse.setSubjectCode(staff.getDepartmentCampus().getDepartment().getMa());
            List<String> rolesCode = staffModuleRoleRepository.getListRoleCodeByUserId(staff.getId());
            if (rolesCode.isEmpty()) {
                tokenSubjectResponse.setRolesCode(Collections.singletonList("STAFF"));
                tokenSubjectResponse.setRolesName(Collections.singletonList("Nhân viên"));
            } else {
                tokenSubjectResponse.setRolesCode(rolesCode);
                tokenSubjectResponse.setRolesName(staffModuleRoleRepository.getListRoleNameByUserId(staff.getId()));
            }
            tokenSubjectResponse.setHost(host);
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationMsec);
        JwtBuilder builder = Jwts.builder();
        Map<String, Object> claims = getBodyClaims(tokenSubjectResponse);
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        builder.setClaims(claims);
        builder.setIssuedAt(now);
        builder.setExpiration(expiryDate);
        builder.signWith(tokenSecret);
        String accessToken = builder.compact();
        Optional<AccessToken> accessTokenOptional = accessTokenRepository.findByUserId(userId);

        if (accessTokenOptional.isPresent()) {
            accessTokenOptional.get().setAccessToken(accessToken);
            accessTokenOptional.get().setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiryDate));
            accessTokenRepository.save(accessTokenOptional.get());
            return accessToken;
        }
        AccessToken token = new AccessToken();
        token.setExpiredAt(DateTimeUtil.convertDateToTimeStampSecond(expiryDate));
        token.setAccessToken(accessToken);
        token.setUserId(userId);
        AccessToken accessTokenSaved = accessTokenRepository.save(token);
        return accessTokenSaved.getAccessToken();
    }

    private static Map<String, Object> getBodyClaims(TokenSubjectResponse tokenSubjectResponse) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("fullName", tokenSubjectResponse.getFullName());
        claims.put("userId", tokenSubjectResponse.getUserId());
        claims.put("userName", tokenSubjectResponse.getUserCode());
        claims.put("userCode", tokenSubjectResponse.getUserCode());
        claims.put("email", tokenSubjectResponse.getEmail());
        claims.put("trainingFacilityCode", tokenSubjectResponse.getTrainingFacilityCode());
        claims.put("subjectCode", tokenSubjectResponse.getSubjectCode());
        List<String> rolesCode = tokenSubjectResponse.getRolesCode();
        List<String> rolesName = tokenSubjectResponse.getRolesName();
        if (rolesCode.size() == 1) {
            claims.put("rolesCode", rolesCode.get(0));
        } else {
            claims.put("rolesCode", rolesCode);
        }
        if (rolesName.size() == 1) {
            claims.put("rolesName", rolesName.get(0));
        } else {
            claims.put("rolesName", rolesName);
        }
        claims.put("host", tokenSubjectResponse.getHost());
        return claims;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

    public String getHostFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(tokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        TokenSubjectResponse tokenSubjectResponse = null;
        try {
            tokenSubjectResponse = objectMapper.readValue(claims.getSubject(), TokenSubjectResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace(System.out
            );
        }
        log.info("Token subject: " + tokenSubjectResponse.toString());
        return tokenSubjectResponse.getHost();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(tokenSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private Object getCurrentUserLogin(String email) {
        if (staffRepository.findByAccountFPT(email).isPresent()) {
            return staffRepository.findByAccountFPT(email).get();
        }
        if (staffRepository.findByAccountFE(email).isPresent()) {
            return staffRepository.findByAccountFE(email).get();
        }
        if (studentRepository.findByEmailFpt(email).isPresent()) {
            return studentRepository.findByEmailFpt(email).get();
        }
        return null;
    }


}
