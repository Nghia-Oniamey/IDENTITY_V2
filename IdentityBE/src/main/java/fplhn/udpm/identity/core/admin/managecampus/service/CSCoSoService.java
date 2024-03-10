package fplhn.udpm.identity.core.admin.managecampus.service;


import fplhn.udpm.identity.core.admin.managecampus.model.request.CSCreateCoSoRequest;
import fplhn.udpm.identity.core.admin.managecampus.model.request.CSFindCoSoRequest;
import fplhn.udpm.identity.core.admin.managecampus.model.response.CSCoSoResponse;
import fplhn.udpm.identity.core.admin.managecampus.model.response.CSListCoSoResponse;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;

import java.util.List;

public interface CSCoSoService {

    PageableObject<CSCoSoResponse> getAllCoSo(CSFindCoSoRequest request);

    ResponseModel addCoSo(CSCreateCoSoRequest csCreateCoSoRequest);

    ResponseModel updateCoSo(CSCreateCoSoRequest csCreateCoSoRequest, Long id);

    ResponseModel updateXoaMemCoSo(Long id);

    List<CSListCoSoResponse> getListCoSo();

}
