import {Container} from "react-bootstrap";
import {useEffect} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {Button, Col, Form, Input, Row, Select} from "antd";
import {FaBackspace} from "react-icons/fa";
import {toast} from "react-toastify";
import Swal from "sweetalert2";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {useModifyStaffInformation} from "../hooks/useModifyStaffInformation";
import quanlynhanvienApi from "../../../service/staffservice/quanlynhanvien.api";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import {AddStaff, Campus, Department} from "../../../type/index.t";

const ModifyStaff = () => {
    const {id} = useParams();

    const [form] = Form.useForm<AddStaff>();

    const navigate = useNavigate();

    const {loading, setLoading} = useLoading();

    const {listCampus, listDepartment} = useModifyStaffInformation();

    const handleAddStaff = async () => {
        if (form.getFieldsError().some(({errors}) => errors.length)) {
            return;
        }

        const successMessage = id
            ? "Cập nhật nhân viên thành công"
            : "Thêm nhân viên thành công";
        const confirmMessage = id
            ? "Bạn có chắc chắn muốn cập nhật nhân viên này?"
            : "Bạn có chắc chắn muốn thêm nhân viên này?";

        const result = await Swal.fire({
            title: confirmMessage,
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
        });

        if (result.isConfirmed) {
            // @ts-ignore
            const nhanVien: AddStaff = {
                tenNhanVien: form.getFieldValue("tenNhanVien"),
                maNhanVien: form.getFieldValue("maNhanVien"),
                taiKhoanFPT: form.getFieldValue("taiKhoanFPT"),
                taiKhoanFE: form.getFieldValue("taiKhoanFE"),
                idBoMon: form.getFieldValue("tenBoMon"),
                idCoSo: form.getFieldValue("tenCoSo"),
                soDienThoai: form.getFieldValue("soDienThoai"),
            };
            setLoading(true);
            try {
                if (id) {
                    await quanlynhanvienApi.updateStaff(parseInt(id), nhanVien);
                } else {
                    await quanlynhanvienApi.addStaff(nhanVien);
                }
                setLoading(false);
                toast.success(successMessage);
                navigate("/admin/manage-staff");
            } catch (err) {
                console.log(err);
                setLoading(false);
            }
        }
    };

    const searchSelect = (input: string, option: any) => {
        return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    };

    useEffect(() => {
        const fetchDataDetailStaff = async (id: number) => {
            try {
                const response = await quanlynhanvienApi.getDetailStaff(id);
                if (response.data.status === "OK") {
                    const detailStaffData = response.data.data;
                    form.setFieldsValue({
                        tenNhanVien: detailStaffData.tenNhanVien,
                        maNhanVien: detailStaffData.maNhanVien,
                        idBoMon: detailStaffData.idBoMon,
                        idCoSo: detailStaffData.idCoSo,
                        taiKhoanFPT: detailStaffData.emailFpt,
                        taiKhoanFE: detailStaffData.emailFe,
                        soDienThoai: detailStaffData.soDienThoai,
                    });
                } else {
                    toast.error(response.data.message);
                }
            } catch (error) {
                console.log(error);
            }
        };
        if (id) fetchDataDetailStaff(parseInt(id));
    }, [id]);


    return (
        <>
            {loading && <GlobalLoading/>}
            <Link to={"/admin/manage-staff"} className={"mb-5"}>
                <FaBackspace
                    className={"mb-1"}
                    size={30}
                    style={{color: "#01693B"}}
                />
            </Link>
            <Container>
                <h2 className={"my-5"}>
                    {id ? "Cập Nhật Nhân Viên" : "Thêm Nhân Viên"}
                </h2>
                <div>
                    <Form
                        form={form}
                        layout="vertical"
                        initialValues={{
                            modifier: "public",
                        }}
                        onFinish={handleAddStaff}
                    >
                        <Row>
                            <Col span={"6"} className={"me-3"}>
                                <Form.Item
                                    name={"tenNhanVien"}
                                    label={"Họ Và Tên"}
                                    rules={[
                                        {
                                            required: true,
                                            message: "Vui lòng nhập họ và tên",
                                        },
                                    ]}
                                >
                                    <Input placeholder="Họ và tên"/>
                                </Form.Item>
                            </Col>
                            <Col span={'6'} className={"me-3"}>
                                <Form.Item
                                    name={"maNhanVien"}
                                    label={"Mã Nhân Viên"}
                                    rules={[
                                        {
                                            required: true,
                                            message: "Vui lòng nhập mã nhân viên",
                                        },
                                    ]}
                                >
                                    <Input placeholder="Mã nhân viên" disabled={!!id}/>
                                </Form.Item>
                            </Col>
                            <Col span={"6"}>
                                <Form.Item
                                    name={"soDienThoai"}
                                    label={"Số Điện Thoại"}
                                    rules={[
                                        {
                                            required: true,
                                            message: "Vui lòng nhập số điện thoại",
                                        },
                                    ]}
                                >
                                    <Input placeholder="Số điện thoại"/>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row>
                            <Col span={"8"} className={"me-5"}>
                                <Form.Item
                                    name={"taiKhoanFPT"}
                                    label={"Email FPT"}
                                    rules={[
                                        {
                                            required: true,
                                            message: "Vui lòng nhập email FPT",
                                        },
                                    ]}
                                >
                                    <Input placeholder="Email FPT"/>
                                </Form.Item>
                            </Col>
                            <Col span={"8"} className={"me-5"}>
                                <Form.Item
                                    name={"taiKhoanFE"}
                                    label={"Email FE"}
                                    rules={[
                                        {
                                            required: true,
                                            message: "Vui lòng nhập email FE",
                                        },
                                    ]}
                                >
                                    <Input placeholder="Email FE"/>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Row>
                            <Col span={"8"} className={"me-5"}>
                                <Form.Item
                                    name={"tenBoMon"}
                                    label={"Bộ Môn"}
                                    rules={[
                                        {
                                            required: true,
                                            message: "Vui lòng chọn bộ môn",
                                        },
                                    ]}
                                >
                                    <Select
                                        placeholder="Chọn bộ môn"
                                        showSearch
                                        optionFilterProp="children"
                                        filterOption={searchSelect}
                                        options={listDepartment.map((department: Department) => ({
                                            value: department.idBoMon,
                                            label: department.tenBoMon,
                                        }))}
                                    />
                                </Form.Item>
                            </Col>
                            <Col span={"8"}>
                                <Form.Item
                                    name={"tenCoSo"}
                                    label={"Cơ Sở"}
                                    rules={[
                                        {
                                            required: true,
                                            message: "Vui lòng chọn cơ sở",
                                        },
                                    ]}
                                >
                                    <Select
                                        placeholder="Chọn cơ sở"
                                        showSearch
                                        optionFilterProp="children"
                                        filterOption={searchSelect}
                                        options={listCampus.map((campus: Campus) => ({
                                            value: campus.idCoSo,
                                            label: campus.tenCoSo,
                                        }))}
                                    />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Form.Item className={"d-flex justify-content-end"}>
                            <Button
                                type="primary"
                                htmlType="submit"
                                style={{
                                    backgroundColor: "#01693B",
                                }}
                                className="login-form-button"
                            >
                                {id ? "Cập Nhật" : "Thêm"}
                            </Button>
                        </Form.Item>
                    </Form>
                </div>
            </Container>
        </>
    );
};

export default ModifyStaff;
