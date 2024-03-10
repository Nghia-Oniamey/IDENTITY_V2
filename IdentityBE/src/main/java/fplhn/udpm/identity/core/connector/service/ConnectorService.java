package fplhn.udpm.identity.core.connector.service;

import fplhn.udpm.identity.core.common.config.ResponseObject;
import fplhn.udpm.identity.core.connector.model.request.CommonUserRoleRequest;
import fplhn.udpm.identity.core.connector.model.request.GetListDetailUserRequest;
import fplhn.udpm.identity.core.connector.model.request.GetListUserFromRoleCodeRequest;
import fplhn.udpm.identity.core.connector.model.response.DetailUserResponseStaff;
import fplhn.udpm.identity.core.connector.model.response.RolesResponse;

import java.util.List;

public interface ConnectorService {

    List<DetailUserResponseStaff> getListDetailUser(GetListDetailUserRequest request);

    List<DetailUserResponseStaff> getListUserFromRoleCode(GetListUserFromRoleCodeRequest request);

    DetailUserResponseStaff getDetailUser(CommonUserRoleRequest request);

    List<RolesResponse> getListRoleCode(CommonUserRoleRequest request);

}
