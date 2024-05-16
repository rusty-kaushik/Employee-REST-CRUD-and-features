package com.ak.Employee.controller;

import com.ak.Employee.helper.CsvUploadHelper;
import com.ak.Employee.message.Response;
import com.ak.Employee.service.VariableCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/VendorCode")
public class VariableCodeController {

    @Autowired
    VariableCodeService variableCodeService;

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadCSV(@RequestParam("file") MultipartFile file){
        if(CsvUploadHelper.hasCSVFormat(file)){
                variableCodeService.save(file);
                return ResponseEntity.status(HttpStatus.OK).body(new Response("Successfully uploaded the CSV file : " + file.getOriginalFilename()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Please upload a CSV file"));
    }
}
