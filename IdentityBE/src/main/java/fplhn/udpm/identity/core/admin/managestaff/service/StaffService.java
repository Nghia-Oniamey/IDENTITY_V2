package fplhn.udpm.identity.core.admin.managestaff.service;

import fplhn.udpm.identity.core.admin.managestaff.model.request.AddStaffRequest;
import fplhn.udpm.identity.core.admin.managestaff.model.request.UpdateStaffRequest;
import fplhn.udpm.identity.core.admin.managestaff.model.response.StaffResponse;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {

    PageableObject<List<StaffResponse>> getAllNhanVien(Pageable pageable);

    ResponseObject getAllNhanVien();

    ResponseObject createNhanVien(AddStaffRequest request);

    ResponseObject updateNhanVien(Long id, UpdateStaffRequest request);

    ResponseObject deleteNhanVien(Long id);

    ResponseObject getDetailNhanVien(Long id);

}
