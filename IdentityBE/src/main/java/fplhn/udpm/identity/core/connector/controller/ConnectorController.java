package fplhn.udpm.identity.core.connector.controller;

import fplhn.udpm.identity.core.connector.model.request.CommonUserRoleRequest;
import fplhn.udpm.identity.core.connector.model.request.GetListDetailUserRequest;
import fplhn.udpm.identity.core.connector.model.request.GetListUserFromRoleCodeRequest;
import fplhn.udpm.identity.core.connector.service.ConnectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/connector")
@CrossOrigin("*")
@Slf4j
public class ConnectorController {

    private final ConnectorService connectorService;

    @PostMapping("/get-detail-users")
    public ResponseEntity<?> GetDetailUsers(@Valid @RequestBody GetListDetailUserRequest request) {
        log.info("GetListDetailUserRequest: {}", request);
        return new ResponseEntity<>(connectorService.getListDetailUser(request), HttpStatus.OK);
    }

    @PostMapping("/get-list-user-by-role-code")
    public ResponseEntity<?> GetListUserFromRoleCode(@Valid @RequestBody GetListUserFromRoleCodeRequest request) {
        return new ResponseEntity<>(connectorService.getListUserFromRoleCode(request), HttpStatus.OK);
    }

    @PostMapping("/get-user-by-id")
    public ResponseEntity<?> findUserById(@Valid @RequestBody CommonUserRoleRequest request) {
        return new ResponseEntity<>(connectorService.getDetailUser(request), HttpStatus.OK);
    }

    @PostMapping("/get-roles-of-user")
    public ResponseEntity<?> findRolesOfUserId(@Valid @RequestBody CommonUserRoleRequest request) {
        log.info("Request: {}", request);
        return new ResponseEntity<>(connectorService.getListRoleCode(request), HttpStatus.OK);
    }

}
