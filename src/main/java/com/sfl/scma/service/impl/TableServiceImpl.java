package com.sfl.scma.service.impl;

import com.sfl.scma.domain.Table;
import com.sfl.scma.entity.TableEntity;
import com.sfl.scma.entity.UserEntity;
import com.sfl.scma.mapper.TableMapper;
import com.sfl.scma.repository.TableRepository;
import com.sfl.scma.repository.UserRepository;
import com.sfl.scma.service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final TableMapper tableMapper;

    @Override
    @Transactional
    public Table createTable() {
        TableEntity savedTableEntity = tableRepository.save(new TableEntity());
        return tableMapper.toDomain(savedTableEntity);
    }

    @Override
    @Transactional
    public Table assignWaiter(Long tableId, Long waiterId) {
        TableEntity tableEntity = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
        UserEntity userEntity = userRepository.findById(waiterId).orElseThrow(IllegalArgumentException::new);
        tableEntity.setWaiter(userEntity);
        TableEntity updatedTableEntity = tableRepository.save(tableEntity);
        return tableMapper.toDomain(updatedTableEntity);
    }

    @Override
    public List<Table> getAllTables() {
        return tableRepository
                .findAll()
                .stream()
                .map(tableMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Table> getAllTablesAssignedToWaiter(Long waiterId) {
        UserEntity userEntity = userRepository.findById(waiterId).orElseThrow(IllegalArgumentException::new);
        List<TableEntity> tables = tableRepository.findByWaiter(userEntity);
        return tableMapper.toDomain(tables);
    }
}