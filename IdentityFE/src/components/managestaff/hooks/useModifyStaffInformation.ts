import {useEffect, useState} from "react";
import quanlynhanvienApi from "../../../service/staffservice/quanlynhanvien.api";
import {Campus, Department} from "../../../type/index.t";

// id nhân viên đăng nhập
export const useModifyStaffInformation = (): {
    listDepartment: Department[];
    listCampus: Campus[];
} => {
    const [listDepartment, setListDepartment] = useState([]);
    const [listCampus, setListCampus] = useState([]);
    const fetchDepartmentData = async () => {
        try {
            const response = await quanlynhanvienApi.fetchListDepartment();
            if (response.data.status === "OK") {
                setListDepartment(response.data.data);
            }
        } catch (error) {
            console.log(error);
        }
    };

    const fetchCampusData = async () => {
        try {
            const response = await quanlynhanvienApi.fetchListCampus();
            if (response.data.status === "OK") {
                setListCampus(response.data.data);
            }
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchDepartmentData();
        fetchCampusData();
    }, []);

    return {
        listDepartment,
        listCampus,
    };
};
