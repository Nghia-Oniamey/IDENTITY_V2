package fplhn.udpm.identity.infrastructure.config.excelconfig.service;

import java.io.ByteArrayInputStream;

public interface ExcelFileService {

    ByteArrayInputStream downloadTemplate(Long idNhanVien);

    ByteArrayInputStream downloadTemplate();

}
