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
        setValue,
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
    }, [dataUpdate]);

    return (
        <>
            <Row gutter={16}>
                <Col flex={6}>
                    <Form
                        form={form}
                        onFieldsChange={(changedFields, allFields) =>
                            handleFieldsChange(changedFields, allFields)
                        }
                        layout="vertical"
                        initialValues={{
                            nameFormInput: dataUpdate?.tenCoSoCon,
                        }}
                    >
                        <Form.Item
                            name={nameFormInput}
                            rules={[
                                {
                                    validator: (_, value) => {
                                        if (value && value.trim().length <= 0) {
                                            return Promise.reject(
                                                new Error(
                                                    "Trường không được để trống hoặc khoảng trắng không"
                                                )
                                            );
                                        } else if (value && value.trim().length >= 255) {
                                            return Promise.reject(
                                                new Error("Trường không được quá 255 ký tự")
                                            );
                                        } else {
                                            return Promise.resolve();
                                        }
                                    },
                                },
                            ]}
                        >
                            <Input
                                // value={nameFormInput}
                                // name={nameFormInput}
                                size={sizeInput ? sizeInput : "large"}
                                placeholder={placeholderInput}
                            />
                        </Form.Item>
                        <Form.Item
                            name={nameFormInput}
                            rules={[
                                {
                                    validator: (_, value) => {
                                        if (value && value.trim().length <= 0) {
                                            return Promise.reject(
                                                new Error(
                                                    "Trường không được để trống hoặc khoảng trắng không"
                                                )
                                            );
                                        } else if (value && value.trim().length >= 255) {
                                            return Promise.reject(
                                                new Error("Trường không được quá 255 ký tự")
                                            );
                                        } else {
                                            return Promise.resolve();
                                        }
                                    },
                                },
                            ]}
                        >
                            <Input
                                // value={nameFormInput}
                                // name={nameFormInput}
                                size={sizeInput ? sizeInput : "large"}
                                placeholder={placeholderInput}
                            />
                        </Form.Item>
                        <Form.Item>
                            <Col>
                                {isButtonDisabled ? (
                                    <Tooltip title={titleTooltip} color={"#052C65"}>
                                        <Popconfirm
                                            placement="top"
                                            title={titlePopconfirm ? titlePopconfirm : "Thông báo"}
                                            description={contentPopconfirm}
                                            okText="Có"
                                            cancelText="Không"
                                            onConfirm={() => handleOk()}
                                        >
                                            <Button
                                                icon={
                                                    <FontAwesomeIcon icon={faPenToSquare} size="lg"/>
                                                }
                                                size={sizeButton ? sizeButton : "large"}
                                                type={typeButton ? typeButton : "primary"}
                                                style={{
                                                    backgroundColor: "#052C65",
                                                }}
                                            ></Button>
                                        </Popconfirm>
                                    </Tooltip>
                                ) : null}
                            </Col>
                        </Form.Item>
                    </Form>
                </Col>
            </Row>
        </>
    );
};

export default InputCustom;
