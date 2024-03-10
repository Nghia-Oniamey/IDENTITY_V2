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
        codeFormInput,
        sizeButton,
        typeButton,
        dataUpdate,
        setValue
    } = props;

    const handleFieldsChange = (changedFields, allFields) => {
        const allFieldsFilled = Object.keys(allFields).every((field) => {

            const data = {
                name: allFields[0].value,
                code: allFields[1].value
            }

            if (data.name === undefined && data.name === "") {
                console.log("ret Name");
                return false;
            }

            if (data.code === undefined && data.code === "") {
                console.log("ret Code");
                return false;
            }

            const isWithinLengthLimitName = data.name ? data.name.length : data.name <= 255;
            const isWithinLengthLimitCode = data.code ? data.code.length : data.code <= 255;

            if (!!data.name && isWithinLengthLimitName && !!data.code && isWithinLengthLimitCode) {
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
                        onFieldsChange={(changedFields, allFields) =>
                            handleFieldsChange(changedFields, allFields)
                        }
                        initialValues={
                            {
                                nameFormInput: dataUpdate?.tenCoSo,
                                codeFormInput: dataUpdate?.maCoSo
                            }
                        }
                    >
                        <Form.Item
                            label="Tên: "
                            name={nameFormInput}
                            rules={[
                                {
                                    validator: (_, value) => {
                                        if (value && value.trim().length <= 0) {
                                            return Promise.reject(new Error("Trường không được để trống hoặc khoảng trắng không"));
                                        } else if (value && value.trim().length >= 255) {
                                            return Promise.reject(new Error("Trường không được quá 255 ký tự"));
                                        } else {
                                            return Promise.resolve();
                                        }
                                    },
                                },

                            ]}
                            required={true}
                        >
                            <Input
                                // value={nameFormInput}
                                // name={nameFormInput}
                                size={sizeInput ? sizeInput : "large"}
                                placeholder={placeholderInput}
                            />
                        </Form.Item>
                        <Form.Item
                            label="Mã: "
                            name={codeFormInput}
                            rules={[
                                {
                                    validator: (_, value) => {
                                        if (value && value.trim().length <= 0) {
                                            return Promise.reject(new Error("Trường không được để trống hoặc khoảng trắng không"));
                                        } else if (value && value.trim().length >= 255) {
                                            return Promise.reject(new Error("Trường không được quá 255 ký tự"));
                                        } else {
                                            return Promise.resolve();
                                        }
                                    },
                                },

                            ]}
                            required={true}
                        >
                            <Input
                                // value={nameFormInput}
                                // name={nameFormInput}
                                size={sizeInput ? sizeInput : "large"}
                                placeholder={"Nhập vào mã"}
                            />
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
            <div className="d-flex justify-content-end">
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
            </div>
        </>
    )
}

export default InputCustom;