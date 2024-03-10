package fplhn.udpm.identity.core.admin.managestaff.service.impl;

import fplhn.udpm.identity.core.admin.managestaff.model.response.ModuleResponse;
import fplhn.udpm.identity.core.admin.managestaff.model.response.ModuleRoleResponse;
import fplhn.udpm.identity.core.admin.managestaff.model.response.RoleResponse;
import fplhn.udpm.identity.core.admin.managestaff.repository.ModuleStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.repository.RoleStaffRepository;
import fplhn.udpm.identity.core.admin.managestaff.service.CommonModuleRoleService;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonModuleRoleServiceImpl implements CommonModuleRoleService {

    private final ModuleStaffRepository moduleStaffRepository;

    private final RoleStaffRepository roleStaffRepository;

    public CommonModuleRoleServiceImpl(
            ModuleStaffRepository moduleStaffRepository,
            RoleStaffRepository roleStaffRepository
    ) {
        this.moduleStaffRepository = moduleStaffRepository;
        this.roleStaffRepository = roleStaffRepository;
    }

    @Override
    public ResponseObject findALlRole() {
        List<RoleResponse> roleResponses = roleStaffRepository.getAllRole();
        return new ResponseObject().success(roleResponses, "Lấy danh sách chức vụ thành công!");
    }

    @Override
    public ResponseObject findALlModule() {
        List<ModuleResponse> moduleResponses = moduleStaffRepository.getAllModule();
        return new ResponseObject().success(moduleResponses, "Lấy danh sách module thành công!");
    }

    @Override
    public ResponseObject findListRoleAndListModuleByStaffId(Long staffId) {
        List<RoleResponse> roleResponses = roleStaffRepository.findAllRoleByStaffId(staffId);
        List<ModuleResponse> moduleResponses = moduleStaffRepository.findAllModuleByStaffId(staffId);
        ModuleRoleResponse moduleRoleResponse = new ModuleRoleResponse(moduleResponses, roleResponses);
        return new ResponseObject().success(moduleRoleResponse, "Lấy danh sách chức vụ và module thành công!");
    }

}
