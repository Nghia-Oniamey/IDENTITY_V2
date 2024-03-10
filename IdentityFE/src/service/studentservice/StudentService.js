import instance from "../base/request";

class SinhVienService {

    getAllSinhVien(params) {
        return instance({
            method: "GET",
            url: `/sinh-vien/get-all-sinh-vien`,
            params: params,
        });
    }

    getListSinhVien() {
        return instance({
            method: "GET",
            url: `/sinh-vien/get-list-sinh-vien`,
        });
    }

    getComboBox() {
        return instance({
            method: "GET",
            url: `/sinh-vien/get-list-combobox`,
        });
    }

    addStudent(data) {
        return instance({
            method: "POST",
            url: `/sinh-vien/add-sinh-vien`,
            data: data
        });
    }

    detailStudent(id) {
        return instance({
            method: "GET",
            url: `/sinh-vien/detail/${id}`,
        });
    }

    updateStudent(id, data) {
        return instance({
            method: "PUT",
            url: `/sinh-vien/update-sinh-vien/${id}`,
            data: data
        });
    }
}

const sinhVienService = new SinhVienService();
export default sinhVienService