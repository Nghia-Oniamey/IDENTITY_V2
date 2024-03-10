import {faCircleInfo, faCirclePlus, faGraduationCap, faUser, faUserSecret} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import React, {useEffect, useState} from "react";
import {Container} from "react-bootstrap";
import SearchBar from "../../managestudent/components/SearchBar";
import TablePaginationCustom from "../../managestudent/components/TablePaginationCustom";
import sinhVienService from "../../../service/studentservice/StudentService";
import {toast} from "react-toastify";
import {Button, Flex, Popconfirm, Row, Tag, Tooltip} from "antd";
import {FaListAlt} from "react-icons/fa";
import StudentAddModal from "../components/AddStudent";
import StudentUpdateModal from "../components/UpdateStudent";


const ManageStudent = (props) => {
    const [data, setData] = useState();
    const [dataSearch, setDataSearch] = useState();
    const [arrayField, setArrayField] = useState([]);
    const [totalPages, setTotalPages] = useState();
    const [paginationObj, setPaginationObj] = useState({
        current: 1,
        pageSize: 5,
    });

    //Column table Sinh Viên
    const columnsSinhVien = [{
        title: 'STT',
        dataIndex: 'stt',
        align: "center",
        key: "idSinhVien",
        render: (text) => <span>{text}</span>,
        width: '5%',
    }, {
        title: 'Mã sinh viên',
        dataIndex: 'maSinhVien',
        key: "idSinhVien",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: '12%',
    }, {
        title: 'Tên sinh viên',
        dataIndex: 'tenSinhVien',
        key: "idSinhVien",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: '20%',
    }, {
        title: 'Email',
        dataIndex: 'mailSinhVien',
        key: "idSinhVien",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: '15%',
    },
        {
            title: 'Bộ Môn Theo Cơ Sở',
            dataIndex: 'tenBoMonTheoCoSo',
            key: "idSinhVien",
            sorter: true,
            render: (text) =>
                <Tag color={"green"}>
                    {text}
                </Tag>,
            width: '20%',
        }, {
            title: 'Trạng Thái',
            dataIndex: 'xoaMemSinhVien',
            key: "idSinhVien",
            sorter: true,
            render: (text) =>
                <Tag color={text === "DA_XOA" ? "orange" : "green"}>
                    {text === "DA_XOA" ? 'Ngừng hoạt động' : 'Hoạt động'}
                </Tag>,
            width: '10%',
        }, {
            title: 'Hành động',
            key: "idSinhVien",
            align: 'center',
            render: (row) => (<div className={"button-table-col"}>
                <Flex gap="small" wrap="wrap" className="justify-content-center">
                    <Tooltip title="Chi tiết sinh viên" color={"#052C65"}>
                        <Button icon={<FontAwesomeIcon icon={faCircleInfo}/>}
                                size={"large"}
                                type={"primary"}
                                onClick={() => {
                                    showModalUpdate(row.idSinhVien)
                                }}
                                style={{
                                    backgroundColor: "#052C65",
                                    color: "#ffff",
                                }}
                        >
                        </Button>
                    </Tooltip>
                    {/* <Tooltip title="Chuyển đổi trạng thái" color={"orange"}>
                    <Popconfirm
                        placement="top"
                        title={"Thông báo"}
                        description={"Bạn có muốn cập nhật trạng thái không ?"}
                        okText="Có"
                        cancelText="Không"
                        onConfirm={() => {
                            toast.info("Tính năng yêu cầu gửi mail")
                        }}
                    >
                        <Button size={"large"}
                            type={"primary"}
                            // icon={<FontAwesomeIcon icon={faRetweet} size="lg" style={{ color: "#ffff", }} />}
                            icon={row.xoaMemSinhVien === "DA_XOA" ? <FontAwesomeIcon icon={faUser} /> :
                                <FontAwesomeIcon icon={faUserGraduate} />}
                            style={{
                                backgroundColor: "orange",
                                color: "#ffff",
                            }}
                        >
                        </Button>
                    </Popconfirm>
                </Tooltip> */}

                    <Tooltip title="Chuyển đổi trạng thái" color={"orange"}>
                        <Popconfirm
                            placement="top"
                            title={"Thông báo"}
                            description={"Bạn có muốn cập nhật trạng thái không ?"}
                            okText="Có"
                            cancelText="Không"
                            onConfirm={() => {
                                toast.info("Tính năng yêu cầu gửi mail")
                            }}
                        >
                            <Button size={"large"}
                                    type={"primary"}
                                // icon={<FontAwesomeIcon icon={faRetweet} size="lg" style={{ color: "#ffff", }} />}
                                    icon={row.xoaMemSinhVien === "DELETED" ? <FontAwesomeIcon icon={faUserSecret}/> :
                                        <FontAwesomeIcon icon={faUser}/>}
                                    style={{
                                        backgroundColor: "orange",
                                        color: "#ffff",
                                    }}
                            >
                            </Button>
                        </Popconfirm>
                    </Tooltip>
                </Flex>
            </div>),
            center: "true",
            width: '30%',
        }
    ];

    //Tìm kiếm sinh viên
    const getDataSearch = () => {
        sinhVienService.getListSinhVien().then((res) => {
            if (res.status === 200) {
                console.log(res.data.data)
                setDataSearch(
                    res.data ? res.data.map(sinhVien => ({
                        value: sinhVien.idSinhVien,
                        label: sinhVien.tenSinhVien
                    })) : []
                );
            } else {
                toast.info('Thêm sinh viên vào để tìm kiếm!', {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
            }
        }).catch((err) => {
            toast.error('Không có sinh viên nào!', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        });
    }

    const fetchListSinhVien = (params) => {

        if (params.page === 0) {
            params.page = 1;
        }

        sinhVienService
            .getAllSinhVien(params)
            .then((res) => {
                if (res.status === 200) {
                    setData(res.data.data);
                    setTotalPages(res.data.totalPages);
                    if (res.data.currentPage + 1 > res.data.totalPages && res.data.totalPages >= 1) {
                        setPaginationObj({
                            ...paginationObj,
                            page: 1
                        });
                    }
                } else {
                    toast.info('DataTable lỗi!', {
                        position: "top-right",
                        autoClose: 5000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                        theme: "light",
                    });
                }
            })
            .catch((error) => {
                console.log(error);
                toast.error('DataTable lỗi!' + error, {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
            });
    };

    const searchSinhVien = (arrayField) => {

        const arrayFieldValue = arrayField.arrayField ? arrayField.arrayField.join(",") : "";

        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: arrayFieldValue || ""
        };

        fetchListSinhVien(params);
    };

    const getAllSinhVien = () => {
        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: ""
        };

        console.log(paginationObj.arrayField ? paginationObj.arrayField : "Không có ");

        fetchListSinhVien(params);
    };

    const handleChangeTimKiemSinhVien = (e) => {

        if (e === "" || e === undefined || e.length === 0) {
            setPaginationObj({
                page: 1,
                size: 5,
            });
        }

        setArrayField({
            arrayField: e,
        });

        console.log(e);
    }

    //Add Modal
    const [isModalAddOpen, setIsModalAddOpen] = useState(false);
    const showModalAdd = () => {
        setIsModalAddOpen(true);
    }
    const handleCancelAdd = () => {
        setIsModalAddOpen(false);
    };
    //Add Modal
    const [isModalUpdateOpen, setIsModalUpdateOpen] = useState(false);
    const [idSinhVien, setIdSinhViens] = useState("");
    const showModalUpdate = (id) => {
        setIdSinhViens(id);
        setIsModalUpdateOpen(true);
        console.log(id)
    }
    const handleCancelUpdate = () => {
        setIsModalUpdateOpen(false);
    };

    const title = () => (
        <Row className={"d-flex justify-content-between align-items-center text-primary-emphasis"}>
            <h3 className={"d-flex justify-content-between align-items-center gap-3"}>
                <FaListAlt/>
                Danh sách sinh viên
            </h3>
            <Tooltip title="Thêm sinh viên" color={"#052c65"}>
                <Button type="primary" defaultBg={"text-primary-emphasis"} icon={<FontAwesomeIcon icon={faCirclePlus}/>}
                        size={"large"}

                        style={{
                            backgroundColor: "#052c65",
                            color: "white",
                            fontWeight: "500",
                        }}
                        onClick={showModalAdd}
                >
                </Button>
            </Tooltip>
        </Row>
    )

    useEffect(() => {
        if (arrayField && arrayField !== "") {
            searchSinhVien(arrayField);
        } else {
            getAllSinhVien();
        }
        getDataSearch();
    }, [paginationObj, arrayField]);


    return (
        <>
            <h2 className={"text-primary-emphasis p-4 gap-3 d-flex align-items-center"}>
                <FontAwesomeIcon icon={faGraduationCap}/>
                Quản lý học sinh
            </h2>

            <Container fluid className={"shadow p-4 rounded-3"}>
                <SearchBar
                    dataSearch={dataSearch}
                    contentSearch={"học sinh"}
                    playholder={"Tìm kiếm học sinh"}
                    onChange={handleChangeTimKiemSinhVien}
                />
            </Container>

            <Container fluid className={"shadow p-4 rounded-3 mt-5 text-primary-emphasis"}>
                <TablePaginationCustom
                    title={title}
                    columns={columnsSinhVien}
                    data={data}
                    paginationObj={paginationObj}
                    totalPages={totalPages}
                    setPaginationObj={setPaginationObj}
                />
            </Container>

            <StudentAddModal
                isOpenAdd={isModalAddOpen}
                onCancelAdd={handleCancelAdd}
                loadTable={getAllSinhVien}
            >
            </StudentAddModal>

            <StudentUpdateModal
                idSinhVien={idSinhVien}
                isOpenUpdate={isModalUpdateOpen}
                onCancelUpdate={handleCancelUpdate}
                loadTable={getAllSinhVien}
            ></StudentUpdateModal>
        </>
    );
};
export default ManageStudent;