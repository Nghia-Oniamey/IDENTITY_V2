export interface Response<T> {
    data: T,
    message: string,
    status: string,
    isSuccess: boolean,
}