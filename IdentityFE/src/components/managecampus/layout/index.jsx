import React, {useEffect, useState} from "react";
import {Button, Flex, Popconfirm, Row, Tag, Tooltip} from 'antd'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faBuildingCircleArrowRight,
    faBuildingFlag,
    faBuildingLock,
    faCirclePlus,
    faCity
} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import SearchBar from "../component/SearchBar";
import TablePaginationCustom from "../component/TablePaginationCustom";
import {toast} from "react-toastify";
import {FaListAlt} from "react-icons/fa";
import ModalUpdateCoSo from "../component/ModalUpdateCoSo";
import ModalAddCampus from "../component/ModalAddCampus";
import coSoService from "../../../service/campusservice/CoSoService";

const ManageCampus = (props) => {
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
     * columns Table Cơ Sở
     * @type {[{dataIndex: string, width: string, title: string, align: string, render: (function(*)), key: string},{sorter: boolean, dataIndex: string, width: string, title: string, render: (function(*)), key: string},{center: string, width: string, title: string, align: string, render: (function(*)), key: string}]}
     */
    const columnsCampus = [{
        title: 'STT',
        dataIndex: 'stt',
        align: "center",
        key: "idCoSo",
        render: (text) => <span>{text}</span>,
        width: '5%',
    }, {
        title: 'Tên cơ sở',
        key: "idCoSo",
        sorter: true,
        render: (row) => <span>{row.tenCoSo + " - " + (row.maCoSo ? row.maCoSo : "Chưa xác định")}</span>,
        width: '20%',
    }, {
        title: 'Trạng thái',
        dataIndex: 'xoaMemCoSo',
        key: "idCoSo",
        render: (text) =>
            <Tag color={text === "DELETED" ? "orange" : "green"}>
                {text === "DELETED" ? 'Ngừng hoạt động' : 'Hoạt động'}
            </Tag>,
        align: "center",
        width: '20%',
    }, {
        title: 'Hành động', key: "idCoSo", align: 'center', render: (row) => (<div className={"button-table-col"}>
            <Flex gap="small" wrap="wrap" className="justify-content-center">
                <Tooltip title="Chi tiết cơ sở" color={"#052C65"}>
                    <Button icon={<FontAwesomeIcon icon={faBuildingCircleArrowRight}/>}
                            size={"large"}
                            type={"primary"}
                            style={{
                                backgroundColor: "#052C65",
                                color: "#ffff",
                            }}
                            onClick={() => {
                                handleUpdateCoSo(row);
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
                            // icon={<FontAwesomeIcon icon={faRetweet} size="lg" style={{ color: "#ffff", }} />}
                                icon={row.xoaMemCoSo === "DA_XOA" ? <FontAwesomeIcon icon={faBuildingFlag}/> :
                                    <FontAwesomeIcon icon={faBuildingLock}/>}
                                style={{
                                    backgroundColor: "orange",
                                    color: "#ffff",
                                }}
                        >
                        </Button>
                    </Popconfirm>
                </Tooltip>
            </Flex>
        </div>), center: "true", width: '30%',
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
                Danh sách cơ sở
            </h3>
            <Tooltip title="Thêm cơ sở" color={"#052c65"}>
                <Button type="primary" defaultBg={"text-primary-emphasis"} icon={<FontAwesomeIcon icon={faCirclePlus}/>}
                        size={"large"}
                        onClick={handleAddCoSo}
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
     * list Cơ Sở
     */
    const getDataSearch = () => {
        coSoService.getListCoSo().then((res) => {
            if (res.status === 200) {
                setDataSearch(
                    res.data ? res.data.map(coSo => ({
                        value: coSo.idCoSo,
                        label: coSo.tenCoSo + " - " + (coSo.maCoSo ? coSo.maCoSo : "Chưa xác định")
                    })) : []
                );
            } else {
                toast.info('Thêm cơ sở vào để tìm kiếm!', {
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
            toast.error('Không có cơ sở nào!', {
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

    /**
     *
     * @param {
     *   Array and page - size
     * } params
     */
    const fetchListCoSo = (params) => {

        if (params.page === 0) {
            params.page = 1;
        }

        coSoService
            .getAllCoSo(params)
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

    const searchCoSo = (arrayField) => {

        const arrayFieldValue = arrayField.arrayField ? arrayField.arrayField.join(",") : "";

        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: arrayFieldValue || ""
        };

        fetchListCoSo(params);
    };

    const getAllCoSo = () => {
        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: ""
        };

        console.log(paginationObj.arrayField ? paginationObj.arrayField : "Không có ");

        fetchListCoSo(params);
    };

    const handleChangeTimKiemCoSo = (e) => {

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

    /**
     * modal add coSo
     */
    const [isModalAddCoSoOpen, setIsModalAddCoSoOpen] = useState(false);

    const handleAddCoSo = (row) => {
        setIsModalAddCoSoOpen(true);
        setDataUpdate(row);
    };

    /**
     * modal update coSo
     */
    const [isModalUpdateCoSoOpen, setIsModalUpdateCoSoOpen] = useState(false);

    const handleUpdateCoSo = (row) => {
        setIsModalUpdateCoSoOpen(true);
        setDataUpdate(row);
    }

    useEffect(() => {
        if (arrayField && arrayField !== "") {
            searchCoSo(arrayField);
        } else {
            getAllCoSo();
        }

        getDataSearch();
    }, [paginationObj, arrayField, isModalUpdateCoSoOpen, isModalAddCoSoOpen]);

    return (
        <>
            <h2 className={"text-primary-emphasis p-4 gap-3 d-flex align-items-center"}>
                {/* <FontAwesomeIcon icon={faBuildingCircleArrowRight}/>  */}
                <FontAwesomeIcon icon={faCity}/>
                Quản lý cơ sở
            </h2>
            <Container fluid className={"shadow p-4 rounded-3"}>
                <SearchBar
                    dataSearch={dataSearch}
                    contentSearch={"cơ sở"}
                    playholder={"Tìm kiếm theo tên cơ sở"}
                    onChange={handleChangeTimKiemCoSo}
                />
            </Container>

            <Container fluid className={"shadow p-4 rounded-3 mt-5 text-primary-emphasis"}>
                <TablePaginationCustom
                    title={title}
                    columns={columnsCampus}
                    data={data}
                    // handleTableChange = {handleTableChange}
                    paginationObj={paginationObj}
                    totalPages={totalPages}
                    setPaginationObj={setPaginationObj}
                />
            </Container>

            <ModalUpdateCoSo
                isModalUpdateCoSoOpen={isModalUpdateCoSoOpen}
                setIsModalUpdateCoSoOpen={setIsModalUpdateCoSoOpen}
                dataUpdate={dataUpdate}
                setDataUpdate={setDataUpdate}
            />

            <ModalAddCampus
                isModalAddCoSoOpen={isModalAddCoSoOpen}
                setIsModalAddCoSoOpen={setIsModalAddCoSoOpen}
                dataUpdate={dataUpdate}
                setDataUpdate={setDataUpdate}
            />
        </>
    );
};

export default ManageCampus;