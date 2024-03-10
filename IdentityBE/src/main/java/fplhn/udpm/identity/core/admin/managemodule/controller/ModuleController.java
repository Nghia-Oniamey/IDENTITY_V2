package fplhn.udpm.identity.core.admin.managemodule.controller;

import fplhn.udpm.identity.core.admin.managemodule.model.request.CreateModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.request.FindModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.request.UpdateModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.service.ModuleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/module")
@CrossOrigin("*")
@Slf4j
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/get-all-module")
    public ResponseEntity<?> getModuleByListId(FindModuleRequest request) {
        try {
            return new ResponseEntity<>(moduleService.getModuleByListId(request), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("ERROR");
        }
    }

    @GetMapping("/get-list-module")
    public ResponseEntity<?> getListModule() {
        try {
            return new ResponseEntity<>(moduleService.getListModule(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("ERROR");
        }
    }

    @PostMapping("/add-module")
    public ResponseEntity<?> addCoSo(@Valid @RequestBody CreateModuleRequest createModuleRequest) {
        return new ResponseEntity<>(moduleService.addModule(createModuleRequest), HttpStatus.OK);
    }

    @PutMapping("/update-module/{id}")
    public ResponseEntity<?> updateModule(@RequestBody UpdateModuleRequest request, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(moduleService.updateModule(request, id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("ERROR");
        }
    }

    @PutMapping("/update-xoa-mem-module/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable Long id) {
        return new ResponseEntity<>(moduleService.updateXoaMemModule(id), HttpStatus.OK);
    }
}
