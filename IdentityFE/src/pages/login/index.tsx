// @ts-ignore
import backgroundImage from "../../assets/image/background.jpg";
// @ts-ignore
import logo from "../../assets/image/Logo_FPT.png";
import {useEffect, useState} from "react";
import {getAllEntryModule} from "../../service/unsecure";
import {URL_BACKEND_OAUTH2} from "../../constant";
import {toast} from "react-toastify";
import {Button, Select} from "antd";
import {buttonStyle} from "./style";
import {FcGoogle} from "react-icons/fc";

interface IModule {
    name: string;
    uri: string;
}

const Landing = () => {

    const [urlRedirect, setUrlRedirect] = useState<string>("");

    const [module, setModule] = useState<IModule[]>([]);


    const handleLogin = () => {
        if (urlRedirect === "") {
            toast.error("Vui lòng chọn module");
            return;
        }
        window.location.href = URL_BACKEND_OAUTH2 + urlRedirect;
    }

    useEffect(() => {
        const getAllModule = async () => {
            try {
                const res = await getAllEntryModule();
                setModule(res.data.data);
            } catch (e) {
                console.log(e);
            }
        }
        getAllModule();
    }, []);

    return (
        <div
            className="vh-100"
            style={{
                backgroundColor: '#eee',
                backgroundImage: `url(${backgroundImage})`,
                backgroundSize: "cover",
            }}>
            <div className="container py-5 h-100">
                <div className="row d-flex justify-content-center align-items-center h-100">
                    <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                        <div className="card shadow-2-strong" style={{borderRadius: '1rem'}}>
                            <div className="card-body p-5 text-center">
                                <img src={logo} alt="Logo"
                                     style={{marginBottom: '20px'}}/>
                                <div className="form-outline mb-4">
                                    <Select
                                        style={{width: "100%"}}
                                        onChange={(value) => setUrlRedirect(value)}
                                        placeholder="Chọn Ứng Dụng Truy Cập"
                                    >
                                        {module.map((item, index) => (
                                            <Select.Option key={index}
                                                           value={item.uri}>{item.name}</Select.Option>
                                        ))}
                                    </Select>
                                    <Button
                                        style={buttonStyle}
                                        onClick={handleLogin}
                                        type={"primary"}
                                        size={"large"}
                                        className={'mt-5'}
                                    >
                                        <FcGoogle className={"me-2"}/>
                                        <span>Đăng nhập bằng Google</span>
                                    </Button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Landing;
