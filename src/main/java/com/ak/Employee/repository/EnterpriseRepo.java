package com.ak.Employee.repository;

import com.ak.Employee.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnterpriseRepo extends JpaRepository<Enterprise, Long> {
    List<Enterprise> findByVariableCodeId(int variableCode);
}
