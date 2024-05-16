package com.ak.Employee.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VariableCode")
public class VariableCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorial_generator")
    private int id;

    @Column(name = "variableCode")
    private String variableCode;

    public VariableCode() {
    }

    public VariableCode(String vendorCode) {
        this.variableCode = vendorCode;
    }

    public String getVariableCode() {
        return variableCode;
    }

    public void setVariableCode(String variableCode) {
        this.variableCode = variableCode;
    }

    public int getId() {
        return id;
    }
}
