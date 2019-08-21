package by.haidash.shop.core.service;

import by.haidash.shop.core.controller.details.BaseDetails;
import by.haidash.shop.core.entity.BaseEntity;

public interface EntityMapperService {

    <T extends BaseEntity, P extends BaseDetails> T convertToEntity(P details, Class<T> returnType);

    <T extends BaseEntity, P extends BaseDetails> P convertToDetails(T entity, Class<P> returnType);

}
