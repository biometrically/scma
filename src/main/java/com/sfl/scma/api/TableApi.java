package com.sfl.scma.api;

import com.sfl.scma.domain.Table;
import com.sfl.scma.security.jwt.domain.ScmaUserPrincipal;
import com.sfl.scma.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tables")
@RequiredArgsConstructor
public class TableApi {

    private final TableService tableService;

    /**
     * @return Created table with id.
     */
    @PostMapping
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_MANAGER)")
    public Table createTable() {
        return tableService.createTable();
    }

    /**
     * @param tableId which table.
     * @param waiterId whom to be assigned.
     * @return updated Table.
     */
    @PutMapping("/{tableId}/waiter/{waiterId}")
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_MANAGER)")
    public Table assignWaiterToTable(@PathVariable Long tableId, @PathVariable Long waiterId) {
        return tableService.assignWaiter(tableId, waiterId);
    }

    /**
     * @return All tables from DB.
     */
    @GetMapping
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_MANAGER)")
    public List<Table> getAllTables() {
        return tableService.getAllTables();
    }

    /**
     * @param user to get current user id.
     * @return All tables from DB for current waiter.
     */
    @GetMapping("/my")
    @PreAuthorize("hasRole(T(com.sfl.scma.enums.Role).ROLE_WAITER)")
    public List<Table> getAllTablesAssignedToWaiter(@AuthenticationPrincipal ScmaUserPrincipal user) {
        return tableService.getAllTablesAssignedToWaiter(user.getId());
    }
}