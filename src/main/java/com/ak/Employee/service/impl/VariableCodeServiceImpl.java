package com.ak.Employee.service.impl;

import com.ak.Employee.exception.CSVFileUploadFailedException;
import com.ak.Employee.helper.CsvUploadHelper;
import com.ak.Employee.model.VariableCode;
import com.ak.Employee.repository.VariableCodeRepo;
import com.ak.Employee.service.VariableCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
public class VariableCodeServiceImpl implements VariableCodeService {

    @Autowired
    VariableCodeRepo variableCodeRepo;

    public VariableCodeServiceImpl(VariableCodeRepo variableCodeRepo) {
    }

    @Override
    public void save(MultipartFile file) {
        try{
            Set<VariableCode> variableCodes = CsvUploadHelper.csvToVariableCodes(file.getInputStream());
            variableCodeRepo.saveAll(variableCodes);
        }catch(IOException e){
            throw new CSVFileUploadFailedException(e.getMessage(),e);
        }
    }
}
