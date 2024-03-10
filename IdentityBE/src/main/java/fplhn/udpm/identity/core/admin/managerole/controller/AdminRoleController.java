package fplhn.udpm.identity.core.admin.managerole.controller;

import fplhn.udpm.identity.core.admin.managerole.model.request.AdminRoleRequest;
import fplhn.udpm.identity.core.admin.managerole.model.response.AdminRoleResponse;
import fplhn.udpm.identity.core.admin.managerole.service.AdminRoleService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.util.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
@CrossOrigin("*")
@Slf4j
public class AdminRoleController {
    private final AdminRoleService adminRoleService;

    @GetMapping("")
    public Object findAllRole(AdminRoleRequest roleDTO) {
        PageableObject<AdminRoleResponse> listRole = adminRoleService.findAllEntity(roleDTO);

        return ResponseHelper.getResponse(listRole, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable("id") Long id) {
        Object adminBrandResponse = adminRoleService.findById(id);

        return ResponseHelper.getResponse(adminBrandResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public Object create(@RequestBody @Valid AdminRoleRequest roleDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseHelper.getErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);

        return ResponseHelper.getResponse(adminRoleService.create(roleDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable("id") Long id, @RequestBody @Valid AdminRoleRequest roleDTO, BindingResult bindingResult) {
        roleDTO.setId(id);

        if (bindingResult.hasErrors())
            return ResponseHelper.getErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);

        return ResponseHelper.getResponse(adminRoleService.update(roleDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") Long id) {
        return ResponseHelper.getResponse(adminRoleService.delete(id), HttpStatus.OK);
    }

}
