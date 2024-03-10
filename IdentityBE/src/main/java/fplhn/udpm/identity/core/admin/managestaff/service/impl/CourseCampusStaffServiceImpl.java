package fplhn.udpm.identity.core.admin.managestaff.service.impl;


import fplhn.udpm.identity.core.admin.managestaff.model.response.CampusResponse;
import fplhn.udpm.identity.core.admin.managestaff.model.response.DepartmentResponse;
import fplhn.udpm.identity.core.admin.managestaff.repository.DepartmentCampusStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.service.CourseCampusStaffService;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCampusStaffServiceImpl implements CourseCampusStaffService {

    private DepartmentCampusStaffRepository departmentCampusStaffRepository;

    @Autowired
    public void setBoMonCoSoNhanVienRepository(DepartmentCampusStaffRepository departmentCampusStaffRepository) {
        this.departmentCampusStaffRepository = departmentCampusStaffRepository;
    }

    @Override
    public ResponseObject findAllDepartment() {
        List<DepartmentResponse> departmentResponses = departmentCampusStaffRepository.findAllDepartment();
        if (departmentResponses.isEmpty()) {
            return new ResponseObject(null, HttpStatus.NOT_FOUND, "Không tìm thấy bộ môn nào");
        } else {
            return new ResponseObject(departmentResponses, HttpStatus.OK, "Lấy danh sách bộ môn thành công");
        }
    }

    @Override
    public ResponseObject findAllCampus() {
        List<CampusResponse> campusResponses = departmentCampusStaffRepository.findAllCampus();
        if (campusResponses.isEmpty()) {
            return new ResponseObject(null, HttpStatus.NOT_FOUND, "Không tìm thấy cơ sở nào");
        } else {
            return new ResponseObject(campusResponses, HttpStatus.OK, "Lấy danh sách cơ sở thành công");
        }
    }
}
