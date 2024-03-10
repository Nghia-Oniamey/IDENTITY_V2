import {Pagination, Table} from "antd";
import PropTypes from "prop-types";

const TablePaginationCustom = ({
                                   columns,
                                   data,
                                   handleTableChange,
                                   paginationObj,
                                   totalPages,
                                   setPaginationObj,
                                   title,
                               }) => {
    return (
        <>
            <Table
                scroll={
                    {
                        x: "100%",
                    }
                }
                title={title}
                columns={columns}
                dataSource={data}
                // onChange={handleTableChange}
                pagination={false}
            />
            <div className={"d-flex justify-content-end mt-3"}>
                <Pagination
                    current={paginationObj.current}
                    total={isNaN(totalPages * paginationObj.pageSize) ? 0 : totalPages * paginationObj.pageSize}
                    showSizeChanger={!isNaN(totalPages * paginationObj.pageSize)}
                    pageSizeOptions={isNaN(totalPages * paginationObj.pageSize) ? [] : ['5', '10', '15', '20']}
                    defaultPageSize={paginationObj.pageSize}
                    onChange={(page, pageSize) => {
                        setPaginationObj({
                            ...paginationObj,
                            current: page,
                            pageSize: pageSize,
                        });
                    }}
                />
            </div>
        </>
    );
};

TablePaginationCustom.propTypes = {
    columns: PropTypes.array.isRequired,
    data: PropTypes.array.isRequired,
    handleTableChange: PropTypes.func.isRequired,
    paginationObj: PropTypes.object.isRequired,
    setPaginationObj: PropTypes.func.isRequired,
};

export default TablePaginationCustom;
