package com.ak.Employee.controller;

import com.ak.Employee.helper.CsvUploadHelper;
import com.ak.Employee.message.Response;
import com.ak.Employee.model.Enterprise;
import com.ak.Employee.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadCSV(@RequestParam("file") MultipartFile file){
        String message = "";
        if(CsvUploadHelper.hasCSVFormat(file)){
                enterpriseService.save(file);
                message = "Successfully uploaded the CSV file : " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new Response(message));
        }
        message = "Please upload a CSV file";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(message));
    }

    @PostMapping("/upload/original")
    public ResponseEntity<Response> uploadOriginalCSV(@RequestParam("file") MultipartFile file){
        if(CsvUploadHelper.hasCSVFormat(file)){
                enterpriseService.saveOriginal(file);
                return ResponseEntity.status(HttpStatus.OK).body(new Response("Successfully uploaded the CSV file : " + file.getOriginalFilename()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Please upload a CSV file"));
    }

    @GetMapping("/variableCode/{variableCode}/enterprises")
    public ResponseEntity<List<Enterprise>> getAllEnterprisesByVariableCode(@PathVariable(value = "variableCode") String variableCode) throws Exception {
        List<Enterprise> etr =  enterpriseService.getAllEnterprisesByVariableCode(variableCode);
        return ResponseEntity.status(HttpStatus.OK).body(etr);
    }

    @GetMapping
    public ResponseEntity<List<Enterprise>> getAllEnterprises() throws Exception {
        List<Enterprise> etr =  enterpriseService.getAllEnterprises();
        return ResponseEntity.status(HttpStatus.OK).body(etr);
    }

}
