package fplhn.udpm.identity.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fplhn.udpm.identity.core.admin.managestaff.model.request.AddStaffRequest;


public class JSONGenerator {

//    public static void main(String[] args) throws Exception {
//        AddStaffRequest request = new AddStaffRequest();
//        request.setMaNhanVien("NV001");
//        request.setTenNhanVien("Nguyen Van A");
//        request.setTenChucVu(List.of("CHU_NHIEM_BO_MON"));
//        request.setEmailFpt("nv001@fpt.edu.vn");
//        request.setEmailFe("nv001@fe.edu.vn");
//        request.setKyHocOnboard(String.valueOf(TenHocKy.FALL));
//        request.setNamOnboard(2023L);
//        request.setLoaiHopDong(String.valueOf(LoaiHopDong.TOAN_THOI_GIAN));
//        String json = generateJSON(request);
//        System.out.println(json);
//    }

    public static String generateJSON(AddStaffRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(request);
    }

}
