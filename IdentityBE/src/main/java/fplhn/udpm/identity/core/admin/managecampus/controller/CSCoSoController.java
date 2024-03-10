package fplhn.udpm.identity.core.admin.managecampus.controller;

import fplhn.udpm.identity.core.admin.managecampus.model.request.CSCreateCoSoRequest;
import fplhn.udpm.identity.core.admin.managecampus.model.request.CSFindCoSoRequest;
import fplhn.udpm.identity.core.admin.managecampus.service.CSCoSoService;
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
@RequestMapping("/api/coso")
@CrossOrigin("*")
@Slf4j
public class CSCoSoController {

    @Autowired
    private CSCoSoService csCoSoService;

    /**
     * lấy data cơ sở
     *
     * @return
     */

    @GetMapping("/get-all-co-so")
    public ResponseEntity<?> get(CSFindCoSoRequest request) {
        return new ResponseEntity<>(csCoSoService.getAllCoSo(request), HttpStatus.OK);
    }

    /**
     * thêm cơ sở
     *
     * @param csCreateCoSoRequest
     * @return
     */

    @PostMapping("/add-co-so")
    public ResponseEntity<?> addCoSo(@Valid @RequestBody CSCreateCoSoRequest csCreateCoSoRequest) {
        return new ResponseEntity<>(csCoSoService.addCoSo(csCreateCoSoRequest), HttpStatus.OK);
    }

    /**
     * cập nhật cơ sở
     *
     * @param csCreateCoSoRequest
     * @param id
     * @return
     */

    @PutMapping("/update-co-so/{id}")
    public ResponseEntity<?> updateCoSo(@Valid @RequestBody CSCreateCoSoRequest csCreateCoSoRequest, @PathVariable Long id) {
        return new ResponseEntity<>(csCoSoService.updateCoSo(csCreateCoSoRequest, id), HttpStatus.OK);
    }

    /**
     * xóa cơ sở
     *
     * @param id
     * @return
     */

    @PutMapping("/update-xoa-mem-co-so/{id}")
    public ResponseEntity<?> deleteCoSo(@PathVariable Long id) {
        return new ResponseEntity<>(csCoSoService.updateXoaMemCoSo(id), HttpStatus.OK);
    }

    @GetMapping("/get-list-co-so")
    public ResponseEntity<?> getListCoSo() {
        return new ResponseEntity<>(csCoSoService.getListCoSo(), HttpStatus.OK);
    }
}
