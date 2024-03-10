import axiosCustomize from "../base/request";
import {AddStaff, StaffPagination} from "../../type/index.t";

const PREFIX_URL_NHANVIEN = "/nhan-vien";

const PREFIX_URL_MODULE_ROLE = "/module-role-staff";

const PREFIX_URL_FILE_IO = "/file";

class QuanLyNhanVienServiceApi {
    async findStaffPagination(params: StaffPagination) {
        const data = {
            page: params.page,
            size: params.size,
            column: params.column,
            type: params.type,
        }
        try {
            return await axiosCustomize({
                url: `${PREFIX_URL_NHANVIEN}/danh-sach-nhan-vien`,
                method: "GET",
                params: data,
            });
        } catch (error) {
            console.error(error);
        }
    }

    findStaffPaginationByIdCampus = (idCoSo: number, params: StaffPagination) => {
        const data = {
            page: params.page,
            size: params.size,
            column: params.column,
            type: params.type,
        }
        return axiosCustomize({
            url: `${PREFIX_URL_NHANVIEN}/danh-sach-nhan-vien-theo-co-so/${idCoSo}`,
            method: "GET",
            params: data,
        });
    }

    findAllStaffSearch = () => {
        return axiosCustomize({
            url: `${PREFIX_URL_NHANVIEN}/danh-sach-nhan-vien-all`,
            method: "GET",
        });
    }

    addStaff = async (nhanVien: AddStaff) => {
        try {
            return await axiosCustomize({
                url: PREFIX_URL_NHANVIEN + "/them-nhan-vien",
                method: "POST",
                data: nhanVien,
            });
        } catch (e) {
            console.log(e);
        }
    };

    async updateStaff(idNhanVien: number, nhanVien: AddStaff) {
        try {
            return await axiosCustomize({
                url: `${PREFIX_URL_NHANVIEN}/cap-nhat-nhan-vien/${idNhanVien}`,
                method: "PUT",
                data: nhanVien,
            });
        } catch (error) {
            console.log(error);
        }
    }


    downloadTemplate = (idNhanVien: number) => {
        return axiosCustomize({
            url: `${PREFIX_URL_FILE_IO}/download-template/${idNhanVien}`,
            method: "GET",
            responseType: "blob",
        });
    };

    downloadTemplateByAdmin = () => {
        return axiosCustomize({
            url: `${PREFIX_URL_FILE_IO}/download-template`,
            method: "GET",
            responseType: "blob",
        });
    };

    uploadFile = (file: File) => {
        const formData = new FormData();
        formData.append("file", file);
        return axiosCustomize({
            url: `${PREFIX_URL_FILE_IO}/upload`,
            method: "POST",
            data: formData,
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });
    };

    fetchListDepartment = () => {
        return axiosCustomize({
            url: `${PREFIX_URL_NHANVIEN}/danh-sach-bo-mon`,
            method: "GET",
        });
    }

    fetchListCampus = () => {
        return axiosCustomize({
            url: `${PREFIX_URL_NHANVIEN}/danh-sach-co-so`,
            method: "GET",
        });
    }

    deleteStaff = (idNhanVien: number) => {
        return axiosCustomize({
            url: `${PREFIX_URL_NHANVIEN}/xoa-nhan-vien/${idNhanVien}`,
            method: "PUT",
        });
    }

    getDetailStaff = (idNhanVien: number) => {
        return axiosCustomize({
            url: `${PREFIX_URL_NHANVIEN}/chi-tiet-nhan-vien/${idNhanVien}`,
            method: "GET",
        });
    }

    fetchListRole = () => {
        return axiosCustomize({
            url: `${PREFIX_URL_MODULE_ROLE}/list-role`,
            method: "GET",
        });
    }

    fetchListModule = () => {
        return axiosCustomize({
            url: `${PREFIX_URL_MODULE_ROLE}/list-module`,
            method: "GET",
        });
    }

    fetchListRoleAndListModuleByIdStaff = (idNhanVien: number | undefined) => {
        return axiosCustomize({
            url: `${PREFIX_URL_MODULE_ROLE}/list-role-module-of-staff/${idNhanVien}`,
            method: "GET",
        });
    }

}

const instanceQuanLyNhanVienServiceApi = new QuanLyNhanVienServiceApi();

export default instanceQuanLyNhanVienServiceApi;
