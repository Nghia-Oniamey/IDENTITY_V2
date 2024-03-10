import {useEffect, useState} from "react";
import {toast} from "react-toastify";
import Swal from "sweetalert2";
import quanlynhanvienApi from "../../../service/staffservice/quanlynhanvien.api";
import {useDebounce} from "../../common/hook";
import {Staff, StaffPagination} from "../../../type/index.t";

export const useNhanViens = () => {
    const [listStaffPagination, setListStaffPagination] = useState<Staff[]>([]);

    const [paginationObj, setPaginationObj] = useState<StaffPagination>({
        page: 1,
        size: 5,
        column: "",
        type: "",
    });

    const [allStaff, setAllStaff] = useState<Staff[]>([]);

    const [totalPages, setTotalPages] = useState<number | null>();

    const [searchValue, setSearchValue] = useState('');

    // @ts-ignore
    const [debouncedSearchValue] = useDebounce(searchValue, 500);

    const handleDelete = async (id: number) => {
        const {isConfirmed} = await Swal.fire({
            title: "Bạn có chắc chắn muốn xóa nhân viên này?",
            showDenyButton: true,
            confirmButtonText: `Xóa`,
            denyButtonText: `Không xóa`,
            confirmButtonColor: "#01693B",
            denyButtonColor: "#000000",
        });

        if (isConfirmed) {
            try {
                const response = await quanlynhanvienApi.deleteStaff(id);
                await getDanhSachNhanVien(
                    paginationObj.page,
                    paginationObj.size,
                    paginationObj.column,
                    paginationObj.type,
                );
                if (response.data.status === "OK") {
                    toast.success("Xóa nhân viên thành công");
                } else {
                    toast.error(response.data.message);
                }
            } catch (error: any) {
                toast.error(error.response?.data?.message);
            }
        }
    };

    const getDanhSachNhanVien = async (page: number, size: number, column: string, type: string) => {
        try {
            const params: StaffPagination = {
                page,
                size,
                column,
                type,
            };
            const response = await quanlynhanvienApi.findStaffPagination(params);
            const dataWithKeys = response?.data?.data?.map((item: any) => ({
                ...item,
                key: item.maNhanVien,
            }));
            setListStaffPagination(dataWithKeys || []);
            setTotalPages(response?.data?.totalPages || null);
        } catch (error) {
            toast.info("Không tìm thấy dữ liệu");
        }
    };

    const handleSearch = async (value: string) => {
        setSearchValue(value);
    };

    useEffect(() => {

        const search = async (value: string) => {
            try {
                if (value === "") {
                    await getDanhSachNhanVien(
                        paginationObj.page,
                        paginationObj.size,
                        paginationObj.column,
                        paginationObj.type,
                    );
                } else {
                    const filteredData = allStaff.filter((item) => {
                        return (
                            item.maNhanVien.toLowerCase().includes(value.toLowerCase()) ||
                            item.tenNhanVien.toLowerCase().includes(value.toLowerCase()) ||
                            item.taiKhoanFE.toLowerCase().includes(value.toLowerCase()) ||
                            item.taiKhoanFPT.toLowerCase().includes(value.toLowerCase())
                        );
                    });
                    const dataWithKeys = filteredData.map((item) => ({
                        ...item,
                        key: item.maNhanVien,
                    }));
                    setListStaffPagination(dataWithKeys);
                    setTotalPages(null);
                }
            } catch (error) {
                console.log(error);
            }
        };

        search(debouncedSearchValue);
    }, [debouncedSearchValue]);

    useEffect(() => {
        getDanhSachNhanVien(
            paginationObj.page,
            paginationObj.size,
            paginationObj.column,
            paginationObj.type,
        );
    }, [paginationObj]);

    useEffect(() => {
        const fetchAllStaff = async () => {
            try {
                const response = await quanlynhanvienApi.findAllStaffSearch();
                setAllStaff(response.data.data);
            } catch (error) {
                console.log(error);
            }
        };
        fetchAllStaff();
    }, []);

    return {
        danhSachNhanVien: listStaffPagination,
        setDanhSachNhanVien: setListStaffPagination,
        paginationObj,
        setPaginationObj,
        totalPages,
        setTotalPages,
        getDanhSachNhanVien,
        handleDelete,
        handleSearch,
    };
};
