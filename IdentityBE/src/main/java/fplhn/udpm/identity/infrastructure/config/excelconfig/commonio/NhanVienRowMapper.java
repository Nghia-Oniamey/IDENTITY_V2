package fplhn.udpm.identity.infrastructure.config.excelconfig.commonio;

import fplhn.udpm.identity.infrastructure.config.excelconfig.model.request.ImportExcelRequest;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

public class NhanVienRowMapper implements RowMapper<ImportExcelRequest> {


    @Override
    public ImportExcelRequest mapRow(RowSet rowSet) {
        try {
            ImportExcelRequest importExcelRequest = new ImportExcelRequest();
            importExcelRequest.setIdGiaoVien(rowSet.getColumnValue(1));
            importExcelRequest.setHoTen(rowSet.getColumnValue(2));
            importExcelRequest.setChucVu(rowSet.getColumnValue(3));
            importExcelRequest.setEmailFe(rowSet.getColumnValue(4));
            importExcelRequest.setEmailFpt(rowSet.getColumnValue(5));
            importExcelRequest.setTenBoMonTheoCoSo(rowSet.getColumnValue(6));
            return importExcelRequest;
        } catch (Exception e) {
            return null;
        }
    }
}
