package fplhn.udpm.identity.core.admin.managedepartmentcampus.service;

import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoDetailRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.BMBoMonTheoCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.BMListBoMonTheoCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.CSListCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.NVListNhanVienResponse;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;

import java.util.List;

public interface BMBoMonTheoCoSoService {

    PageableObject<BMBoMonTheoCoSoResponse> getAllBoMonTheoCoSo(Long id, BMBoMonTheoCoSoDetailRequest request);

    ResponseModel addBoMonTheoCoSo(BMBoMonTheoCoSoRequest request);

    ResponseModel updateBoMonTheoCoSo(BMBoMonTheoCoSoRequest request, Long id);

    ResponseModel deleteBoMonTheoCoSo(Long id);

    List<CSListCoSoResponse> getListCoSo();

    List<NVListNhanVienResponse> getListNhanVien();

    List<BMListBoMonTheoCoSoResponse> getListBoMonTheoCoSo(Long id);

    String getTenBoMon(Long id);

}
