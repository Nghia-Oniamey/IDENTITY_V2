package fplhn.udpm.identity.core.connector.service.impl;

import fplhn.udpm.identity.core.connector.model.request.CommonUserRoleRequest;
import fplhn.udpm.identity.core.connector.model.request.GetListDetailUserRequest;
import fplhn.udpm.identity.core.connector.model.request.GetListUserFromRoleCodeRequest;
import fplhn.udpm.identity.core.connector.model.response.DetailUserResponseStaff;
import fplhn.udpm.identity.core.connector.model.response.RolesResponse;
import fplhn.udpm.identity.core.connector.repository.ClientConnectorRepository;
import fplhn.udpm.identity.core.connector.repository.StaffConnectorRepository;
import fplhn.udpm.identity.core.connector.service.ConnectorService;
import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.Staff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectorServiceImpl implements ConnectorService {

    private final ClientConnectorRepository clientConnectorRepository;

    private final StaffConnectorRepository staffConnectorRepository;

    @Override
    public List<DetailUserResponseStaff> getListDetailUser(GetListDetailUserRequest request) {
        try {
            String clientId = request.getClientId();
            String clientSecret = request.getClientSecret();
            List<String> userId = request.getUserCodes();
            List<Staff> listStaffValid = new ArrayList<>();

            Optional<Client> clientOptional = clientConnectorRepository.findByClientIdAndClientSecret(clientId, clientSecret);

            if (clientOptional.isEmpty()) {
                return null;
            }

            for (String userCode : userId) {
                Optional<Staff> staffOptional = staffConnectorRepository.findByStaffCode(userCode);
                staffOptional.ifPresent(listStaffValid::add);
            }

            return getResponseObject(listStaffValid);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DetailUserResponseStaff> getListUserFromRoleCode(GetListUserFromRoleCodeRequest request) {
        try {
            String clientId = request.getClientId();
            String clientSecret = request.getClientSecret();
            String roleCode = request.getRoleCode();
            Optional<Client> clientOptional = clientConnectorRepository.findByClientIdAndClientSecret(clientId, clientSecret);
            if (clientOptional.isEmpty()) {
                return null;
            }
            List<Staff> listStaff = staffConnectorRepository.findByRoleCode(roleCode);

            List<DetailUserResponseStaff> listDetailUserResponseStaff = new ArrayList<>();

            for (Staff staff : listStaff) {
                DetailUserResponseStaff detailUserResponseStaff = new DetailUserResponseStaff();
                detailUserResponseStaff.setId(staff.getStaffCode());
                detailUserResponseStaff.setName(staff.getFullName());
                detailUserResponseStaff.setUserName(staff.getStaffCode());
                detailUserResponseStaff.setEmail(staff.getAccountFPT());
//                detailUserResponseStaff.setEmailFE(staff.getAccountFE());
                detailUserResponseStaff.setPicture(staff.getAvatar());
                listDetailUserResponseStaff.add(detailUserResponseStaff);
            }

            return listDetailUserResponseStaff;
        } catch (Exception e) {
            return null;
        }
    }

    private List<DetailUserResponseStaff> getResponseObject(List<Staff> listStaff) {
        List<DetailUserResponseStaff> listDetailUserResponseStaff = new ArrayList<>();

        for (Staff staff : listStaff) {
            DetailUserResponseStaff detailUserResponseStaff = new DetailUserResponseStaff();
            detailUserResponseStaff.setId(staff.getStaffCode());
            detailUserResponseStaff.setName(staff.getFullName());
            detailUserResponseStaff.setUserName(staff.getStaffCode());
            detailUserResponseStaff.setEmail(staff.getAccountFPT());
//            detailUserResponseStaff.setEmailFE(staff.getAccountFE());
            detailUserResponseStaff.setPicture(staff.getAvatar());
            listDetailUserResponseStaff.add(detailUserResponseStaff);
        }

        return listDetailUserResponseStaff;
    }

    @Override
    public DetailUserResponseStaff getDetailUser(CommonUserRoleRequest request) {
        try {
            String clientId = request.getClientId();
            String clientSecret = request.getClientSecret();
            String userCode = request.getUserCode();
            Optional<Client> clientOptional = clientConnectorRepository.findByClientIdAndClientSecret(clientId, clientSecret);
            if (clientOptional.isEmpty()) {
                return null;
            }
            Optional<Staff> staffOptional = staffConnectorRepository.findByStaffCode(userCode);
            if (staffOptional.isEmpty()) {
                return null;
            }
            Staff staff = staffOptional.get();
            DetailUserResponseStaff detailUserResponseStaff = new DetailUserResponseStaff();
            detailUserResponseStaff.setId(staff.getStaffCode());
            detailUserResponseStaff.setName(staff.getFullName());
            detailUserResponseStaff.setUserName(staff.getStaffCode());
            detailUserResponseStaff.setEmail(staff.getAccountFPT());
//            detailUserResponseStaff.setEmailFE(staff.getAccountFE());
            detailUserResponseStaff.setPicture(staff.getAvatar());
            return detailUserResponseStaff;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RolesResponse> getListRoleCode(CommonUserRoleRequest request) {
        try {
            String clientId = request.getClientId();
            String clientSecret = request.getClientSecret();
            Optional<Client> clientOptional = clientConnectorRepository.findByClientIdAndClientSecret(clientId, clientSecret);
            if (clientOptional.isEmpty()) {
                return null;
            }
            List<Role> listRoleCode = staffConnectorRepository.findRoleCodeByStaffId(request.getUserCode());
            List<RolesResponse> listRoleCodeResponse = new ArrayList<>();
            for (Role role : listRoleCode) {
                RolesResponse rolesResponse = new RolesResponse();
                rolesResponse.setId(role.getId());
                rolesResponse.setRoleCode(role.getMa());
                rolesResponse.setRoleName(role.getTen());
                listRoleCodeResponse.add(rolesResponse);
            }
            return listRoleCodeResponse;
        } catch (Exception e) {
            return null;
        }
    }
}
