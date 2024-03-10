package fplhn.udpm.identity.core.admin.managestudent.service.impl;

import fplhn.udpm.identity.core.admin.managestudent.model.request.AddStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.request.FindStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.request.UpdateStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.CbbBoMonTheoCoSoRespone;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.ListStudentRespone;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.StudentRespone;
import fplhn.udpm.identity.core.admin.managestudent.repository.StudentsRepository;
import fplhn.udpm.identity.core.admin.managestudent.service.StudentService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.entity.Student;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImplement implements StudentService {
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private DepartmentCampusRepository departmentCampusRepository;

    @Override
    public PageableObject<StudentRespone> getAllSinhVien(FindStudentRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        if (request.getArrayField() == null || request.getArrayField().length == 0) {
            Page<StudentRespone> res = studentsRepository.searchAll(pageable);
            return new PageableObject<>(res);
        }
        Page<StudentRespone> res = studentsRepository.search(pageable, request);
        return new PageableObject<>(res);
    }

    @Override
    public List<ListStudentRespone> getListSinhVien() {
        return studentsRepository.getListSinhVien();
    }

    @Override
    public List<CbbBoMonTheoCoSoRespone> getComboboxBoMonTheoCoSo() {
        return studentsRepository.getComboboxBoMonTheoCoSo();
    }

    @Override
    public Student addSinhVien(AddStudentRequest request) {
        Student student = Student.builder()
                .maSinhVien("PH" + studentsRepository.getListSinhVien().size())
                .hoTen(request.getTenSinhVien())
                .emailFpt(request.getMailSinhVien())
                .soDienThoai(request.getSdtSinhVien())
                .departmentCampus(departmentCampusRepository.findById(request.getIdBoMonTheoCoSo()).get())
                .build();
        student.setDeleteStatus(DeleteStatus.NOT_DELETED);
        studentsRepository.save(student);
        return student;
    }

    @Override
    public StudentRespone detailSinhVien(Long id) {
        return studentsRepository.DetailSinhVien(id);
    }

    @Override
    public Student updateProduct(Long id, UpdateStudentRequest request) {
        Student studentupdate = Student.builder()
                .maSinhVien(request.getMaSinhVien())
                .hoTen(request.getTenSinhVien())
                .emailFpt(request.getMailSinhVien())
                .soDienThoai(request.getSdtSinhVien())
                .departmentCampus(departmentCampusRepository.findById(request.getIdBoMonTheoCoSo()).get())
                .build();
        studentupdate.setId(id);
        studentupdate.setDeleteStatus(DeleteStatus.NOT_DELETED);
        studentsRepository.save(studentupdate);
        return studentupdate;
    }
}
