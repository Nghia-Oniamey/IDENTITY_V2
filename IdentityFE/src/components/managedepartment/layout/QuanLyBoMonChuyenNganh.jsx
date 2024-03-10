import React, {useEffect, useState} from "react";
import {Button, Flex, Popconfirm, Row, Tag, Tooltip} from 'antd'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleInfo, faCirclePlus, faPenToSquare, faRetweet, faSwatchbook} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import {FaListAlt} from "react-icons/fa";
import {Link} from "react-router-dom";
import ModalAddBoMon from "../component/ModalAddBoMon";
import ModalUpdateBoMon from "../component/ModalUpdateBoMon";
import boMonChuyenNganhService from "../../../service/departmentservice/BoMonChuyenNganhService";
import SearchBar from "../../managecampus/component/SearchBar";
import IdentityTable from "../../common/IdentityTable";

const QuanLyBoMonChuyenNganh = (props) => {
    const [data, setData] = useState();
    const [dataSearch, setDataSearch] = useState();
    const [arrayField, setArrayField] = useState([]);
    const [totalPages, setTotalPages] = useState();
    const [dataUpdate, setDataUpdate] = useState();

    const [paginationObj, setPaginationObj] = useState({
        current: 1,
        pageSize: 5,
    });

    /**
     * columns Table Bộ môn
     * @type {[{dataIndex: string, width: string, title: string, align: string, render: (function(*)), key: string},{sorter: boolean, dataIndex: string, width: string, title: string, render: (function(*)), key: string},{center: string, width: string, title: string, align: string, render: (function(*)), key: string}]}
     */

    const columnsBoMon = [{
        title: 'STT',
        dataIndex: 'stt',
        align: "center",
        key: "idBoMon",
        render: (text) => <span>{text}</span>,
        width: '5%',
    }, {
        title: 'Tên Bộ môn',
        key: "idBoMon",
        sorter: true,
        render: (row) => <span>{row.tenBoMon + " - " + (row.maBoMon ? row.maBoMon : "Chưa xác định")}</span>,
        width: '20%',
    }, {
        title: 'Trạng thái',
        dataIndex: 'xoaMemBoMon',
        key: "idBoMon",
        render: (text) =>
            <Tag color={text == "DELETED" ? "red" : "green"}>
                {text == "DELETED" ? 'Ngừng hoạt động' : 'Hoạt động'}
            </Tag>,
        align: "center",
        width: '20%',
    }, {
        title: 'Hành động', key: "idBoMon", align: 'center', render: (row) => (

            <Flex gap="small" wrap="wrap" className="justify-content-center">
                <Tooltip title="Chi tiết bộ môn" color={"#052C65"}>
                    <Button icon={<FontAwesomeIcon icon={faPenToSquare} size="lg" style={{color: "#ffff",}}/>}
                            size={"large"}
                            type={"primary"}
                            style={{
                                backgroundColor: "#052C65",
                                color: "#ffff",
                            }}
                            onClick={() => {
                                handleUpdateBoMon(row);
                            }}
                    >
                    </Button>
                </Tooltip>
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
                                icon={<FontAwesomeIcon icon={faRetweet} size="lg" style={{color: "#ffff",}}/>}
                                style={{
                                    backgroundColor: "orange",
                                    color: "#ffff",
                                }}
                        >
                        </Button>
                    </Popconfirm>
                </Tooltip>
                <Link to={
                    `/admin/manage-department-campus/${row.idBoMon}`
                }>
                    <Tooltip title="Chi tiết bộ môn cơ sở" color={"#6c757d"}>
                        <Button size={"large"}
                                className="btn btn-secondary border-0 bg-secondary bg-gradient"
                                type={"primary"}
                                icon={<FontAwesomeIcon icon={faCircleInfo} size="lg" style={{color: "#ffff",}}/>}
                        >
                        </Button>
                    </Tooltip>
                </Link>
            </Flex>
        ), center: "true", width: '30%',
    }

    ];

    /**
     * title Table
     * @returns
     */
    const title = () => (
        <Row className={"d-flex justify-content-between align-items-center text-primary-emphasis"}>
            <h3 className={"d-flex justify-content-between align-items-center gap-3"}>
                <FaListAlt/>
                Danh sách bộ môn
            </h3>
            <Tooltip title="Thêm bộ môn" color={"#052c65"}>
                <Button type="primary" defaultBg={"text-primary-emphasis"} icon={<FontAwesomeIcon icon={faCirclePlus}/>}
                        size={"large"}
                        onClick={handleAddBoMon}
                        style={{
                            backgroundColor: "#052c65",
                            color: "white",
                            fontWeight: "500",
                        }}
                >
                </Button>
            </Tooltip>
        </Row>
    )

    /**
     * list bộ môn
     */
    const getDataSearch = () => {
        boMonChuyenNganhService.getListBoMon().then((res) => {
            if (res.status === 200) {
                setDataSearch(
                    res.data ? res.data.map(boMon => ({
                        value: boMon.idBoMon,
                        label: boMon.tenBoMon
                    })) : []
                );
            } else {
                toast.info('Thêm bộ môn vào để tìm kiếm!', {
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
            toast.error('Không có bộ môn nào!', {
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
        console.log("dataSearch");
        console.log(dataSearch);
    }

    /**
     *
     * @param {
     *   Array and page - size
     * } params
     */
    const fetchListBoMon = (params) => {

        if (params.page === 0) {
            params.page = 1;
        }

        boMonChuyenNganhService
            .getAllBoMon(params)
            .then((res) => {
                if (res.status === 200) {
                    console.log(res);
                    setData(res.data.data);
                    setTotalPages(res.data.totalPages);
                    if (res.data.currentPage + 1 > res.data.totalPages && res.data.totalPages >= 1) {
                        setPaginationObj({
                            ...paginationObj,
                            page: 1
                        });
                    }
                } else {
                    toast.info('dataTable lỗi!', {
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
                toast.error('dataTable lỗi!' + error, {
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

    const searchBoMon = (arrayField) => {

        const arrayFieldValue = arrayField.arrayField ? arrayField.arrayField.join(",") : "";

        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: arrayFieldValue || ""
        };

        fetchListBoMon(params);
    };

    const getAllBoMon = () => {
        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: ""
        };

        console.log(paginationObj.arrayField ? paginationObj.arrayField : "Không có ");

        fetchListBoMon(params);
    };

    const handleChangeTimKiemBoMon = (e) => {
        if (e === "" || e === undefined || e.length === 0) {
            setPaginationObj({
                page: 1,
                size: 5,
            });
        }
        setArrayField({
            arrayField: e,
        });
    };

    /**
     *  modal add
     */

    const [isModalAddBoMonOpen, setIsModalAddBoMonOpen] = useState(false);

    const handleAddBoMon = (row) => {
        setIsModalAddBoMonOpen(true);
        setDataUpdate(row);
    };

    /**
     * modal update coSo
     */
    const [isModalUpdateBoMonOpen, setIsModalUpdateBoMonOpen] = useState(false);

    const handleUpdateBoMon = (row) => {
        setIsModalUpdateBoMonOpen(true);
        setDataUpdate(row);
    }

    useEffect(() => {
        if (arrayField && arrayField !== "") {
            searchBoMon(arrayField);
        } else {
            getAllBoMon();
        }

        getDataSearch();
    }, [paginationObj, arrayField, isModalUpdateBoMonOpen, isModalAddBoMonOpen]);

    return (
        <>
            <h2 className={"text-primary-emphasis p-4 gap-3 d-flex"}>

                <FontAwesomeIcon icon={faSwatchbook}/>
                Quản lý bộ môn
            </h2>
            <Container fluid className={"shadow p-4 rounded-3"}>
                <SearchBar
                    dataSearch={dataSearch}
                    contentSearch={"bộ môn"}
                    playholder={"Tìm kiếm theo tên bộ môn"}
                    onChange={handleChangeTimKiemBoMon}
                />
            </Container>

            <Container fluid className={"shadow p-4 rounded-3 mt-5 text-primary-emphasis"}>
                <IdentityTable
                    title={title}
                    columns={columnsBoMon}
                    // handleTableChange = {handleTableChange}
                    totalPages={totalPages}
                    dataSource={data}
                    setPaginationParams={setPaginationObj}
                    paginationParams={paginationObj}
                />
            </Container>

            <ModalUpdateBoMon
                isModalUpdateBoMonOpen={isModalUpdateBoMonOpen}
                setIsModalUpdateBoMonOpen={setIsModalUpdateBoMonOpen}
                dataUpdate={dataUpdate}
                setDataUpdate={setDataUpdate}
            />

            <ModalAddBoMon
                isModalAddBoMonOpen={isModalAddBoMonOpen}
                setIsModalAddBoMonOpen={setIsModalAddBoMonOpen}
                dataUpdate={dataUpdate}
                setDataUpdate={setDataUpdate}
            />
        </>
    );
};

export default QuanLyBoMonChuyenNganh;