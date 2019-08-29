package by.haidash.shop.jpa.service;

import by.haidash.shop.core.controller.details.BaseDetails;
import by.haidash.shop.core.entity.BaseEntity;
import by.haidash.shop.core.service.EntityMapperService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;

public class JpaEntityMapperService implements EntityMapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaEntityMapperService.class);

    private final ModelMapper modelMapper;

    @Autowired
    public JpaEntityMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <T extends BaseEntity, P extends BaseDetails> T convertToEntity(P details, Class<T> returnType) {

        LOGGER.debug(format("Mapping details '%s' to entity '%s'.",
                details.getClass().getSimpleName(),
                returnType.getSimpleName()));

        return modelMapper.map(details, returnType);
    }

    @Override
    public <T extends BaseEntity, P extends BaseDetails> P convertToDetails(T entity, Class<P> returnType) {

        LOGGER.debug(format("Mapping entity '%s' to details '%s'.",
                entity.getClass().getSimpleName(),
                returnType.getSimpleName()));

        return modelMapper.map(entity, returnType);
    }
}
