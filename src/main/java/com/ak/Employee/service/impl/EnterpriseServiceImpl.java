package com.ak.Employee.service.impl;

import com.ak.Employee.exception.CSVFileUploadFailedException;
import com.ak.Employee.helper.CsvUploadHelper;
import com.ak.Employee.model.Enterprise;
import com.ak.Employee.model.EnterpriseOriginal;
import com.ak.Employee.model.VariableCode;
import com.ak.Employee.repository.EnterpriseOriginalRepo;
import com.ak.Employee.repository.EnterpriseRepo;
import com.ak.Employee.repository.VariableCodeRepo;
import com.ak.Employee.service.EnterpriseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    EnterpriseRepo enterpriseRepo;

    @Autowired
    VariableCodeRepo variableCodeRepo;

    @Autowired
    EnterpriseOriginalRepo enterpriseOriginalRepo;

    @Override
    public void save(MultipartFile file) {
        try {
            Map<String, VariableCode> variableCodeMap = new HashMap<>();
            List<VariableCode> variableCodes = variableCodeRepo.findAll();
            for (VariableCode vc : variableCodes) {
                variableCodeMap.put(vc.getVariableCode(), vc);
            }

            List<Enterprise> enterpriseList = CsvUploadHelper.csvToEnterprise(file.getInputStream());

            for (Enterprise data : enterpriseList) {
                VariableCode variableCode = variableCodeMap.get(data.getVariable_code());
                data.setVariableCode(variableCode);
            }

            enterpriseRepo.saveAll(enterpriseList);
        } catch (IOException e) {
            throw new CSVFileUploadFailedException(e.getMessage(),e);
        }
    }

    @Override
    public void saveOriginal(MultipartFile file) {
        try {
            List<EnterpriseOriginal> enterpriseList = CsvUploadHelper.csvToEnterpriseOriginal(file.getInputStream());
            enterpriseOriginalRepo.saveAll(enterpriseList);
        } catch (IOException e) {
            throw new CSVFileUploadFailedException(e.getMessage(),e);
        }
    }

    @Override
    public List<Enterprise> getAllEnterprisesByVariableCode(String variableCode) {

        if(variableCodeRepo.countByVariableCode(variableCode) > 0){
            return enterpriseRepo.findByVariableCodeId(variableCodeRepo.findByVariableCode(variableCode).getId());
        }else {
            return null;
        }
    }

    @Override
    public List<Enterprise> getAllEnterprises() {
            return enterpriseRepo.findAll();
    }

}
