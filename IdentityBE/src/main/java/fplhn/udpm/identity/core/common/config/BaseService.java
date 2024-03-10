package fplhn.udpm.identity.core.common.config;


public interface BaseService<T, K, R> {
    PageableObject<T> findAllEntity(R request);

    ResponseObject create(R request);

    ResponseObject update(R request);

    ResponseObject findById(K id);

    ResponseObject delete(K id);
}
