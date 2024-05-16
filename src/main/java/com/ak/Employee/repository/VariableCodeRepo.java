package com.ak.Employee.repository;

import com.ak.Employee.model.VariableCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariableCodeRepo extends JpaRepository<VariableCode, Integer> {
    VariableCode findByVariableCode(String variableCode);
    long countByVariableCode(String variableCode);
   // boolean existsByVariableCode(String variableCode);
}
