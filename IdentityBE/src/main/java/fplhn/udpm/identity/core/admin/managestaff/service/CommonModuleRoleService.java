package fplhn.udpm.identity.core.admin.managestaff.service;

import fplhn.udpm.identity.core.common.config.ResponseObject;

public interface CommonModuleRoleService {

    ResponseObject findALlRole();

    ResponseObject findALlModule();

    ResponseObject findListRoleAndListModuleByStaffId(Long staffId);

}
