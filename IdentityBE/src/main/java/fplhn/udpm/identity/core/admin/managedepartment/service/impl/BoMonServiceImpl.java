package fplhn.udpm.identity.core.admin.managedepartment.service.impl;

import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMAddBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMFindBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMUpdateBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.response.BMBoMonResponse;
import fplhn.udpm.identity.core.admin.managedepartment.model.response.BMListBoMonResponse;
import fplhn.udpm.identity.core.admin.managedepartment.repository.DepartmentExtendRepository;
import fplhn.udpm.identity.core.admin.managedepartment.service.BoMonService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoMonServiceImpl implements BoMonService {

    @Autowired
    private DepartmentExtendRepository boMonRepository;

    @Override
    public PageableObject<BMBoMonResponse> getAllBoMon(BMFindBoMonRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        if (request.getArrayField() == null || request.getArrayField().length == 0) {
            Page<BMBoMonResponse> res = boMonRepository.getAllBoMon(pageable);
            return new PageableObject<>(res);
        }
        Page<BMBoMonResponse> res = boMonRepository.getAllBoMonByFilter(pageable, request);
        return new PageableObject<>(res);
    }

    @Override
    public ResponseModel addBoMon(BMAddBoMonRequest request) {
        if (request.getTenBoMon().length() > 255) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Tên bộ môn vượt quá định dạng");
        }
        request.setTenBoMon(request.getTenBoMon().replaceAll("\\s+", " "));
        request.setMaBoMon(request.getMaBoMon().replaceAll("\\s+", " "));
        boolean tonTai = boMonRepository.existsByMa(request.getMaBoMon().trim());
        if (tonTai) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Tên bộ môn Đã Tồn Tại");
        } else {
            Department department = new Department();
            department.setDeleteStatus(DeleteStatus.NOT_DELETED);
            department.setTen(request.getTenBoMon().trim());
            department.setMa(request.getMaBoMon().trim());
            this.boMonRepository.save(department);
            return new ResponseModel(HttpStatus.CREATED, "Thêm thành công bộ môn");
        }
    }

    @Override
    public ResponseModel updateBoMon(BMUpdateBoMonRequest request, Long id) {
        if (request.getTenBoMon().length() > 255) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Tên bộ môn vượt quá định dạng");
        }
        request.setTenBoMon(request.getTenBoMon().replaceAll("\\s+", " "));
        request.setMaBoMon(request.getMaBoMon().replaceAll("\\s+", " "));
        boolean tonTai = boMonRepository.existsById(id);
        if (tonTai) {
            Optional<Department> boMonOptional = boMonRepository.findById(id);

            if (!boMonOptional.get().getMa().trim().equalsIgnoreCase(request.getMaBoMon().trim())) {
                if (boMonRepository.existsByMa(request.getMaBoMon().trim())) {
                    return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Mã bộ môn đã tồn tại: " +
                            request.getMaBoMon().trim());
                }
            }
            boMonOptional.get().setTen(request.getTenBoMon().trim());
            boMonOptional.get().setMa(request.getMaBoMon().trim());
            this.boMonRepository.save(boMonOptional.get());
            return new ResponseModel(HttpStatus.OK, "Cập nhật thành công");
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "bộ môn không tồn tại");
        }
    }

    @Override
    public ResponseModel deleteBoMon(Long id) {
        boolean tonTai = boMonRepository.existsById(id);

        if (tonTai) {
            Optional<Department> boMonOptional = boMonRepository.findById(id);

            if (boMonOptional.get().getDeleteStatus() == DeleteStatus.DELETED) {
                boMonOptional.get().setDeleteStatus(DeleteStatus.NOT_DELETED);
            } else {
                boMonOptional.get().setDeleteStatus(DeleteStatus.DELETED);
            }

            this.boMonRepository.save(boMonOptional.get());
            return new ResponseModel(HttpStatus.OK, "Cập nhật thành công");
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "bộ môn không tồn tại");
        }
    }

    @Override
    public List<BMListBoMonResponse> getListBoMon() {
        return boMonRepository.getListBoMon();
    }

}
