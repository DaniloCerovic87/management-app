package com.hr.managementapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="department_id", nullable = false)
    private Long id;
    @Column(name="department_name")
    private String name;
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Employee> employees;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee teamLead;

}
