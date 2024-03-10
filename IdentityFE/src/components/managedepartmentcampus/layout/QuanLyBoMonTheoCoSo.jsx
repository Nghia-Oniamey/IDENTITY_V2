import React, {useEffect, useState} from "react";
import {Button, Flex, Popconfirm, Row, Tag, Tooltip} from 'antd'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCirclePlus, faPenToSquare, faRetweet, faSwatchbook} from "@fortawesome/free-solid-svg-icons";
import {FaListAlt} from "react-icons/fa";
import {useParams} from "react-router-dom";
import ModalAddMonHocTheoCoSo from "../component/ModalAddQuanLyBoMonTheoCoSo";
import ModalUpdateMonHocTheoCoSo from "../component/ModalUpdateQuanLyBoMonTheoCoSo";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import boMonTheoCoSoService from "../../../service/departmentcampusservice/BoMonTheoCoSoService";
import SearchBar from "../../managecampus/component/SearchBar";
import IdentityTable from "../../common/IdentityTable";

const QuanLyBoMonTheoCoSo = (props) => {

    const [loading, setLoading] = useState(false);
    const [data, setData] = useState();
    const [tenBoMon, setTenBoMon] = useState();
    const [arrayField, setArrayField] = useState([]);
    const [totalPages, setTotalPages] = useState();
    const [dataSearch, setDataSearch] = useState();
    const [paginationObj, setPaginationObj] = useState({
        current: 1,
        pageSize: 5,
    });

    const {idBoMon} = useParams();
    const [dataUpdate, setDataUpdate] = useState();

    const getTenBoMon = (id) => {
        boMonTheoCoSoService
            .getTenBoMon(id)
            .then((res) => {
                console.log("res.data", res.data);
                setTenBoMon({
                    tenBoMon: res?.data
                })
                console.log(res.data);
            }).catch((err) => {
            console.log(err);
        });
    }

    /**
     * title Table
     * @returns
     */
    const title = () => (
        <Row className={"d-flex justify-content-between align-items-center text-primary-emphasis"}>
            <h3 className={"d-flex justify-content-between align-items-center gap-3"}>
                <FaListAlt/>
                Danh sách bộ môn theo cơ sở
            </h3>
            <Tooltip title="Thêm bộ môn" color={"#052c65"}>
                <Button type="primary" defaultBg={"text-primary-emphasis"} icon={<FontAwesomeIcon icon={faCirclePlus}/>}
                        size={"large"}
                        onClick={handleModalAddBoMonTheoCoSo}
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

    const fetchListBoMonTheoCoSo = (id, params) => {
        setLoading(true);

        boMonTheoCoSoService
            .getAllBoMonTheoCoSo(id, params)
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

        getTenBoMon(id);

        // console.log("data", data);
    };

    const searchBoMonTheoCoSo = (id, arrayField) => {
        const arrayFieldValue = arrayField.arrayField ? arrayField.arrayField.join(",") : "";
        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: arrayFieldValue || ""
        };
        fetchListBoMonTheoCoSo(id, params);
    };

    const getAllBoMonTheoCoSo = (id) => {
        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: ""
        };
        console.log(paginationObj.arrayField ? paginationObj.arrayField : "Không có ");
        fetchListBoMonTheoCoSo(id, params);
    };

    const handleChangeStatusBoMonTheoCoSo = (row) => {
        const id = row.idBoMonTheoCoSo;

        boMonTheoCoSoService
            .deleteBoMonTheoCoSo(id)
            .then((response) => {
                if (dataSearch && dataSearch.gia !== "") {
                    searchBoMonTheoCoSo(idBoMon, dataSearch);
                } else {
                    getAllBoMonTheoCoSo(idBoMon);
                }
                toast.info(response.data.message);
            }).catch((error) => {
            toast.error("Xóa thất bại");
            console.log(error);
        })
    }

    const getDataSearch = (id) => {
        boMonTheoCoSoService.getListBoMonTheoCoSo(id).then((res) => {
            if (res.status === 200) {
                setDataSearch(
                    res.data ? res.data.map(coSo => ({
                        value: coSo.idCoSo,
                        label: coSo.maNhanVien ? (coSo.tenCoSo ? coSo.tenCoSo + " - " + coSo.maNhanVien : "chưa có") : (coSo.tenCoSo ? coSo.tenCoSo + " - " + "Chưa có" : "chưa có")
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
            console.log(err);
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
        console.log("dataSearch", dataSearch);
    }

    /** modal **/

    /** add */
    const [isModalCreateMonHocTheoCoSoOpen, setIsModalCreateMonHocTheoCoSoOpen] = useState(false);

    const handleModalAddBoMonTheoCoSo = () => {
        setIsModalCreateMonHocTheoCoSoOpen(true);
    }

    /** cập nhật */

    const [isModalUpdateMonHocTheoCoSoOpen, setIsModalUpdateMonHocTheoCoSoOpen] = useState(false);

    const handleUpdateBoMonTheoCoSo = (row) => {
        setDataUpdate(row);
        setIsModalUpdateMonHocTheoCoSoOpen(true);
    }

    /** detail chuyenNganh */

    useEffect(() => {
        if (arrayField && arrayField !== "") {
            searchBoMonTheoCoSo(idBoMon, arrayField);
        } else {
            getAllBoMonTheoCoSo(idBoMon);
        }

        getDataSearch(idBoMon);
    }, [paginationObj, arrayField, isModalUpdateMonHocTheoCoSoOpen, isModalCreateMonHocTheoCoSoOpen]);

    const columnsBoMon = [{
        title: 'STT',
        dataIndex: 'stt',
        align: "center",
        key: "idBoMonTheoCoSo",
        render: (text) => <span>{text}</span>,
        width: '5%',
    }, {
        title: 'Tên cơ sở',
        dataIndex: 'tenCoSo',
        key: "idBoMonTheoCoSo",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: '20%',
    }, {
        title: 'Tên chủ nhiệm bộ môn',
        dataIndex: 'tenCNBM',
        key: "idBoMonTheoCoSo",
        sorter: true,
        render: (text, record) => <span>{text ? text + " - " + record?.maNhanVien : "Chưa xác định"}</span>,
        width: '20%',
    }, {
        title: 'Trạng thái',
        dataIndex: 'xoaMemBoMonTheoCoSo',
        key: "idBoMonTheoCoSo",
        render: (text) =>
            <Tag color={text === "DELETED" ? "red" : "green"}>
                {text === "DELETED" ? 'Ngừng hoạt động' : 'Hoạt động'}
            </Tag>,
        align: "center",
        width: '10%',
    }, {
        title: 'Hành động', key: "idBoMonTheoCoSo", align: 'center', render: (row) => (
            <>

                <Flex gap="small" wrap="wrap" className="justify-content-center">
                    <Tooltip title="Cập nhật bộ môn theo cơ sở" color={"#052C65"}>
                        <Button icon={<FontAwesomeIcon icon={faPenToSquare} size="lg" style={{color: "#ffff",}}/>}
                                size={"large"}
                                type={"primary"}
                                style={{
                                    backgroundColor: "#052C65",
                                    color: "#ffff",
                                }}
                                onClick={() => handleUpdateBoMonTheoCoSo(row)}
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
                            onConfirm={() => handleChangeStatusBoMonTheoCoSo(row)}
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
                </Flex>

            </>
        ), center: "true", width: '30%',
    }

    ];

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

    return (
        <>
            <h2 className={"text-primary-emphasis p-4 gap-3 d-flex"}>
                <FontAwesomeIcon icon={faSwatchbook}/>
                Quản lý Bộ môn: <span style={{color: "red"}}>{tenBoMon?.tenBoMon}</span>
            </h2>

            <Container fluid className={"shadow p-4 rounded-3"}>
                <SearchBar
                    dataSearch={dataSearch}
                    contentSearch={"cơ sở - chủ nhiệm bộ môn"}
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
                    paginationParams={paginationObj}
                    setPaginationParams={setPaginationObj}
                />
            </Container>

            <ModalAddMonHocTheoCoSo
                isModalCreateMonHocTheoCoSoOpen={isModalCreateMonHocTheoCoSoOpen}
                setIsModalCreateMonHocTheoCoSoOpen={setIsModalCreateMonHocTheoCoSoOpen}
                idBoMon={idBoMon}
                tenBoMon={tenBoMon?.tenBoMon}
            />
            <ModalUpdateMonHocTheoCoSo
                isModalUpdateMonHocTheoCoSoOpen={isModalUpdateMonHocTheoCoSoOpen}
                setIsModalUpdateMonHocTheoCoSoOpen={setIsModalUpdateMonHocTheoCoSoOpen}
                idBoMon={idBoMon}
                tenBoMon={tenBoMon?.tenBoMon}
                dataUpdate={dataUpdate}
            />
        </>
    );

}

export default QuanLyBoMonTheoCoSo;