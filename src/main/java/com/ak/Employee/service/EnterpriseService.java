package com.ak.Employee.service;

import com.ak.Employee.model.Enterprise;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EnterpriseService {

    public void save(MultipartFile file);

    public void saveOriginal(MultipartFile file);

    public List<Enterprise> getAllEnterprisesByVariableCode(String variableCode);

    public List<Enterprise> getAllEnterprises();
}
