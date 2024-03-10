package fplhn.udpm.identity.core.admin.managestaff.service.impl;

import fplhn.udpm.identity.core.admin.managestaff.model.request.AddStaffRequest;
import fplhn.udpm.identity.core.admin.managestaff.model.request.UpdateStaffRequest;
import fplhn.udpm.identity.core.admin.managestaff.model.response.DetailStaffResponse;
import fplhn.udpm.identity.core.admin.managestaff.model.response.StaffResponse;
import fplhn.udpm.identity.core.admin.managestaff.repository.CampusStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.repository.DepartmentCampusStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.repository.DepartmentStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.repository.ModuleStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.repository.RoleStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.repository.StaffCustomRepository;
import fplhn.udpm.identity.core.admin.managestaff.repository.StaffModuleRoleExtendRepository;
import fplhn.udpm.identity.core.admin.managestaff.service.StaffService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {

    private final StaffCustomRepository staffCustomRepository;

    private final CampusStaffRepository campusStaffRepository;

    private final DepartmentCampusStaffRepository departmentCampusStaffRepository;

    private final DepartmentStaffRepository departmentStaffRepository;

    private final StaffModuleRoleExtendRepository staffModuleRoleExtendRepository;

    private final ModuleStaffRepository moduleStaffRepository;

    private final RoleStaffRepository roleStaffRepository;


    @Override
    public PageableObject<List<StaffResponse>> getAllNhanVien(Pageable pageable) {
        Page<List<StaffResponse>> nhanVienPage = staffCustomRepository.findAllNhanVien(pageable);
        return new PageableObject<>(nhanVienPage);
    }

    @Override
    public ResponseObject getAllNhanVien() {
        List<StaffResponse> nhanVienList = staffCustomRepository.findAllNhanVien();
        return new ResponseObject(nhanVienList, HttpStatus.OK, "Lấy danh sách nhân viên thành công");
    }

    @Override
    public ResponseObject createNhanVien(AddStaffRequest request) {

        String maNhanVien = request.getMaNhanVien();
        String taiKhoanFpt = request.getEmailFpt();
        String taiKhoanFe = request.getEmailFe();
        Long idBoMon = request.getIdBoMon();
        Long idCoSo = request.getIdCoSo();
        String soDienThoai = request.getSoDienThoai();
        String tenNhanVien = request.getTenNhanVien();

        Optional<Staff> nhanVienOptional = staffCustomRepository.findByStaffCode(maNhanVien);
        Optional<Staff> nhanVienOptionalEmailFpt = staffCustomRepository.findByAccountFPT(taiKhoanFpt);
        Optional<Staff> nhanVienOptionalEmailFe = staffCustomRepository.findByAccountFE(taiKhoanFe);
        Optional<Campus> coSoOptional = campusStaffRepository.findById(idCoSo);
        Optional<Department> boMonOptional = departmentStaffRepository.findById(idBoMon);
        if (coSoOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Cơ sở không tồn tại");
        }

        if (boMonOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Bộ môn không tồn tại");
        }

        Optional<DepartmentCampus> boMonTheoCoSoOptional = departmentCampusStaffRepository.findByCampusAndDepartment(coSoOptional.get(), boMonOptional.get());

        if (boMonTheoCoSoOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Bộ môn theo cơ sở không tồn tại");
        }

        if (nhanVienOptional.isPresent()) {
            if (nhanVienOptional.get().getDeleteStatus().equals(DeleteStatus.DELETED)) {
                return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Mã nhân viên đã tồn tại");
            }
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Mã nhân viên đã tồn tại");
        }
        if (nhanVienOptionalEmailFpt.isPresent()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Email FPT đã tồn tại");
        }
        if (nhanVienOptionalEmailFe.isPresent()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Email FE đã tồn tại");
        }
        if (!taiKhoanFe.toLowerCase().contains(maNhanVien.toLowerCase()) || !taiKhoanFpt.toLowerCase().contains(maNhanVien.toLowerCase())) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Email FE hoặc Email FPT không chứa mã nhân viên");
        }


        Staff staff = new Staff();
        staff.setStaffCode(maNhanVien);
        staff.setFullName(tenNhanVien);
        staff.setAccountFE(taiKhoanFe);
        staff.setAccountFPT(taiKhoanFpt);
        staff.setDepartmentCampus(boMonTheoCoSoOptional.get());
        staff.setPhoneNumber(soDienThoai);
        staff.setDeleteStatus(DeleteStatus.NOT_DELETED);
        Staff staffSave = staffCustomRepository.save(staff);
        return new ResponseObject(staffSave, HttpStatus.OK, "Thêm mới thành công");
    }

    @Override
    public ResponseObject updateNhanVien(Long id, UpdateStaffRequest request) {
        Optional<Staff> nhanVienOptional = staffCustomRepository.findById(id);
        if (nhanVienOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Nhân viên không tồn tại");
        }
        Staff staff = nhanVienOptional.get();
        String accountFPT = request.getEmailFpt();
        String accountFE = request.getEmailFe();
        Long departmentId = request.getIdBoMon();
        Long campusId = request.getIdCoSo();
        String phoneNumber = request.getSoDienThoai();
        String fullName = request.getTenNhanVien();

        Optional<Campus> coSoOptional = campusStaffRepository.findById(campusId);
        if (coSoOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Cơ sở không tồn tại");
        }

        Optional<Department> boMonOptional = departmentStaffRepository.findById(departmentId);
        if (boMonOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Bộ môn không tồn tại");
        }

        Optional<DepartmentCampus> boMonTheoCoSoOptional = departmentCampusStaffRepository.findByCampusAndDepartment(coSoOptional.get(), boMonOptional.get());

        if (staff.getAccountFE().equals(accountFE) && staff.getAccountFPT().equals(accountFPT)) {
            if (!accountFE.toLowerCase().contains(staff.getStaffCode().toLowerCase()) || !accountFPT.toLowerCase().contains(staff.getStaffCode().toLowerCase())) {
                log.info("accountFE: {}, accountFPT: {}, maNhanVien: {}", accountFE, accountFPT, staff.getStaffCode());
                return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Email FE hoặc Email FPT không chứa mã nhân viên");
            }
        }

        if (boMonTheoCoSoOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Bộ môn không tồn tại");
        }

        if (staff.getPhoneNumber().equals(phoneNumber)) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Số điện thoại đã tồn tại");
        }

        staff.setFullName(fullName);
        staff.setAccountFE(accountFE);
        staff.setAccountFPT(accountFPT);
        staff.setDepartmentCampus(boMonTheoCoSoOptional.get());
        staff.setPhoneNumber(phoneNumber);
        Staff staffSave = staffCustomRepository.save(staff);
        return new ResponseObject(staffSave, HttpStatus.OK, "Cập nhật thành công");
    }

    @Override
    public ResponseObject deleteNhanVien(Long id) {
        Optional<Staff> nhanVienOptional = staffCustomRepository.findById(id);
        if (nhanVienOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Nhân viên không tồn tại");
        }
        Staff staff = nhanVienOptional.get();
        staff.setDeleteStatus(DeleteStatus.DELETED);
        Staff staffDeleted = staffCustomRepository.save(staff);
        return new ResponseObject(staffDeleted, HttpStatus.OK, "Xóa thành công");
    }

    @Override
    public ResponseObject getDetailNhanVien(Long id) {
        DetailStaffResponse detailStaffResponse = staffCustomRepository.findDetailNhanVienById(id);
        if (detailStaffResponse == null) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Nhân viên không tồn tại");
        }
        return new ResponseObject(detailStaffResponse, HttpStatus.OK, "Lấy thông tin thành công");
    }

}
