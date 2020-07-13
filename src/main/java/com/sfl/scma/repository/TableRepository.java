package com.sfl.scma.repository;

import com.sfl.scma.entity.TableEntity;
import com.sfl.scma.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
    List<TableEntity> findByWaiter(UserEntity userEntity);
}
