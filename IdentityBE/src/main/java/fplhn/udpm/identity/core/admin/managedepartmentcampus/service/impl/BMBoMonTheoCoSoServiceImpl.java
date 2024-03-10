package fplhn.udpm.identity.core.admin.managedepartmentcampus.service.impl;

import fplhn.udpm.identity.core.admin.managecampus.repository.CSCampusRepository;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoDetailRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.BMBoMonTheoCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.BMListBoMonTheoCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.CSListCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.NVListNhanVienResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.repository.BMDepartmentCampusRepository;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.service.BMBoMonTheoCoSoService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import fplhn.udpm.identity.repository.DepartmentRepository;
import fplhn.udpm.identity.repository.StaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BMBoMonTheoCoSoServiceImpl implements BMBoMonTheoCoSoService {

    @Autowired
    private BMDepartmentCampusRepository bmtcsRepository;

    @Autowired
    private CSCampusRepository csCoSoRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public PageableObject<BMBoMonTheoCoSoResponse> getAllBoMonTheoCoSo(Long id, BMBoMonTheoCoSoDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        if (request.getArrayField() == null || request.getArrayField().length == 0) {
            Page<BMBoMonTheoCoSoResponse> res = bmtcsRepository.getBoMonTheoCoSosByValueFind(id, pageable);
            return new PageableObject<>(res);
        }
        Page<BMBoMonTheoCoSoResponse> res = bmtcsRepository.getBoMonTheoCoSosByValueFind(id, pageable, request);
        return new PageableObject<>(res);
    }

    @Override
    public ResponseModel addBoMonTheoCoSo(BMBoMonTheoCoSoRequest request) {

        Optional<Department> boMonOptional = departmentRepository.findById(request.getIdBoMon());
        Optional<Campus> coSoOptional = csCoSoRepository.findById(request.getIdCoSo());
        Optional<Staff> nhanVienOptional = staffRepository.findById(request.getIdCNBM());

        if (bmtcsRepository.existsByIdBoMonAndIdCoSoAndIdAdd(request).isPresent()) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Bộ môn theo cơ sở đã tồn tại !");
        }

        if (coSoOptional.isPresent() && nhanVienOptional.isPresent() && boMonOptional.isPresent()) {
            DepartmentCampus departmentCampus = new DepartmentCampus();
            departmentCampus.setDepartment(boMonOptional.get());
            departmentCampus.setDeleteStatus(DeleteStatus.NOT_DELETED);
            departmentCampus.setCampus(coSoOptional.get());
            departmentCampus.setStaff(nhanVienOptional.get());
            bmtcsRepository.save(departmentCampus);
            return new ResponseModel(HttpStatus.CREATED, "Thêm thành công ");
        } else if (coSoOptional.isEmpty()) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Không tìm thấy cơ sở trên");
        } else if (nhanVienOptional.isEmpty()) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Không tìm thấy nhân viên trên");
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Không tìm thấy bộ môn trên");
        }
    }

    @Override
    public ResponseModel updateBoMonTheoCoSo(BMBoMonTheoCoSoRequest request, Long id) {

        Optional<DepartmentCampus> boMonTheoCoSoOptional = bmtcsRepository.findById(id);
        if (boMonTheoCoSoOptional.isPresent()) {
            Optional<Department> boMonOptional = departmentRepository.findById(request.getIdBoMon());
            Optional<Campus> coSoOptional = csCoSoRepository.findById(request.getIdCoSo());
            Optional<Staff> nhanVienOptional = staffRepository.findById(request.getIdCNBM());

            if (coSoOptional.isPresent() && nhanVienOptional.isPresent() && boMonOptional.isPresent()) {

                if (boMonTheoCoSoOptional.get().getCampus().equals(coSoOptional.get())) {
                    boMonTheoCoSoOptional.get().setStaff(nhanVienOptional.get());
                    boMonTheoCoSoOptional.get().setDepartment(boMonOptional.get());
                    boMonTheoCoSoOptional.get().setCampus(coSoOptional.get());
                    bmtcsRepository.save(boMonTheoCoSoOptional.get());
                } else {
                    if (isDuplicateRecord(boMonTheoCoSoOptional.get())) {
                        return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Cập nhật trùng cơ sở hoặc để như cũ");
                    }
                    boMonTheoCoSoOptional.get().setStaff(nhanVienOptional.get());
                    boMonTheoCoSoOptional.get().setDepartment(boMonOptional.get());
                    boMonTheoCoSoOptional.get().setCampus(coSoOptional.get());
                    bmtcsRepository.save(boMonTheoCoSoOptional.get());
                }


                return new ResponseModel(HttpStatus.OK, "Sửa thành công !");
            } else if (coSoOptional.isEmpty()) {
                return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Không tìm thấy cơ sở trên");
            } else if (nhanVienOptional.isEmpty()) {
                return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Không tìm thấy nhân viên trên");
            } else if (boMonOptional.isEmpty()) {
                return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Không tìm thấy bộ môn trên");
            } else {
                return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Sửa thất bại");
            }
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Bộ môn theo cơ sở trên không tồn tại");
        }

    }

    private boolean isDuplicateRecord(DepartmentCampus departmentCampus) {
        Campus campus = departmentCampus.getCampus();
        Department department = departmentCampus.getDepartment();

        return bmtcsRepository.existsBoMonTheoCoSoByCampusAndDepartment(campus, department);
    }

    @Override
    public ResponseModel deleteBoMonTheoCoSo(Long id) {
        Optional<DepartmentCampus> boMonTheoCoSoOptional = bmtcsRepository.findById(id);

        if (boMonTheoCoSoOptional.isPresent()) {
            if (boMonTheoCoSoOptional.get().getDeleteStatus().equals(DeleteStatus.NOT_DELETED)) {
                boMonTheoCoSoOptional.get().setDeleteStatus(DeleteStatus.DELETED);
            } else {
                boMonTheoCoSoOptional.get().setDeleteStatus(DeleteStatus.NOT_DELETED);
            }
            bmtcsRepository.save(boMonTheoCoSoOptional.get());
            return new ResponseModel(HttpStatus.OK, "Chỉnh sửa thành công bộ môn theo cơ sở");
        } else {
            return new ResponseModel(HttpStatus.OK, "Bộ môn theo cơ sở không tồn tại");
        }
    }

    @Override
    public List<CSListCoSoResponse> getListCoSo() {
        return bmtcsRepository.getListCoSo();
    }

    @Override
    public List<NVListNhanVienResponse> getListNhanVien() {
        return bmtcsRepository.getListNhanVien();
    }

    @Override
    public List<BMListBoMonTheoCoSoResponse> getListBoMonTheoCoSo(Long id) {
        return bmtcsRepository.getListBoMonTheoCoSo(id);
    }

    @Override
    public String getTenBoMon(Long id) {
        return bmtcsRepository.getTenBoMon(id);
    }
}
