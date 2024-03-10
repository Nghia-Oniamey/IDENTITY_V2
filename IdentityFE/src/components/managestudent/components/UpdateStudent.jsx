import React, {useEffect, useState} from "react";
import {Form, Input, Modal, Select} from 'antd';
import {faGraduationCap} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import sinhVienService from "../../../service/studentservice/StudentService";
import {toast} from "react-toastify";

const StudentUpdateModal = ({isOpenUpdate, loadTable, idSinhVien, onCancelUpdate}) => {
    const [form1] = Form.useForm();
    const [boMonTheoCoSo, setBoMonTheoCoSos] = useState([]);
    const [sinhVienCheck, setSinhVienChecks] = useState([]);
    const [checkDinhDangMail, setDinhDangMail] = useState(true);

    const getBoMonTheoCoSo = async () => {
        const data = await sinhVienService.getComboBox();
        setBoMonTheoCoSos(data.data);
    }

    const getSinhVienCheck = async () => {
        const data = await sinhVienService.getAllSinhVien();
        setSinhVienChecks(data.data.data);
    }

    const [sinhVien, setDetailSinhViens] = useState("");
    const update = async () => {
        if (idSinhVien === undefined || idSinhVien === "") {
            console.log("hehe")
            return;
        }
        const result = await sinhVienService.detailStudent(idSinhVien);
        setDetailSinhViens(result.data)
    }
    useEffect(() => {
        update();
    }, [idSinhVien])


    const onChange = (e) => {
        setDetailSinhViens({...sinhVien, [e.target.name]: e.target.value});
    };

    useEffect(() => {
        getBoMonTheoCoSo();
        getSinhVienCheck();
    }, []);

    console.log(sinhVien)
    //Update
    const suaSinhVien = async () => {
        console.log("heheh")
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
        const result = await sinhVienService.updateStudent(idSinhVien, sinhVien);
        if (result != null) {
            form1.resetFields();
            onCancelUpdate();
            loadTable();
            toast.success('Sửa thành công !', {
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
                        <FontAwesomeIcon icon={faGraduationCap}/>Sửa sinh viên
                    </h3>
                }
                style={{
                    paddingTop: 200
                }}
                centered
                open={isOpenUpdate}
                onCancel={onCancelUpdate}
                onOk={() => {
                    Modal.confirm({
                        centered: true,
                        title: 'Sửa sinh viên',
                        content: 'Bạn có muốn sửa sinh viên không ?',
                        okButtonProps: {style: {backgroundColor: "green"}},
                        onOk: () => {
                            form1.submit()
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
                okText="Update"
                cancelText="Cancel"
                okButtonProps={{style: {backgroundColor: "#052c65"}}}>
                <Form
                    className="mt-4"
                    layout='vertical'
                    form={form1}
                    onFinish={suaSinhVien}
                >
                    <div className='row mt-2'>
                        <div className='col-md-6'>
                            <Form.Item hasFeedback rules={[{required: true, message: 'Tên không được trống !'},]}>
                                <Input name="tenSinhVien" value={sinhVien?.tenSinhVien} onChange={onChange}></Input>
                            </Form.Item>
                        </div>
                        <div className='col-md-6'>
                            <Form.Item hasFeedback rules={[{required: true, message: 'Email không được trống !'},]}>
                                <Input name="mailSinhVien" value={sinhVien?.mailSinhVien} onChange={onChange}></Input>
                            </Form.Item>
                        </div>
                    </div>
                    <div className='row mt-2'>
                        <div className='col-md-6'>
                            <Form.Item hasFeedback rules={[{required: true, message: 'Please input quantity!'},]}>
                                <Input name="sdtSinhVien" value={sinhVien?.sdtSinhVien} onChange={onChange}></Input>
                            </Form.Item>
                        </div>
                        <div className='col-md-6'>
                            <Form.Item hasFeedback rules={[{required: true, message: 'Please choose subcategory!'},]}>
                                <Select placeholder='Chọn bộ môn theo cơ sở' value={sinhVien?.idBoMonTheoCoSo}
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
export default StudentUpdateModal;