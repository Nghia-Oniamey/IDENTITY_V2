package fplhn.udpm.identity.core.admin.managestudent.controller;

import fplhn.udpm.identity.core.admin.managestudent.model.request.AddStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.request.FindStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.request.UpdateStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.repository.StudentsRepository;
import fplhn.udpm.identity.core.admin.managestudent.service.StudentService;
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
@RequestMapping("/api/sinh-vien")
@CrossOrigin("*")
@Slf4j
public class StudentController {
    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private StudentService studentService;

    @GetMapping("/get-all-sinh-vien")
    public ResponseEntity<?> get(FindStudentRequest request) {
        return new ResponseEntity<>(studentService.getAllSinhVien(request), HttpStatus.OK);
    }

    @GetMapping("/get-list-sinh-vien")
    public ResponseEntity<?> getListSinhVien() {
        return new ResponseEntity<>(studentsRepository.getListSinhVien(), HttpStatus.OK);
    }

    @GetMapping("/get-list-combobox")
    public ResponseEntity<?> getComboboxBoMonTheoCoSo() {
        return new ResponseEntity<>(studentsRepository.getComboboxBoMonTheoCoSo(), HttpStatus.OK);
    }

    @GetMapping("/detail/{idSV}")
    public ResponseEntity<?> getDetail(@PathVariable Long idSV) {
        return ResponseEntity.ok(studentService.detailSinhVien(idSV));
    }

    @PostMapping("/add-sinh-vien")
    public ResponseEntity<?> addSinhVien(@Valid @RequestBody AddStudentRequest request) {
        return new ResponseEntity<>(studentService.addSinhVien(request), HttpStatus.OK);
    }

    @PutMapping("/update-sinh-vien/{idSV}")
    public ResponseEntity updateProduct(@PathVariable Long idSV, @RequestBody UpdateStudentRequest request) {
        return ResponseEntity.ok(studentService.updateProduct(idSV, request));
    }
}
