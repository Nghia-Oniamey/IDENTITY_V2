import {Row, Select} from "antd";
import {FaFilter} from "react-icons/fa";

const SearchBar = (props) => {

    const {
        dataSearch,
        contentSearch,
        playholder,
        onChange
    } = props;

    return (
        <>
            <Row flex={4} className={"text-primary-emphasis"}>
                <h3 className={"ps-3 pb-3 d-flex align-items-center gap-2"}>
                    <FaFilter/> Tìm kiếm {contentSearch}
                </h3>
                <Select
                    hasFeedback={true}
                    size={"large"}
                    mode="multiple"
                    showSearch
                    style={{
                        width: "100%",
                    }}
                    placeholder={playholder}
                    optionFilterProp="children"
                    filterOption={(input, option) => (option?.label ?? '').includes(input)}
                    filterSort={(optionA, optionB) =>
                        (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
                    }
                    options={dataSearch ? dataSearch : []}
                    onChange={onChange}
                />
            </Row>
        </>
    );
}

export default SearchBar;