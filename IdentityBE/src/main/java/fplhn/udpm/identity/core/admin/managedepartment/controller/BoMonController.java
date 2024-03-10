package fplhn.udpm.identity.core.admin.managedepartment.controller;

import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMAddBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMFindBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMUpdateBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.service.BoMonService;
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
public class BoMonController {

    @Autowired
    private BoMonService boMonService;

    @GetMapping("/get-all-bo-mon")
    public ResponseEntity<?> getAllBoMon(BMFindBoMonRequest request) {
        return new ResponseEntity<>(boMonService.getAllBoMon(request), HttpStatus.OK);
    }

    @PostMapping("/add-bo-mon")
    public ResponseEntity<?> addCoSo(@Valid @RequestBody BMAddBoMonRequest request) {
        return new ResponseEntity<>(boMonService.addBoMon(request), HttpStatus.OK);
    }

    @PutMapping("/update-bo-mon/{id}")
    public ResponseEntity<?> updateCoSo(@Valid @RequestBody BMUpdateBoMonRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(boMonService.updateBoMon(request, id), HttpStatus.OK);
    }

    @PutMapping("/delete-bo-mon/{id}")
    public ResponseEntity<?> deleteCoSo(@PathVariable Long id) {
        return new ResponseEntity<>(boMonService.deleteBoMon(id), HttpStatus.OK);
    }

    @GetMapping("/get-list-bo-mon")
    public ResponseEntity<?> getListCoSoCon() {
        return new ResponseEntity<>(boMonService.getListBoMon(), HttpStatus.OK);
    }

}
