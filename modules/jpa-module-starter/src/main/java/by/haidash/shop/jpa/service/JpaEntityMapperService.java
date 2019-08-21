package by.haidash.shop.jpa.service;

import by.haidash.shop.core.controller.details.BaseDetails;
import by.haidash.shop.core.entity.BaseEntity;
import by.haidash.shop.core.service.EntityMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaEntityMapperService implements EntityMapperService {

    private final ModelMapper modelMapper;

    @Autowired
    public JpaEntityMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <T extends BaseEntity, P extends BaseDetails> T convertToEntity(P details, Class<T> returnType) {
        return modelMapper.map(details, returnType);
    }

    @Override
    public <T extends BaseEntity, P extends BaseDetails> P convertToDetails(T entity, Class<P> returnType) {
        return modelMapper.map(entity, returnType);
    }
}
