import React, {useEffect, useState} from "react";
import {Form, Input, Modal, Select} from 'antd';
import {faGraduationCap} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import sinhVienService from "../../../service/studentservice/StudentService";
import {toast} from "react-toastify";

const StudentAddModal = ({isOpenAdd, loadTable, onCancelAdd}) => {
    const [form] = Form.useForm();
    const [boMonTheoCoSo, setBoMonTheoCoSos] = useState([]);
    const [sinhVienCheck, setSinhVienChecks] = useState([]);
    const [checkDinhDangMail, setDinhDangMail] = useState(true);
    const [sinhVien, setSinhViens] = useState({
        tenSinhVien: "",
        mailSinhVien: "",
        sdtSinhVien: "",
        idBoMonTheoCoSo: ""
    })

    const getBoMonTheoCoSo = async () => {
        const data = await sinhVienService.getComboBox();
        setBoMonTheoCoSos(data.data);
    }

    const getSinhVienCheck = async () => {
        const data = await sinhVienService.getAllSinhVien();
        setSinhVienChecks(data.data.data);
    }

    const onChange = (e) => {
        setSinhViens({...sinhVien, [e.target.name]: e.target.value});
    };

    useEffect(() => {
        getBoMonTheoCoSo();
        getSinhVienCheck();
    }, []);

    //Add sinh vien
    const addSinhVien = async () => {
        //Check định dạng mail
        const regex = /^[a-zA-Z0-9._%+-]+@fpt\.edu\.vn$/;
        if (regex.test(sinhVien.mailSinhVien) === false) {
            toast.error('Email không phải là mail FPT !', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        //Check định dạng sdt
        const regexSDT = /^(0[1-9]|84[1-9])(\d{8,9})$/;
        if (regexSDT.test(sinhVien.sdtSinhVien) === false) {
            toast.error('Không đúng định dạng số điện thoại !', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        //Check trùng mail
        const checkTrungMail = (code) => {
            return sinhVienCheck.some(svc => svc.mailSinhVien.trim().toLowerCase() === code.trim().toLowerCase());
        };
        if ((checkTrungMail(sinhVien.mailSinhVien))) {
            toast.error('Email đã được sử dụng !', {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            return;
        }
        const result = await sinhVienService.addStudent(sinhVien);
        if (result != null) {
            form.resetFields();
            onCancelAdd();
            loadTable();
            toast.success('Thêm thành công !', {
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

    }

    return (
        <>
            <Modal
                title={
                    <h3
                        className="d-flex gap-2 align-items-center"
                        style={{
                            color: "#052C65"
                        }}
                    >
                        <FontAwesomeIcon icon={faGraduationCap}/>Thêm sinh viên
                    </h3>
                }
                style={{
                    paddingTop: 200
                }}
                centered
                open={isOpenAdd}
                onCancel={onCancelAdd}
                onOk={() => {
                    Modal.confirm({
                        centered: true,
                        title: 'Thêm sinh viên',
                        content: 'Bạn có muốn thêm sinh viên không ?',
                        okButtonProps: {style: {backgroundColor: "green"}},
                        onOk: () => {
                            form.submit();
                        },
                        footer: (_, {OkBtn, CancelBtn}) => (
                            <>
                                <CancelBtn/>
                                <OkBtn/>
                            </>
                        ),
                    });
                }}
                width={700}
                height={700}
                okText="Add"
                cancelText="Cancel"
                okButtonProps={{style: {backgroundColor: "#052c65"}}}>
                <Form
                    className="mt-4"
                    layout='vertical'
                    form={form}
                    onFinish={addSinhVien}
                >
                    <div className='row mt-2'>
                        <div className='col-md-6'>
                            <Form.Item name="tenSinhVien" hasFeedback={true.toString()}
                                       rules={[{required: true, message: 'Tên không được trống !'},]}>
                                <Input name="tenSinhVien" placeholder="Nhập họ và tên" onChange={onChange}></Input>
                            </Form.Item>
                        </div>
                        <div className='col-md-6'>
                            <Form.Item name="mailSinhVien" hasFeedback={true.toString()}
                                       rules={[{required: true, message: 'Email không được trống !'},]}>
                                <Input name="mailSinhVien" placeholder="Nhập email" onChange={onChange}></Input>
                            </Form.Item>
                        </div>
                    </div>
                    <div className='row mt-2'>
                        <div className='col-md-6'>
                            <Form.Item name="sdtSinhVien" hasFeedback={true.toString()}
                                       rules={[{required: true, message: 'Please input quantity!'},]}>
                                <Input name="sdtSinhVien" placeholder="Nhập số điện thoại" onChange={onChange}></Input>
                            </Form.Item>
                        </div>
                        <div className='col-md-6'>
                            <Form.Item name="idBoMonTheoCoSo" hasFeedback={true.toString()}
                                       rules={[{required: true, message: 'Please choose subcategory!'},]}>
                                <Select name="idBoMonTheoCoSo" placeholder='Chọn bộ môn theo cơ sở'
                                        onChange={(value) => onChange({target: {name: "idBoMonTheoCoSo", value}})}>
                                    {boMonTheoCoSo.map(items => (
                                        <Select.Option key={items.idBoMonTheoCoSo}
                                                       value={items.idBoMonTheoCoSo}>{items.tenBoMonTheoCoSo}</Select.Option>
                                    ))}
                                </Select>
                            </Form.Item>
                        </div>
                    </div>
                </Form>
            </Modal>
        </>
    );
};
export default StudentAddModal;