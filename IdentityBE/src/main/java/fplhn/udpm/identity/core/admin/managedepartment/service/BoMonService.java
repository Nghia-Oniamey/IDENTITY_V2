package fplhn.udpm.identity.core.admin.managedepartment.service;

import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMAddBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMFindBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMUpdateBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.response.BMBoMonResponse;
import fplhn.udpm.identity.core.admin.managedepartment.model.response.BMListBoMonResponse;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;

import java.util.List;

public interface BoMonService {

    PageableObject<BMBoMonResponse> getAllBoMon(BMFindBoMonRequest request);

    ResponseModel addBoMon(BMAddBoMonRequest request);

    ResponseModel updateBoMon(BMUpdateBoMonRequest request, Long id);

    ResponseModel deleteBoMon(Long id);

    List<BMListBoMonResponse> getListBoMon();

}
