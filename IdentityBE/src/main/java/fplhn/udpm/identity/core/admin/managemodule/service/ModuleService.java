package fplhn.udpm.identity.core.admin.managemodule.service;

import fplhn.udpm.identity.core.admin.managemodule.model.request.CreateModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.request.FindModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.request.UpdateModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.response.ListModuleResponse;
import fplhn.udpm.identity.core.admin.managemodule.model.response.ModuleResponse;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModuleService {
    PageableObject<ModuleResponse> getModuleByListId(FindModuleRequest request);

    List<ListModuleResponse> getListModule();

    ResponseObject addModule(CreateModuleRequest createModuleRequest);

    ResponseModel updateModule(UpdateModuleRequest updateModuleRequest, Long id);

    ResponseModel updateXoaMemModule(Long id);
}
