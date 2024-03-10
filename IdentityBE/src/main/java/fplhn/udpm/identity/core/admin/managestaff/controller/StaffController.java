package fplhn.udpm.identity.core.admin.managestaff.controller;

import fplhn.udpm.identity.core.admin.managestaff.model.request.AddStaffRequest;
import fplhn.udpm.identity.core.admin.managestaff.model.request.PaginationStaffRequest;
import fplhn.udpm.identity.core.admin.managestaff.model.request.UpdateStaffRequest;
import fplhn.udpm.identity.core.admin.managestaff.service.CourseCampusStaffService;
import fplhn.udpm.identity.core.admin.managestaff.service.StaffService;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nhan-vien")
@CrossOrigin("*")
@Slf4j
public class StaffController {

    private final StaffService staffService;

    private final CourseCampusStaffService courseCampusStaffService;

    @GetMapping("/danh-sach-nhan-vien")
    public ResponseEntity<?> getAllNhanVien(PaginationStaffRequest request) {
        log.info("page: {}, size: {}", request.getPage(), request.getSize());
        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getSize() == 0 ? 10 : request.getSize(),
                Sort.by(
                        (request.getType() == Sort.Direction.DESC || request.getType() == null) ? Sort.Direction.DESC : Sort.Direction.ASC,
                        (request.getColumn() == null || request.getColumn().isEmpty() || request.getColumn() == "") ? "id" : request.getColumn()
                ));
        return new ResponseEntity<>(staffService.getAllNhanVien(pageable), null, 200);
    }

    @GetMapping("/danh-sach-nhan-vien-all")
    public ResponseEntity<?> getAllNhanVien() {
        return new ResponseEntity<>(staffService.getAllNhanVien(), null, 200);
    }

    @PostMapping("/them-nhan-vien")
    public ResponseEntity<?> createNhanVien(@RequestBody @Valid AddStaffRequest request) {
        ResponseObject responseObject = staffService.createNhanVien(request);
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

    @PutMapping("/cap-nhat-nhan-vien/{id}")
    public ResponseEntity<?> updateNhanVien(@PathVariable Long id, @RequestBody @Valid UpdateStaffRequest request) {
        log.info("request: {}", request);
        ResponseObject responseObject = staffService.updateNhanVien(id, request);
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

    @GetMapping("/danh-sach-bo-mon")
    public ResponseEntity<?> getAllBoMon() {
        ResponseObject responseModel = courseCampusStaffService.findAllDepartment();
        return new ResponseEntity<>(responseModel, responseModel.getStatus());
    }

    @GetMapping("/danh-sach-co-so")
    public ResponseEntity<?> getAllCoSo() {
        ResponseObject responseModel = courseCampusStaffService.findAllCampus();
        return new ResponseEntity<>(responseModel, responseModel.getStatus());
    }

    @PutMapping("/xoa-nhan-vien/{id}")
    public ResponseEntity<?> deleteNhanVien(@PathVariable Long id) {
        ResponseObject responseModel = staffService.deleteNhanVien(id);
        return new ResponseEntity<>(responseModel, responseModel.getStatus());
    }

    @GetMapping("/chi-tiet-nhan-vien/{id}")
    public ResponseEntity<?> getNhanVienById(@PathVariable Long id) {
        ResponseObject responseModel = staffService.getDetailNhanVien(id);
        return new ResponseEntity<>(responseModel, responseModel.getStatus());
    }

}
