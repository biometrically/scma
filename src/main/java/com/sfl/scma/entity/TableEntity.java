package com.sfl.scma.entity;

import com.sfl.scma.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scma_table")
public class TableEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "waiter_id")
    private UserEntity waiter;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderEntity> orders;
}
