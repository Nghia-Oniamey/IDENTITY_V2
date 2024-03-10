import IdentityTable from "../../common/IdentityTable";
import {Button} from "antd";
import {BsFillPencilFill} from "react-icons/bs";
import {BiSolidTrash} from "react-icons/bi";
import {MdSecurity} from "react-icons/md";
import {useNavigate} from "react-router-dom";
import PropTypes from "prop-types";
import {Staff} from "../../../type/index.t";

interface TableStaffProps {
    danhSachNhanVien: Staff[];
    paginationObj: any;
    setPaginationObj: (paginationObj: any) => void;
    totalPages: number | null;
    handleDelete: (id: string | number) => void;
    handleOpenModalModifyModuleRole: (record: any) => void;
}

interface ColumnTable {
    title: string | undefined;
    dataIndex?: string | undefined;
    key?: string | undefined;
    align?: "center" | "left" | "right" | undefined;
    sorter?: boolean | undefined;
    responsive?: string[] | undefined;
    render?: (text: any, record: any) => JSX.Element | undefined | null;
}

const TableStaff = ({
                        danhSachNhanVien,
                        paginationObj,
                        setPaginationObj,
                        totalPages,
                        handleDelete,
                        handleOpenModalModifyModuleRole,
                    }: TableStaffProps): JSX.Element => {
    const navigate = useNavigate();
    const handleTableChange = (pagination: any, filters: any, sorter: any) => {
        switch (sorter.order) {
            case "ascend":
                setPaginationObj({
                    ...paginationObj,
                    column: sorter.field,
                    order: "ASC",
                });
                break;
            case "descend":
                setPaginationObj({
                    ...paginationObj,
                    column: sorter.field,
                    order: "DESC",
                });
                break;
            default:
                setPaginationObj({
                    ...paginationObj,
                    column: "",
                    order: "",
                });
                break;
        }
    };

    const columnsTable: ColumnTable[] = [
        {
            title: "Mã nhân viên",
            dataIndex: "maNhanVien",
            key: "maNhanVien",
            align: "center",
            sorter: true,
            responsive: ["md", "lg"],
        },
        {
            title: "Họ tên",
            dataIndex: "tenNhanVien",
            key: "tenNhanVien",
            sorter: true,
            responsive: ["sm", "md", "lg"],
        },
        {
            title: "Email FE",
            dataIndex: "taiKhoanFE",
            key: "taiKhoanFE",
            responsive: ["md", "lg"],
        },
        {
            title: "Email FPT",
            dataIndex: "taiKhoanFPT",
            key: "taiKhoanFPT",
            responsive: ["md", "lg"],
        },
        {
            title: "Bộ Môn",
            dataIndex: "tenBoMon",
            key: "tenBoMon",
            responsive: ["md", "lg"],
        },
        {
            title: "Thao tác",
            align: "center",
            responsive: ["md", "lg"],
            render: (text: any, record: any) => {
                return (
                    <div className={"d-flex justify-content-center"}>
                        <Button
                            onClick={() => {
                                handleOpenModalModifyModuleRole(record.id);
                            }}
                            className={"me-1"}
                            style={{backgroundColor: "#FFA500"}}
                        >
                            <MdSecurity color={"white"}/>
                        </Button>
                        <Button
                            onClick={() => {
                                navigate(`update-staff/${record.id}`);
                            }}
                            className={"me-1"}
                            style={{backgroundColor: "#01693B"}}
                        >
                            <BsFillPencilFill color={"white"}/>
                        </Button>
                        <Button
                            onClick={() => {
                                handleDelete(record.id);
                            }}
                            style={{backgroundColor: "#000000"}}
                        >
                            <BiSolidTrash color={"white"}/>
                        </Button>
                    </div>
                );
            },
        },
    ];

    return (
        <div className={"container-fluid"}>
            <IdentityTable
                paginationParams={paginationObj}
                dataSource={danhSachNhanVien}
                columns={columnsTable}
                // @ts-ignore
                totalPages={totalPages as number | undefined | null}
                handleTableChange={handleTableChange}
                setPaginationParams={setPaginationObj}
                tableLayout={"fixed"}
                title={undefined}
            />
        </div>
    );
};

TableStaff.propTypes = {
    danhSachNhanVien: PropTypes.array,
    paginationObj: PropTypes.object,
    setPaginationObj: PropTypes.func,
    totalPages: PropTypes.number,
    handleViewDetail: PropTypes.func,
    handleDelete: PropTypes.func,
};

export default TableStaff;
