package com.hr.managementapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    @Column(name = "personal_id")
    private String personalId;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public boolean isTeamLead() {
        return this.equals(this.getDepartment().getTeamLead());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
