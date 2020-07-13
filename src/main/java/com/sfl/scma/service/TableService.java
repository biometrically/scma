package com.sfl.scma.service;

import com.sfl.scma.domain.Table;

import java.util.List;

public interface TableService {

    Table createTable();

    Table assignWaiter(Long tableId, Long waiterId);

    List<Table> getAllTables();

    List<Table> getAllTablesAssignedToWaiter(Long waiterId);
}
