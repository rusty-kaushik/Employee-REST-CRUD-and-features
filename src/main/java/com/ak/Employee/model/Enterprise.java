package com.ak.Employee.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "enterprise_details")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "year")
    private int Year;
    @Column(name = "industry_aggregation_NZSIOC")
    private String Industry_aggregation_NZSIOC;
    @Column(name = "industry_code_NZSIOC")
    private String Industry_code_NZSIOC;
    @Column(name = "industry_name_NZSIOC")
    private String Industry_name_NZSIOC;
    @Column(name = "units")
    private String Units;
    @Column(name = "variable_code")
    private String Variable_code;
    @Column(name = "variable_name")
    private String Variable_name;
    @Column(name = "variable_category")
    private String Variable_category;
    @Column(name = "value")
    private String Value;
    @Column(name = "industry_code_ANZSIC06")
    private String Industry_code_ANZSIC06;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variableCode_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VariableCode variableCode;


    public Enterprise() {
    }

    public Enterprise(int year, String industry_aggregation_NZSIOC, String industry_code_NZSIOC, String industry_name_NZSIOC,
                      String units, String variable_code, String variable_name, String variable_category,
                      String value, String industry_code_ANZSIC06) {
        Year = year;
        Industry_aggregation_NZSIOC = industry_aggregation_NZSIOC;
        Industry_code_NZSIOC = industry_code_NZSIOC;
        Industry_name_NZSIOC = industry_name_NZSIOC;
        Units = units;
        Variable_code = variable_code;
        Variable_name = variable_name;
        Variable_category = variable_category;
        Value = value;
        Industry_code_ANZSIC06 = industry_code_ANZSIC06;
    }

    public Enterprise(int year, String industry_aggregation_NZSIOC, String industry_code_NZSIOC, String industry_name_NZSIOC,
                      String units, VariableCode variableCode, String variable_code, String variable_name, String variable_category,
                      String value, String industry_code_ANZSIC06) {
        Year = year;
        Industry_aggregation_NZSIOC = industry_aggregation_NZSIOC;
        Industry_code_NZSIOC = industry_code_NZSIOC;
        Industry_name_NZSIOC = industry_name_NZSIOC;
        Units = units;
        this.variableCode = variableCode;
        Variable_code = variable_code;
        Variable_name = variable_name;
        Variable_category = variable_category;
        Value = value;
        Industry_code_ANZSIC06 = industry_code_ANZSIC06;
    }

    public Enterprise(VariableCode variableCode) {
        this.variableCode = variableCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Enterprise(String year, String industryAggregationNzsioc, String industryCodeNzsioc, String industryNameNzsioc, String units, String variableCode, String variableName, String variableCategory, String value, String industryCodeAnzsic06) {
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }


    public String getIndustry_aggregation_NZSIOC() {
        return Industry_aggregation_NZSIOC;
    }

    public void setIndustry_aggregation_NZSIOC(String industry_aggregation_NZSIOC) {
        Industry_aggregation_NZSIOC = industry_aggregation_NZSIOC;
    }

    public String getIndustry_code_NZSIOC() {
        return Industry_code_NZSIOC;
    }

    public void setIndustry_code_NZSIOC(String industry_code_NZSIOC) {
        Industry_code_NZSIOC = industry_code_NZSIOC;
    }

    public String getIndustry_name_NZSIOC() {
        return Industry_name_NZSIOC;
    }

    public void setIndustry_name_NZSIOC(String industry_name_NZSIOC) {
        Industry_name_NZSIOC = industry_name_NZSIOC;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public String getVariable_code() {
        return Variable_code;
    }

    public void setVariable_code(String variable_code) {
        Variable_code = variable_code;
    }

    public String getVariable_name() {
        return Variable_name;
    }

    public void setVariable_name(String variable_name) {
        Variable_name = variable_name;
    }

    public String getVariable_category() {
        return Variable_category;
    }

    public void setVariable_category(String variable_category) {
        Variable_category = variable_category;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getIndustry_code_ANZSIC06() {
        return Industry_code_ANZSIC06;
    }

    public void setIndustry_code_ANZSIC06(String industry_code_ANZSIC06) {
        Industry_code_ANZSIC06 = industry_code_ANZSIC06;
    }

    public VariableCode getVariableCode() {
        return variableCode;
    }

    public void setVariableCode(VariableCode variableCode) {
        this.variableCode = variableCode;
    }
}
