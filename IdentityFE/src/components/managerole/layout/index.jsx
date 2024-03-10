import React, {useEffect, useState} from "react";
import {Button, Flex, Popconfirm, Row, Tag, Tooltip} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faBuildingCircleArrowRight,
    faBuildingFlag,
    faBuildingLock,
    faCirclePlus,
    faCity,
} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import SearchBar from "../component/SearchBar";
import TablePaginationCustom from "../component/TablePaginationCustom";
import {toast} from "react-toastify";
import {FaListAlt} from "react-icons/fa";
import ModalUpdateRole from "../component/ModalUpdateRole";
import ModalAddRole from "../component/ModalAddRole";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import roleService from "../../../service/roleservice/RoleService";

const ManageRole = (props) => {
    const [data, setData] = useState();
    const [dataSearch, setDataSearch] = useState();
    const {loading, setLoading} = useLoading();
    const [arrayField, setArrayField] = useState([]);
    const [totalPages, setTotalPages] = useState();
    const [totalElements, setTotalElements] = useState();
    const [dataUpdate, setDataUpdate] = useState();

    const [idSortOrder, setIdSortOrder] = useState("descend");

    const [paginationObj, setPaginationObj] = useState({
        current: 1,
        pageSize: 5,
    });
    /**
     * columns Table Cơ Sở
     * @type {[{dataIndex: string, width: string, title: string, align: string, render: (function(*)), key: string},{sorter: boolean, dataIndex: string, width: string, title: string, render: (function(*)), key: string},{center: string, width: string, title: string, align: string, render: (function(*)), key: string}]}
     */
    const columnsRole = [
        {
            title: "STT",
            dataIndex: "id",
            align: "center",
            key: "id",
            sorter: true,
            defaultSortOrder: idSortOrder,
            render: (text, record, index) => {
                const isDescOrder = idSortOrder === "descend";
                const current = paginationObj.current;
                const pageSize = paginationObj.pageSize;

                const calculatedIndex = isDescOrder
                    ? totalElements - (current - 1) * pageSize - index
                    : (current - 1) * pageSize + index + 1;

                return calculatedIndex;
            },
            width: "5%",
        },
        {
            title: "Mã vai trò",
            dataIndex: "ma",
            key: "ma",
            sorter: true,
            render: (text) => <span>{text}</span>,
            width: "20%",
        },
        {
            title: "Tên vai trò",
            dataIndex: "ten",
            key: "ten",
            sorter: true,
            render: (text) => <span>{text}</span>,
            width: "20%",
        },
        {
            title: "Trạng thái",
            dataIndex: "status",
            key: "xoaMem",
            render: (text) => (
                <Tag color={text === "DELETED" ? "orange" : "green"}>
                    {text === "DELETED" ? "Ngừng hoạt động" : "Hoạt động"}
                </Tag>
            ),
            align: "center",
            width: "20%",
        },
        {
            title: "Hành động",
            key: "action",
            align: "center",
            render: (row) => (
                <div className={"button-table-col"}>
                    <Flex gap="small" wrap="wrap" className="justify-content-center">
                        <Tooltip title="Chi tiết vai trò" color={"#052C65"}>
                            <Button
                                icon={<FontAwesomeIcon icon={faBuildingCircleArrowRight}/>}
                                size={"large"}
                                type={"primary"}
                                style={{
                                    backgroundColor: "#052C65",
                                    color: "#ffff",
                                }}
                                onClick={() => {
                                    handleUpdateRole(row);
                                }}
                            ></Button>
                        </Tooltip>
                        <Tooltip title="Xoá vai trò" color={"orange"}>
                            <Popconfirm
                                placement="top"
                                title={"Thông báo"}
                                description={"Bạn có muốn xoá vai trò không ?"}
                                okText="Có"
                                cancelText="Không"
                                onConfirm={() => {
                                    handleOkDeleteRole(row.id);
                                }}
                            >
                                <Button
                                    size={"large"}
                                    type={"primary"}
                                    icon={
                                        row.xoaMem === "DELETED" ? (
                                            <FontAwesomeIcon icon={faBuildingFlag}/>
                                        ) : (
                                            <FontAwesomeIcon icon={faBuildingLock}/>
                                        )
                                    }
                                    style={{
                                        backgroundColor: "orange",
                                        color: "#ffff",
                                    }}
                                ></Button>
                            </Popconfirm>
                        </Tooltip>
                    </Flex>
                </div>
            ),
            center: "true",
            width: "30%",
        },
    ];

    /**
     * title Table
     * @returns
     */
    const title = () => (
        <Row
            className={
                "d-flex justify-content-between align-items-center text-primary-emphasis"
            }
        >
            <h3 className={"d-flex justify-content-between align-items-center gap-3"}>
                <FaListAlt/>
                Danh sách vai trò
            </h3>
            <Tooltip title="Thêm vai trò" color={"#052c65"}>
                <Button
                    type="primary"
                    defaultbg={"text-primary-emphasis"}
                    icon={<FontAwesomeIcon icon={faCirclePlus}/>}
                    size={"large"}
                    onClick={handleAddRole}
                    style={{
                        backgroundColor: "#052c65",
                        color: "white",
                        fontWeight: "500",
                    }}
                ></Button>
            </Tooltip>
        </Row>
    );

    /**
     * list vai trò
     */
    const getDataSearch = () => {
        roleService
            .getAllRole()
            .then((res) => {
                if (res.status === 200) {
                    setDataSearch(
                        res.data.content.data
                            ? res.data.content.data.map((role) => ({
                                value: role.id,
                                label: role.ten,
                            }))
                            : []
                    );
                } else {
                    toast.info("Thêm vai trò vào để tìm kiếm!", {
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
            .catch((err) => {
                toast.error("Không có vai trò nào!", {
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

    /**
     *
     * @param {
     *   Array and page - size
     * } params
     */
    const fetchListRole = (params) => {
        if (params.page === 0) {
            params.page = 1;
        }

        roleService
            .getAllRole(params)
            .then((res) => {
                if (res.status === 200) {
                    setData(res.data.content.data);
                    setTotalPages(res.data.content.totalPages);
                    setTotalElements(res.data.content.totalElements);
                    if (
                        res.data.content.currentPage + 1 > res.data.content.totalPages &&
                        res.data.content.totalPages >= 1
                    ) {
                        setPaginationObj({
                            ...paginationObj,
                            page: 1,
                        });
                    }
                    console.log(res);
                } else {
                    toast.info("dataTable lỗi!", {
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
                toast.error("dataTable lỗi!" + error, {
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

    const searchRole = (arrayField) => {
        const arrayFieldValue = arrayField.arrayField
            ? arrayField.arrayField.join(",")
            : "";

        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: arrayFieldValue || "",
        };

        fetchListRole(params);
    };

    const getAllRole = () => {
        const params = {
            page: paginationObj.current,
            size: paginationObj.pageSize,
            arrayField: "",
        };

        fetchListRole(params);
    };

    const handleChangeTimKiemRole = (e) => {
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
    };

    /**
     * modal add role
     */
    const [isModalAddRoleOpen, setIsModalAddRoleOpen] = useState(false);

    const handleAddRole = (row) => {
        setIsModalAddRoleOpen(true);
        setDataUpdate(row);
    };

    /**
     * modal update role
     */
    const [isModalUpdateRoleOpen, setIsModalUpdateRoleOpen] = useState(false);

    const handleUpdateRole = (row) => {
        setIsModalUpdateRoleOpen(true);
        setDataUpdate(row);
    };

    const handleOkDeleteRole = (id) => {
        roleService
            .deleteRole(id)
            .then((response) => {
                toast.success(response.data.content.message);
                const params = {
                    page: paginationObj.current,
                    size: paginationObj.pageSize,
                };

                fetchListRole(params);
            })
            .catch((error) => {
                toast.error("Xoá vai trò thất bại");
            });
    };

    useEffect(() => {
        if (arrayField && arrayField !== "") {
            searchRole(arrayField);
        } else {
            getAllRole();
        }

        getDataSearch();
    }, [paginationObj, arrayField, isModalUpdateRoleOpen, isModalAddRoleOpen]);

    const handleTableChange = (pagination, filters, sorter) => {
        let params = {};

        if (sorter.hasOwnProperty("column")) {
            if (sorter.field === "id") {
                setIdSortOrder(sorter.order);
            }

            params = {
                page: pagination.current,
                size: pagination.pageSize,
                sortBy: sorter.field,
                orderBy: sorter.order,
            };
        }

        fetchListRole(params);
    };

    return (
        <>
            <h2
                className={"text-primary-emphasis p-4 gap-3 d-flex align-items-center"}
            >
                {/* <FontAwesomeIcon icon={faBuildingCircleArrowRight}/>  */}
                <FontAwesomeIcon icon={faCity}/>
                Quản lý vai trò
            </h2>
            <Container fluid className={"shadow p-4 rounded-3"}>
                <SearchBar
                    dataSearch={dataSearch ?? []}
                    contentSearch={"vai trò"}
                    playholder={"Tìm kiếm theo tên vai trò"}
                    onChange={handleChangeTimKiemRole}
                />
            </Container>

            <Container
                fluid
                className={"shadow p-4 rounded-3 mt-5 text-primary-emphasis"}
            >
                <TablePaginationCustom
                    title={title}
                    columns={columnsRole}
                    data={data ?? []}
                    handleTableChange={handleTableChange}
                    paginationObj={paginationObj}
                    totalPages={totalPages}
                    setPaginationObj={setPaginationObj}
                />
            </Container>

            <ModalUpdateRole
                isModalUpdateRoleOpen={isModalUpdateRoleOpen}
                setIsModalUpdateRoleOpen={setIsModalUpdateRoleOpen}
                dataUpdate={dataUpdate}
                setDataUpdate={setDataUpdate}
            />

            <ModalAddRole
                isModalAddRoleOpen={isModalAddRoleOpen}
                setIsModalAddRoleOpen={setIsModalAddRoleOpen}
                dataUpdate={dataUpdate}
                setDataUpdate={setDataUpdate}
            />
        </>
    );
};

export default ManageRole;
