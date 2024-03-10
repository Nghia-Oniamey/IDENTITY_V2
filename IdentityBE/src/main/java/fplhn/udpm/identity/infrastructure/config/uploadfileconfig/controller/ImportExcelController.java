package fplhn.udpm.identity.infrastructure.config.uploadfileconfig.controller;

import fplhn.udpm.identity.infrastructure.config.excelconfig.jobconfig.NhanVienJobLauncher;
import fplhn.udpm.identity.infrastructure.config.excelconfig.service.ExcelFileService;
import fplhn.udpm.identity.infrastructure.config.uploadfileconfig.service.FileUploadService;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static fplhn.udpm.identity.util.ValidateFileExtensionExcel.getExtensionByStringHandling;


@RestController
@RequestMapping("/api/file")
@CrossOrigin("*")
public class ImportExcelController {

    private final FileUploadService storageService;

    private final NhanVienJobLauncher nhanVienJobLauncher;

    private final ExcelFileService excelFileService;


    public ImportExcelController(
            @Qualifier("excelService") FileUploadService storageService,
            NhanVienJobLauncher nhanVienJobLauncher,
            ExcelFileService excelFileService) {
        this.storageService = storageService;
        this.nhanVienJobLauncher = nhanVienJobLauncher;
        this.excelFileService = excelFileService;
    }

    /**
     * @param file MultipartFile
     * @return ResponseEntity<?> message
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            Optional<String> extension = getExtensionByStringHandling(file.getOriginalFilename());
            if (extension.isEmpty() || !extension.get().equals("xlsx")) {
                message = "File Không Đúng Định Dạng";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
            storageService.init();
            String fileName = storageService.save(file);
            message = "Tải File Excel Thành Công: " + file.getOriginalFilename();
            nhanVienJobLauncher.setFullPathFileName(fileName);
            nhanVienJobLauncher.enable();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Đã Xảy Ra Lỗi: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    /**
     * @param idNhanVien Long idNhanVien
     * @return MultiPartFile
     * @throws IOException IOException
     */
    @GetMapping(value = "/download-template/{idNhanVien}")
    public ResponseEntity<?> downloadTemplate(@PathVariable Long idNhanVien) throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template.xlsx");
        ByteArrayInputStream byteArrayInputStream = excelFileService.downloadTemplate(idNhanVien);
        return new ResponseEntity<>(IOUtils.toByteArray(byteArrayInputStream), header, HttpStatus.CREATED);
    }

    @GetMapping(value = "/download-template")
    public ResponseEntity<?> downloadTemplate() throws IOException {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template.xlsx");
        ByteArrayInputStream byteArrayInputStream = excelFileService.downloadTemplate();
        return new ResponseEntity<>(IOUtils.toByteArray(byteArrayInputStream), header, HttpStatus.CREATED);
    }


}
