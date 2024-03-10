package fplhn.udpm.identity.core.admin.managestudent.service;

import fplhn.udpm.identity.core.admin.managestudent.model.request.AddStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.request.FindStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.request.UpdateStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.CbbBoMonTheoCoSoRespone;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.ListStudentRespone;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.StudentRespone;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.entity.Student;

import java.util.List;

public interface StudentService {
    PageableObject<StudentRespone> getAllSinhVien(FindStudentRequest request);

    List<ListStudentRespone> getListSinhVien();

    List<CbbBoMonTheoCoSoRespone> getComboboxBoMonTheoCoSo();

    Student addSinhVien(AddStudentRequest request);

    StudentRespone detailSinhVien(Long id);

    Student updateProduct(Long id, UpdateStudentRequest request);
}
