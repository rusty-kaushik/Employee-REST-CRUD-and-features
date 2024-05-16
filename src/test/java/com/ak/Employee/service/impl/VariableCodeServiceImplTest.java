package com.ak.Employee.service.impl;

import com.ak.Employee.repository.EnterpriseOriginalRepo;
import com.ak.Employee.repository.EnterpriseRepo;
import com.ak.Employee.repository.VariableCodeRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class VariableCodeServiceImplTest {

    @Mock
    private VariableCodeRepo variableCodeRepo;

    @InjectMocks
    private VariableCodeServiceImpl variableCodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks
        variableCodeService = new VariableCodeServiceImpl(variableCodeRepo);
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void save() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("test data".getBytes());

        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", inputStream);

        variableCodeService.save(file);

        verify(variableCodeRepo, times(1)).saveAll(any());
    }
}