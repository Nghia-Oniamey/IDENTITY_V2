//package fplhn.udpm.identity.infrastructure.config.awareuser;
//
//import fplhn.udpm.identity.entity.Staff;
//import fplhn.udpm.identity.entity.Student;
//import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
//import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
////@Component
//public class AuditorAwareImpl implements AuditorAware<Long> {
//
//    private StaffAuthRepository staffAuthRepository;
//
//    private StudentAuthRepository studentRepository;
//
//    @Autowired
//    public void setStudentRepository(StudentAuthRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//
//    @Autowired
//    public void setStaffAuthRepository(StaffAuthRepository staffAuthRepository) {
//        this.staffAuthRepository = staffAuthRepository;
//    }
//
//    @Override
//    public Optional<Long> getCurrentAuditor() {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("email: " + email);
//        if (email.endsWith("@fpt.edu.vn") || email.endsWith("@fe.edu.vn")) {
//            if (email.matches(".*@fpt.edu.vn$")) {
//                Optional<Staff> staff = staffAuthRepository.findByAccountFPT(email);
//                if (staff.isPresent()) {
//                    return staff.get().getId().describeConstable();
//                }
//            } else {
//                Optional<Staff> staff = staffAuthRepository.findByAccountFE(email);
//                if (staff.isPresent()) {
//                    return staff.get().getId().describeConstable();
//                }
//            }
//        }
//        if (email.matches(".*ph\\d{5}@fpt.edu.vn$") || email.endsWith("@gmail.com")) {
//            Optional<Student> student = studentRepository.findByEmailFpt(email);
//            if (student.isPresent()) {
//                return student.get().getId().describeConstable();
//            }
//        }
//        throw new RuntimeException("Invalid email");
//    }
//
//}
