package fplhn.udpm.identity.infrastructure.config.excelconfig.service.impl;

import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.infrastructure.config.excelconfig.repository.CourseCampusStaffRepository;
import fplhn.udpm.identity.infrastructure.config.excelconfig.repository.MainCampusJobConfigRepository;
import fplhn.udpm.identity.infrastructure.config.excelconfig.service.ExcelFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExcelFileServiceImpl implements ExcelFileService {

    private final CourseCampusStaffRepository courseCampusStaffRepository;

    private final MainCampusJobConfigRepository mainCampusJobConfigRepository;

    @Autowired
    public ExcelFileServiceImpl(
            @Qualifier("CourseCampusStaffRepositoryExcel") CourseCampusStaffRepository courseCampusStaffRepository,
            MainCampusJobConfigRepository mainCampusJobConfigRepository
    ) {
        this.courseCampusStaffRepository = courseCampusStaffRepository;
        this.mainCampusJobConfigRepository = mainCampusJobConfigRepository;
    }

    /**
     * @param idNhanVien
     * @return
     */
    @Override
    public ByteArrayInputStream downloadTemplate(Long idNhanVien) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Optional<Campus> coSoOptional = mainCampusJobConfigRepository.findByIdNhanVien(idNhanVien);
            if (coSoOptional.isEmpty()) {
                return null;
            }
            Sheet sheet = createSheetWithHeader(workbook, "Template Thông Tin Giảng Viên", headerTitlesNotAdmin());
            addDataValidations(sheet, coSoOptional.get().getId());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            log.error("Error during export Excel file", ex);
            return null;
        }
    }

    @Override
    public ByteArrayInputStream downloadTemplate() {
        try (Workbook workbook = new XSSFWorkbook()) {
            List<Campus> listCampus = mainCampusJobConfigRepository.findAll();
            if (listCampus.isEmpty()) {
                return null;
            }
            Sheet sheet = createSheetWithHeader(workbook, "Template Thông Tin Giảng Viên", headerTitles());
            addDataValidations(sheet);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            log.error("Error during export Excel file", ex);
            return null;
        }
    }

    private void createRoleSheet(Workbook workbook, List<String> roles) {
        Sheet roleSheet = workbook.createSheet("Chức Vụ");
        CellStyle headerCellStyle = createHeaderCellStyle(workbook);
        createRowWithCell(roleSheet, 0, "Chức Vụ", headerCellStyle);
        for (int i = 0; i < roles.size(); i++) {
            createRowWithCell(roleSheet, i + 1, roles.get(i), null);
        }
        roleSheet.autoSizeColumn(0);
    }

    private void createRowWithCell(Sheet sheet, int rowIndex, String cellValue, CellStyle cellStyle) {
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(0);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        cell.setCellValue(cellValue);
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setLocked(true);
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerCellStyle;
    }

    private String[] headerTitles() {
        return new String[]{"STT", "ID Giảng Viên", "Họ và Tên", "Chức Vụ", "Email FE", "Email FPT", "Bộ Môn", "Cơ Sở"};
    }

    private String[] headerTitlesNotAdmin() {
        return new String[]{"STT", "ID Giảng Viên", "Họ và Tên", "Chức Vụ", "Email FE", "Email FPT", "Bộ Môn - Cơ Sở"};
    }

    private Sheet createSheetWithHeader(Workbook workbook, String sheetName, String[] headers) {
        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle headerCellStyle = createHeaderCellStyle(workbook);
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
            sheet.autoSizeColumn(i);
        }
        return sheet;
    }

    private void addDataValidations(Sheet sheet, Long coSoId) {
        List<String> validBoMon = courseCampusStaffRepository.findAllByCoSo(coSoId);
        addDataValidation(sheet, new CellRangeAddressList(1, 1000, 6, 6), validBoMon.toArray(new String[0]));
    }

    private void addDataValidations(Sheet sheet) {
        List<String> validDepartmentName = courseCampusStaffRepository.findAllDepartmentName();
        List<String> validCampusName = courseCampusStaffRepository.findAllCampusName();
        addDataValidation(sheet, new CellRangeAddressList(1, 1000, 6, 6), validDepartmentName.toArray(new String[0]));
        addDataValidation(sheet, new CellRangeAddressList(1, 1000, 7, 7), validCampusName.toArray(new String[0]));
    }

    private void addDataValidation(Sheet sheet, CellRangeAddressList addressList, String[] explicitListValues) {
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint;
        if (explicitListValues != null) {
            constraint = validationHelper.createExplicitListConstraint(explicitListValues);
        } else {
            constraint = validationHelper.createNumericConstraint(DataValidationConstraint.ValidationType.INTEGER, DataValidationConstraint.OperatorType.BETWEEN, "1950", "2023");
        }
        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setShowErrorBox(true);
        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.createErrorBox("Sai Dữ Liệu", "Hãy Chọn Dữ Liệu Cho Sẵn");
        dataValidation.createPromptBox("Chọn Dữ Liệu", "Chọn Dữ Liệu Cho Sẵn");
        sheet.addValidationData(dataValidation);
    }

    private static class DepartmentCampusConvert {

        private final String campusName;

        private final String departmentName;

        public DepartmentCampusConvert(String campusName, String departmentName) {
            this.campusName = campusName;
            this.departmentName = departmentName;
        }

        public String getDepartmentName() {
            return departmentName + " - " + campusName;
        }

    }

}

