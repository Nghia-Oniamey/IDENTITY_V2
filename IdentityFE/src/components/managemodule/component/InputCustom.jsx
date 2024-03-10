import {faPenToSquare} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Button, Col, Form, Input, Popconfirm, Row, Tooltip} from "antd";
import {useEffect} from "react";

const InputCustom = (props) => {

    const {
        form,
        isButtonDisabled,
        setIsButtonDisabled,
        handleOk,
        titleTooltip,
        titlePopconfirm,
        contentPopconfirm,
        placeholderInput,
        sizeInput,
        nameFormInput,
        sizeButton,
        typeButton,
        dataUpdate,
        setValue
    } = props;

    const handleFieldsChange = (changedFields, allFields) => {
        const allFieldsFilled = Object.keys(allFields).every((field) => {
            const value = allFields[field].value.trim();
            const isWithinLengthLimit = value.length <= 255;

            if (!!value && isWithinLengthLimit) {
                return true;
            } else {
                return false;
            }
        });
        setIsButtonDisabled(allFieldsFilled);
    };

    useEffect(() => {
        setValue ? setValue() : console.log(setValue ? setValue : "");
        ;
    }, [dataUpdate])

    return (
        <>
            <Row gutter={16}>
                <Col flex={6}>
                    <Form
                        layout={"vertical"}
                        form={form}
                        // initialValues={""}
                    >
                        <Form.Item
                            name='maModule'
                            label={"Mã"}
                            required={true}
                            rules={[
                                {
                                    validator: (_, value) => {
                                        const trimmedValue = value ? value.trim() : "";
                                        if (!trimmedValue) {
                                            return Promise.reject(new Error("Mã mô-đun không được để trống hoặc chỉ chứa khoảng trắng"));
                                        } else if (trimmedValue.length >= 255) {
                                            return Promise.reject(new Error("Mã mô-đun không được quá 255 ký tự"));
                                        } else {
                                            return Promise.resolve();
                                        }
                                    },
                                },
                            ]}
                        >
                            <Input
                                value='maModule'
                                name='maModule'
                                size={sizeInput ? sizeInput : "large"}
                                placeholder={"Nhập mã module"}
                            />
                        </Form.Item>
                        <Form.Item
                            name='tenModule'
                            label={"Tên"}
                            required={true}
                            rules={[
                                {
                                    validator: (_, value) => {
                                        const trimmedValue = value ? value.trim() : "";
                                        if (!trimmedValue) {
                                            return Promise.reject(new Error("Tên mô-đun không được để trống hoặc chỉ chứa khoảng trắng"));
                                        } else if (trimmedValue.length >= 255) {
                                            return Promise.reject(new Error("Tên mô-đun không được quá 255 ký tự"));
                                        } else {
                                            return Promise.resolve();
                                        }
                                    },
                                },
                            ]}
                        >
                            <Input
                                value='tenModule'
                                name='tenModule'
                                size={sizeInput ? sizeInput : "large"}
                                placeholder={placeholderInput}
                            />
                        </Form.Item>
                        <Form.Item
                            label={"Địa chỉ mô-đun"}
                            required={true}
                            name='urlModule'
                            rules={[
                                {
                                    validator: (_, value) => {
                                        const trimmedValue = value ? value.trim() : "";
                                        if (!trimmedValue) {
                                            return Promise.reject(new Error("Địa chỉ mô-đun không được để trống hoặc chỉ chứa khoảng trắng"));
                                        } else if (trimmedValue.length >= 255) {
                                            return Promise.reject(new Error("Địa chỉ mô-đun không được quá 255 ký tự"));
                                        } else {
                                            return Promise.resolve();
                                        }
                                    },
                                },
                            ]}
                        >

                            <Input
                                value='urlModule'
                                name='urlModule'
                                size={sizeInput ? sizeInput : "large"}
                                placeholder={"Nhập địa chỉ mô-đun"}
                            />
                        </Form.Item>
                    </Form>
                </Col>
                <Col>
                    {isButtonDisabled ?
                        <Tooltip title={titleTooltip} color={"#052C65"}>
                            <Popconfirm
                                placement="top"
                                title={titlePopconfirm ? titlePopconfirm : "Thông báo"}
                                description={contentPopconfirm}
                                okText="Có"
                                cancelText="Không"
                                onConfirm={() => handleOk()}
                            >
                                <Button icon={<FontAwesomeIcon icon={faPenToSquare} size="lg"/>}
                                        size={sizeButton ? sizeButton : "large"}
                                        type={typeButton ? typeButton : "primary"}
                                        style={{
                                            backgroundColor: "#052C65",
                                        }}
                                >
                                </Button>
                            </Popconfirm>
                        </Tooltip>
                        :
                        null
                    }
                </Col>
            </Row>
        </>
    )
}

export default InputCustom;