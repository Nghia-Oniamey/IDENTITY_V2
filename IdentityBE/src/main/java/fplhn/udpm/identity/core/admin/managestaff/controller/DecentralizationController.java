package fplhn.udpm.identity.core.admin.managestaff.controller;

import fplhn.udpm.identity.core.admin.managestaff.service.CommonModuleRoleService;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/module-role-staff")
@CrossOrigin("*")
@Slf4j
public class DecentralizationController {

    private final CommonModuleRoleService commonModuleRoleService;

    @GetMapping("/list-role")
    public ResponseEntity<?> getAllRole() {
        ResponseObject responseObject = commonModuleRoleService.findALlRole();
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

    @GetMapping("/list-module")
    public ResponseEntity<?> getAllModule() {
        ResponseObject responseObject = commonModuleRoleService.findALlModule();
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

    @GetMapping("/list-role-module-of-staff/{staffId}")
    public ResponseEntity<?> getListRoleByStaffId(@PathVariable Long staffId) {
        ResponseObject responseObject = commonModuleRoleService.findListRoleAndListModuleByStaffId(staffId);
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

}
