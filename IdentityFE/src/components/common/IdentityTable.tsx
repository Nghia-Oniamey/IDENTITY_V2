import {Pagination, Table} from "antd";
import PropTypes from "prop-types";

const IdentityTable = ({
                           title,
                           columns,
                           dataSource,
                           handleTableChange,
                           paginationParams,
                           totalPages,
                           setPaginationParams,
                           tableLayout,
                       }: any
): JSX.Element => {
    return (
        <>
            <Table
                title={title ? title : null}
                columns={columns}
                dataSource={dataSource}
                onChange={handleTableChange}
                pagination={false}
                tableLayout={tableLayout}
            />
            <div className={"d-flex justify-content-end mt-3"}>
                <Pagination
                    current={paginationParams.page}
                    total={
                        isNaN(totalPages * paginationParams.size)
                            ? 0
                            : totalPages * paginationParams.size
                    }
                    showSizeChanger={!isNaN(totalPages * paginationParams.size)}
                    pageSizeOptions={
                        isNaN(totalPages * paginationParams.size)
                            ? []
                            : ["5", "10", "15", "20"]
                    }
                    defaultPageSize={paginationParams.size}
                    onChange={(page, pageSize) => {
                        setPaginationParams({
                            ...paginationParams,
                            page: page,
                            size: pageSize,
                        });
                    }}
                />
            </div>
        </>
    );
};

IdentityTable.propTypes = {
    columns: PropTypes.array.isRequired,
    dataSource: PropTypes.array.isRequired,
    paginationParams: PropTypes.object.isRequired,
    setPaginationParams: PropTypes.func.isRequired,
};

export default IdentityTable;
