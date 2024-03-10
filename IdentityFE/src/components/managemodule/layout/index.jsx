import React, {useEffect, useState} from "react";
import {Button, Flex, Popconfirm, Row, Tag, Tooltip} from 'antd'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowsRotate, faCirclePlus, faNetworkWired, faPencil, faRetweet} from "@fortawesome/free-solid-svg-icons";
import ModalAddModule from "../component/ModalAddModule";
import ModalUpdateModule from "../component/ModalUpdateModule";
import {Container} from "react-bootstrap";
import SearchBar from "../component/SearchBar";
import TablePaginationCustom from "../component/TablePaginationCustom";
import {toast} from "react-toastify";
import {FaListAlt} from "react-icons/fa";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import moduleService from "../../../service/moduleservice/ModuleService";


const ManageModule = () => {
    const [data, setData] = useState();
    const [dataSearch, setDataSearch] = useState();
    const {loading, setLoading} = useLoading();
    const [arrayField, setArrayField] = useState([]);
    const [totalPages, setTotalPages] = useState();
    const [dataUpdate, setDataUpdate] = useState();

    const [paginationObj, setPaginationObj] = useState({
        current: 1,
        pageSize: 5,
    });

    //columns Table module

    const columnsModule = [{
        title: 'STT',
        dataIndex: 'stt',
        align: "center",
        key: "stt",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: '5%',
    }, {
        title: 'Mã module',
        dataIndex: 'maModule',
        key: "maModule",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: '15%',
    }, {
        title: 'Tên module',
        dataIndex: 'tenModule',
        key: "tenModule",
        sorter: true,
        render: (text) => <span>{text}</span>,
        width: '15%',
    },
        {
            title: 'Địa chỉ mô-đun',
            dataIndex: 'urlModule',
            key: "urlModule",
            render: (text) => <span>{text}</span>,
            width: '20%',
        },
        {
            title: 'Trạng thái',
            dataIndex: 'xoaMemModule',
            key: "xoaMemModule",
            render: (text) =>
                <Tag color={text === "DELETED" ? "orange" : "green"}>
                    {text === "DELETED" ? 'Ngừng hoạt động' : 'Hoạt động'}
                </Tag>,
            align: "center",
            width: '20%',
        }, {
            title: 'Hành động', key: "idModule", align: 'center', render: (row) => (<div className={"button-table-col"}>
                <Flex gap="small" wrap="wrap" className="justify-content-center">
                    <Tooltip title="Chi tiết module" color={"#052C65"}>
                        <Button icon={<FontAwesomeIcon icon={faPencil}/>}
                                size={"large"}
                                type={"primary"}
                                style={{
                                    backgroundColor: "#052C65",
                                    color: "#ffff",
                                }}
                                onClick={() => {
                                    handleUpdateModule(row);
                                }}
                        >
                        </Button>
                    </Tooltip>
                    <Tooltip title="Cập nhật trạng thái" color={"orange"}>
                        <Popconfirm
                            placement="top"
                            title={"Thông báo"}
                            description={"Cập nhật trạng thái mô-đun không ?"}
                            okText="Có"
                            cancelText="Không"
                            onConfirm={() => {
                                handleDeleteModule(row.idModule);
                            }}
                        >
                            <Button size={"large"}
                                    type={"primary"}
                                // icon={<FontAwesomeIcon icon={faRetweet} size="lg" style={{ color: "#ffff", }} />}
                                    icon={row.xoaMemModule === "DELETED" ? <FontAwesomeIcon icon={faArrowsRotate}/> :
                                        <FontAwesomeIcon icon={faRetweet}/>}
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
                Danh sách mô-đun
            </h3>
            <Tooltip title="Thêm mô-đun" color={"#052c65"}>
                <Button type="primary" defaultBg={"text-primary-emphasis"} icon={<FontAwesomeIcon icon={faCirclePlus}/>}
                        size={"large"}
                        onClick={handleAddModule}
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
     * list module
     */
    const getDataSearch = () => {
        moduleService.getListModule().then((res) => {
            if (res.status === 200) {
                // console.log(res.data);
                setDataSearch(
                    res.data.map(module => ({
                        value: module.idModule,
                        label: module.tenModule
                    }))
                );
            } else {
                toast.info('Thêm mô-đun vào để tìm kiếm!', {
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
            toast.error('Không có mô-đun nào!', {
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

    const fetchListModule = (params) => {

        if (params.page === 0) {
            params.page = 1;
        }

        moduleService
            .getModuleByListId(params)
            .then((res) => {
                if (res.status === 200) {
                    // console.log(res.data);
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

    const searchModule = (arrayField) => {
        const arrayFieldValue = arrayField.listId ? arrayField.listId.join(",") : "";

        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            listId: arrayFieldValue || ""
        };
        fetchListModule(params);
    };

    const getAllModule = () => {
        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            listId: ""
        };

        console.log(paginationObj.arrayField ? paginationObj.arrayField : "Không có ");

        fetchListModule(params);
    };

    const handleChangeTimKiemModule = (e) => {

        if (e === "" || e === undefined || e.length === 0) {
            setPaginationObj({
                page: 1,
                size: 5,
            });
        }

        setArrayField({
            listId: e,
        });

        console.log(e);
    }

    /**
     * modal add module
     */
    const [isModalAddModuleOpen, setIsModalAddModuleOpen] = useState(false);

    const handleAddModule = (row) => {
        setIsModalAddModuleOpen(true);
        setDataUpdate(row);
    };

    /**
     * modal update module
     */
    const [isModalUpdateModuleOpen, setIsModalUpdateModuleOpen] = useState(false);

    const handleUpdateModule = (row) => {
        setIsModalUpdateModuleOpen(true);
        setDataUpdate(row);
    }
    // delete module 
    const handleDeleteModule = (id) => {
        moduleService.deleteModule(id).then((res) => {
            if (res.data.status === "OK") {
                toast.success(res.data.message, {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
                if (arrayField && arrayField !== "") {
                    searchModule(arrayField);
                } else {
                    getAllModule();
                }

                getDataSearch();
            }
        });
    }

    useEffect(() => {
        if (arrayField && arrayField !== "") {
            searchModule(arrayField);
        } else {
            getAllModule();
        }

        getDataSearch();
    }, [paginationObj, arrayField, isModalUpdateModuleOpen, isModalAddModuleOpen]);

    return (
        <>
            <h2 className={"text-primary-emphasis p-4 gap-3 d-flex align-items-center"}>
                {/* <FontAwesomeIcon icon={faBuildingCircleArrowRight}/>  */}
                <FontAwesomeIcon icon={faNetworkWired}/>
                Quản lý mô-đun
            </h2>
            <Container fluid className={"shadow p-4 rounded-3"}>
                <SearchBar
                    dataSearch={dataSearch}
                    contentSearch={"mô-đun"}
                    playholder={"Tìm kiếm theo tên mô-đun"}
                    onChange={handleChangeTimKiemModule}
                />
            </Container>

            <Container fluid className={"shadow p-4 rounded-3 mt-5 text-primary-emphasis"}>
                <TablePaginationCustom
                    title={title}
                    columns={columnsModule}
                    data={data}
                    paginationObj={paginationObj}
                    totalPages={totalPages}
                    setPaginationObj={setPaginationObj}
                />
            </Container>

            <ModalAddModule
                isModalAddModuleOpen={isModalAddModuleOpen}
                setIsModalAddModuleOpen={setIsModalAddModuleOpen}
            />

            <ModalUpdateModule
                isModalUpdateModuleOpen={isModalUpdateModuleOpen}
                setIsModalUpdateModuleOpen={setIsModalUpdateModuleOpen}
                dataUpdate={dataUpdate}
            />
        </>
    );
};

export default ManageModule;