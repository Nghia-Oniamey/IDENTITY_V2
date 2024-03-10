import {Form, Modal, Select} from "antd";
import {useModuleRole} from "../hooks/useModuleRole";

const ModalModifyModuleRole = ({
                                   show,
                                   handleClose,
                                   module,
                                   role,
                                   idStaff
                               }: {
    show: boolean,
    handleClose: () => void,
    module: any[] | undefined,
    role: any[] | undefined,
    idStaff: number | undefined
}) => {

    const {moduleData, roleData, form} = useModuleRole(idStaff);

    return (
        <Modal
            title="Phân quyền cho nhân viên"
            open={show}
            onOk={handleClose}
            onCancel={handleClose}
            okText="Lưu"
            cancelText="Hủy"
        >
            <Form
                name="modifyModuleRole"
                initialValues={{module, role}}
                layout={"vertical"}
                form={form}
            >
                <Form.Item
                    label="Chọn module"
                    name="module"
                    rules={[
                        {
                            required: true,
                            message: "Trường này không được để trống",
                        },
                    ]}
                >
                    <Select
                        mode="multiple"
                        allowClear
                        style={{width: '100%'}}
                        placeholder="Chọn Module"
                        options={moduleData ? moduleData.map((item) => ({
                            label: item.name,
                            value: item.id
                        })) : []}
                    />
                </Form.Item>
                <Form.Item
                    label="Chọn role"
                    name="role"
                    rules={[
                        {
                            required: true,
                            message: "Trường này không được để trống",
                        },
                    ]}
                >
                    <Select
                        mode="multiple"
                        allowClear
                        style={{width: '100%'}}
                        placeholder="Chọn Role"
                        options={roleData ? roleData.map((item) => ({
                            label: item.name,
                            value: item.id
                        })) : []}
                    />
                </Form.Item>
            </Form>
        </Modal>
    );
};

export default ModalModifyModuleRole;