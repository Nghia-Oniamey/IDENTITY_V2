package fplhn.udpm.identity.core.viewer.controller;

import fplhn.udpm.identity.core.common.config.ResponseObject;
import fplhn.udpm.identity.core.viewer.repository.ModuleEntryRepository;
import fplhn.udpm.identity.core.viewer.response.ModuleEntryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entry-module")
@CrossOrigin("*")
@Slf4j
public class ModuleEntryController {

    private final ModuleEntryRepository moduleEntryRepository;

    @GetMapping("/all")
    public ResponseEntity<?> findAllModule() {
        List<ModuleEntryResponse> moduleEntryResponses = moduleEntryRepository.findAllModule();
        if (moduleEntryResponses.isEmpty()) {
            ResponseObject responseObject = new ResponseObject(null, HttpStatus.NO_CONTENT, "No content");
            return new ResponseEntity<>(responseObject, responseObject.getStatus());
        }
        ResponseObject responseObject = new ResponseObject(moduleEntryResponses, HttpStatus.OK, "Success");
        return new ResponseEntity<>(responseObject, responseObject.getStatus());
    }

}
