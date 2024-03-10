export interface DecodedToken {
    fullName: string,
    userId: string,
    userCode: string,
    rolesName: string,
    host: string,
    rolesCode: string[],
    exp: number,
}

export interface UserInformation {
    fullName: string,
    userId: string,
    userCode: string,
    rolesName: string,
    hostId: string,
}

export interface StaffPagination {
    page: number,
    size: number,
    column: string,
    type: string,
}

export interface Staff {
    maNhanVien: string,
    tenNhanVien: string,
    taiKhoanFE: string,
    taiKhoanFPT: string,
    tenBoMon: string,
}

export interface AddStaff {
    maNhanVien: string,
    tenNhanVien: string,
    taiKhoanFE: string,
    taiKhoanFPT: string,
    idCoSo: number,
    idBoMon: number,
    soDienThoai: string,
    emailFpt: string,
    emailFe: string,
}

export interface Campus {
    idCoSo: number,
    tenCoSo: string,
}

export interface Department {
    idBoMon: number,
    tenBoMon: string,
}

export interface Module {
    id: number,
    name: string,
}

export interface Role {
    id: number,
    name: string,
}
