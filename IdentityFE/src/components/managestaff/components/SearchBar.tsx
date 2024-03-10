import {useEffect, useState} from "react";
import {Input} from "antd";

const SearchBar = ({onSearch}: { onSearch: (value: string) => void }) => {
    const [searchValue, setSearchValue] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchValue(e.target.value);
    };

    useEffect(() => {
        onSearch(searchValue);
    }, [searchValue]);

    return (
        <div className="search-bar p-2">
            <Input
                type="text"
                placeholder="Tìm kiếm nhân viên theo Mã nhân viên, Tên nhân viên, Email..."
                value={searchValue}
                onChange={handleChange}
            />
        </div>
    );
};

export default SearchBar;
