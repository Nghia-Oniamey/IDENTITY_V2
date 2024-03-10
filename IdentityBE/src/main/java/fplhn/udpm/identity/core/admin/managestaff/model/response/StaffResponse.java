package fplhn.udpm.identity.core.admin.managestaff.model.response;

import fplhn.udpm.identity.core.common.config.IsIdentify;

public interface StaffResponse extends IsIdentify {

    String getMaNhanVien();

    String getTenNhanVien();

    String getTaiKhoanFE();

    String getTaiKhoanFPT();

    String getTenBoMon();

}
