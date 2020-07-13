package com.sfl.scma.mapper;

import com.sfl.scma.domain.User;
import com.sfl.scma.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);
}
