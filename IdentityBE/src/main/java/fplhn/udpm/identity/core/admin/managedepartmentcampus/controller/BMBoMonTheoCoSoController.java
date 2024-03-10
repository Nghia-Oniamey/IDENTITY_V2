package fplhn.udpm.identity.core.admin.managedepartmentcampus.controller;

import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoDetailRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.service.BMBoMonTheoCoSoService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/bomon")
@CrossOrigin("*")
public class BMBoMonTheoCoSoController {

    @Autowired
    private BMBoMonTheoCoSoService bmBoMonTheoCoSoService;

    /**
     * tìm kiếm + phân trang
     *
     * @param id
     * @param request
     * @return post
     */
    @GetMapping("/get-all-bo-mon-theo-co-so/{id}")
    public ResponseEntity<?> getAllBoMon(@PathVariable Long id, BMBoMonTheoCoSoDetailRequest request) {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.getAllBoMonTheoCoSo(id, request), HttpStatus.OK);
    }

    @GetMapping("/get-ten-bo-mon/{id}")
    public ResponseEntity<?> getAllBoMon(@PathVariable Long id) {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.getTenBoMon(id), HttpStatus.OK);
    }

    @PostMapping("/add-bo-mon-theo-co-so")
    public ResponseEntity<?> addBoMonTheoCoSo(@Valid @RequestBody BMBoMonTheoCoSoRequest request) {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.addBoMonTheoCoSo(request), HttpStatus.OK);
    }

    @PutMapping("/update-bo-mon-theo-co-so/{id}")
    public ResponseEntity<?> updateBoMonTheoCoSo(@PathVariable Long id, @Valid @RequestBody BMBoMonTheoCoSoRequest request) {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.updateBoMonTheoCoSo(request, id), HttpStatus.OK);
    }

    @PutMapping("/delete-bo-mon-theo-co-so/{id}")
    public ResponseEntity<?> updateBoMonTheoCoSo(@PathVariable Long id) {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.deleteBoMonTheoCoSo(id), HttpStatus.OK);
    }

    @GetMapping("/get-list-co-so")
    public ResponseEntity<?> getListTenCoSo() {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.getListCoSo(), HttpStatus.OK);
    }

    @GetMapping("/get-list-chu-nhiem-bo-mon")
    public ResponseEntity<?> getListTenChuNhiemBoMon() {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.getListNhanVien(), HttpStatus.OK);
    }

    @GetMapping("/get-list-bo-mon-theo-co-so/{id}")
    public ResponseEntity<?> getListBoMonTheoCoSo(@PathVariable Long id) {
        return new ResponseEntity<>(bmBoMonTheoCoSoService.getListBoMonTheoCoSo(id), HttpStatus.OK);
    }
}
