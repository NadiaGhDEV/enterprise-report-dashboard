package com.faniherfeei.demo1dashboard.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(schema = "dashboard")
@Data
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "department")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @JoinColumn(name = "department_name", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<String> columns;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<RowData> rows;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}