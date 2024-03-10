import {Container} from "react-bootstrap";
import {Link} from "react-router-dom";
import {Image} from "antd";
// @ts-ignore
import image from "../../assets/image/image.11cd6c19.svg";

export default function NotFound() {
    return (
        <Container
            className={"d-flex justify-content-center align-items-center p-5"}
        >
            <div>
                <h2>Có vẻ như có điều gì đó không đúng...</h2>
                <h5>
                    Trang bạn đang cố mở không tồn tại. Có thể bạn đã nhập sai địa chỉ,
                    hoặc trang đã được chuyển đến một URL khác. Nếu bạn nghĩ rằng đây là
                    một lỗi, hãy liên hệ với bộ phận hỗ trợ.
                </h5>
                <Link to={"/"} className={"btn btn-outline-info"}>
                    Quay về trang chủ
                </Link>
            </div>
            <div>
                <Image src={image} style={{
                    width: "400px",
                    objectFit: "contain"
                }}/>
            </div>
        </Container>
    );
}
