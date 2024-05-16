package com.ak.Employee.service.impl;

import com.ak.Employee.helper.CsvUploadHelper;
import com.ak.Employee.model.Employee;
import com.ak.Employee.model.Enterprise;
import com.ak.Employee.model.EnterpriseOriginal;
import com.ak.Employee.model.VariableCode;
import com.ak.Employee.repository.EnterpriseOriginalRepo;
import com.ak.Employee.repository.EnterpriseRepo;
import com.ak.Employee.repository.VariableCodeRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mock;

class EnterpriseServiceImplTest {

    @Mock
    private EnterpriseOriginalRepo enterpriseOriginalRepo;

    @Mock
    private EnterpriseRepo enterpriseRepo;

    @Mock
    private VariableCodeRepo variableCodeRepo;

    @InjectMocks
    private EnterpriseServiceImpl enterpriseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks

        String csvContent = "Year,Industry_aggregation_NZSIOC,Industry_code_NZSIOC,Industry_name_NZSIOC,Units,Variable_code,Variable_name,Variable_category,Value,Industry_code_ANZSIC06\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H01,Total income,Financial performance,\"7,57,504\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H04,\"Sales, government funding, grants and subsidies\",Financial performance,\"6,74,890\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H05,\"Interest, dividends and donations\",Financial performance,\"49,593\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"";

        // Convert CSV content to a mock MultipartFile
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void save_ValidFile_SuccessfullySaved() throws IOException {
        // Prepare mock data
        String csvContent = "Year,Industry_aggregation_NZSIOC,Industry_code_NZSIOC,Industry_name_NZSIOC,Units,Variable_code,Variable_name,Variable_category,Value,Industry_code_ANZSIC06\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H01,Total income,Financial performance,\"7,57,504\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H04,\"Sales, government funding, grants and subsidies\",Financial performance,\"6,74,890\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"\n"
                + "2021,Level 1,99999,All industries,Dollars (millions),H05,\"Interest, dividends and donations\",Financial performance,\"49,593\",\"ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)\"";

        // Convert CSV content to a mock MultipartFile
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        // Call the method
        enterpriseService.save(file);

        // Verify
        verify(enterpriseRepo, times(1)).saveAll(any());
    }


    @Test
    void saveOriginal_ValidFile_SuccessfullySaved() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", inputStream);

        enterpriseService.saveOriginal(file);

        verify(enterpriseOriginalRepo, times(1)).saveAll(any());
    }

    @Test
    void getAllEnterprisesByVariableCode_ValidVariableCode_ReturnsEnterprises() {

        String variableCode = "H01";
        List<Enterprise> enterprises = new ArrayList<>();

        when(variableCodeRepo.countByVariableCode(variableCode)).thenReturn(1L);
        when(variableCodeRepo.findByVariableCode(variableCode)).thenReturn(new VariableCode());
        when(enterpriseRepo.findByVariableCodeId(any(Integer.class))).thenReturn(enterprises);

        // Call the method
        List<Enterprise> result = enterpriseService.getAllEnterprisesByVariableCode(variableCode);

        // Assert
        assertNotNull(result); // Ensure result is not null
    }

    @Test
    void getAllEnterprises_ReturnsAllEnterprises() {
        // Prepare mock data
        List<Enterprise> mockEnterprises = new ArrayList<>(); // assuming you have some mock enterprises

        // Mock behavior of enterpriseRepo
        when(enterpriseRepo.findAll()).thenReturn(mockEnterprises);

        // Call the method
        List<Enterprise> enterprises = enterpriseService.getAllEnterprises();

        // Assert
        assertNotNull(enterprises); // Ensure enterprises are not null
        // Add more assertions as needed
    }


}
