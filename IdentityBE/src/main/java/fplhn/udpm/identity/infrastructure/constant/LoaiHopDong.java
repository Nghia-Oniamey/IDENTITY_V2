package fplhn.udpm.identity.infrastructure.constant;

import java.util.Arrays;
import java.util.List;

public enum LoaiHopDong {
    HOI_CONG_TAC, //("Hợp đồng hợp tác")
    TOAN_THOI_GIAN, //("Hợp đồng toàn thời gian"),
    BAN_THOI_GIAN, //("Hợp đồng bán thời gian"),
    THUC_TAP, //("Hợp đồng thực tập"),
    DAU_CONG_TAC; //("Hợp đồng đầu công tác")

    public static List<LoaiHopDong> getAll() {
        return Arrays.asList(LoaiHopDong.values());
    }

}
