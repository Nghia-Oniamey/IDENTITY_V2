package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.repository.StudentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentAuthRepository extends StudentRepository {

    @Query("SELECT s FROM Student s WHERE s.emailFpt = :email")
    Optional<Student> findByEmailFpt(String email);

}
