package fplhn.udpm.identity.core.admin.managerole.service.impl;

import fplhn.udpm.identity.core.admin.managerole.model.request.AdminRoleRequest;
import fplhn.udpm.identity.core.admin.managerole.model.response.AdminRoleResponse;
import fplhn.udpm.identity.core.admin.managerole.repository.AdminRoleRepository;
import fplhn.udpm.identity.core.admin.managerole.service.AdminRoleService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import fplhn.udpm.identity.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements AdminRoleService {

    private final AdminRoleRepository adminRoleRepository;
    private final PaginationUtil paginationUtil;

    @Override
    public PageableObject<AdminRoleResponse> findAllEntity(AdminRoleRequest request) {
        Pageable pageable = paginationUtil.pageable(request);
        Page<AdminRoleResponse> responses = adminRoleRepository.findAllRole(request, pageable);
        return new PageableObject<>(responses);
    }

    @Override
    public ResponseObject create(AdminRoleRequest request) {
        String ma = request.getMa();

        Optional<Role> roleOptional = adminRoleRepository.findByMa(ma);
        if (roleOptional.isPresent()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Mã chức vụ đã tồn tại");
        }

        Role role = new Role();
        role.setMa(request.getMa());
        role.setTen(request.getTen());
        role.setDeleteStatus(DeleteStatus.NOT_DELETED);
        Role savedRole = adminRoleRepository.save(role);
        return new ResponseObject(savedRole, HttpStatus.OK, "Thêm mới thành công");
    }

    @Override
    public ResponseObject update(AdminRoleRequest request) {
        Optional<Role> roleOptional = adminRoleRepository.findById(request.getId());
        if (roleOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Chức vụ không tồn tại");
        }

        if (!roleOptional.get().getMa().trim().equalsIgnoreCase(request.getMa().trim())) {
            if (adminRoleRepository.existsByMa(request.getMa().trim())) {
                return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Mã chức vụ đã tồn tại: " + request.getMa().trim());
            }
        }
        Role role = roleOptional.get();
        role.setTen(request.getTen());
        Role savedRole = adminRoleRepository.save(role);
        return new ResponseObject(savedRole, HttpStatus.OK, "Cập nhật thành công");
    }

    @Override
    public ResponseObject findById(Long id) {
        Optional<Role> roleOptional = adminRoleRepository.findById(id);
        if (roleOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Chức vụ không tồn tại");
        }

        Role role = roleOptional.get();

        return new ResponseObject(role, HttpStatus.OK, "Lấy thông tin thành công");
    }

    @Override
    public ResponseObject delete(Long id) {
        Optional<Role> roleOptional = adminRoleRepository.findById(id);
        if (roleOptional.isEmpty()) {
            return new ResponseObject(null, HttpStatus.BAD_REQUEST, "Chức vụ không tồn tại");
        }

        Role role = roleOptional.get();
        if (roleOptional.get().getDeleteStatus().equals(DeleteStatus.DELETED)) {
            role.setDeleteStatus(DeleteStatus.NOT_DELETED);
        } else {
            role.setDeleteStatus(DeleteStatus.DELETED);
        }
        adminRoleRepository.save(role);
        return new ResponseObject(null, HttpStatus.OK, "Xóa thành công");
    }
}
