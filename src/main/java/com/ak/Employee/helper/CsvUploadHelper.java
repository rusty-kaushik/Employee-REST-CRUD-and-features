package com.ak.Employee.helper;

import com.ak.Employee.model.Enterprise;
import com.ak.Employee.model.EnterpriseOriginal;
import com.ak.Employee.model.VariableCode;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvUploadHelper {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file){
        return TYPE.equals(file.getContentType());
    }

    public static List<Enterprise> csvToEnterprise(InputStream inputStream) throws IOException {
        try(
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            )
        {
            List<Enterprise> enterpriseList = new ArrayList<Enterprise>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords){
                Enterprise enterprise = new Enterprise(
                        Integer.parseInt(csvRecord.get("Year")),
                        csvRecord.get("Industry_aggregation_NZSIOC"),
                        csvRecord.get("Industry_code_NZSIOC"),
                        csvRecord.get("Industry_name_NZSIOC"),
                        csvRecord.get("Units"),
                        csvRecord.get("Variable_code"),
                        csvRecord.get("Variable_name"),
                        csvRecord.get("Variable_category"),
                        csvRecord.get("Value"),
                        csvRecord.get("Industry_code_ANZSIC06")
                );
                enterpriseList.add(enterprise);
            }
            return enterpriseList;
        }catch(IOException e){
            throw new RuntimeException("Failed to parse file : "+e.getMessage()) ;
        }

    }

    public static List<EnterpriseOriginal> csvToEnterpriseOriginal(InputStream inputStream) throws IOException {
        try(
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        )
        {
            List<EnterpriseOriginal> enterpriseList = new ArrayList<EnterpriseOriginal>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords){
                EnterpriseOriginal enterprise = new EnterpriseOriginal(
                        Integer.parseInt(csvRecord.get("Year")),
                        csvRecord.get("Industry_aggregation_NZSIOC"),
                        csvRecord.get("Industry_code_NZSIOC"),
                        csvRecord.get("Industry_name_NZSIOC"),
                        csvRecord.get("Units"),
                        csvRecord.get("Variable_code"),
                        csvRecord.get("Variable_name"),
                        csvRecord.get("Variable_category"),
                        csvRecord.get("Value"),
                        csvRecord.get("Industry_code_ANZSIC06")
                );
                enterpriseList.add(enterprise);
            }
            return enterpriseList;
        }catch(IOException e){
            throw new RuntimeException("Failed to parse file : "+e.getMessage()) ;
        }

    }

//    public static Set<VariableCode> csvToVariableCodes(InputStream inputStream) throws IOException {
//        try (
//                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
//                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
//        ) {
//            Set<String> variableCodeValues = new HashSet<>(); // To store unique Variable_code values
//            Set<VariableCode> variableCodes = new HashSet<>(); // To store unique VariableCode objects
//
//            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
//
//            for (CSVRecord csvRecord : csvRecords) {
//                String variableCodeValue = csvRecord.get("Variable_code");
//                // Check if the value already exists in the set
//                if (!variableCodeValues.contains(variableCodeValue)) {
//                    // If not, create a new VariableCode object, set its value, and add it to the set
//                    VariableCode variableCode = new VariableCode();
//                    variableCode.setVariableCode(variableCodeValue);
//                    variableCodes.add(variableCode);
//                    variableCodeValues.add(variableCodeValue); // Add the value to the set of seen values
//                }
//            }
//            return variableCodes;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse file: " + e.getMessage());
//        }
//    }

    public static Set<VariableCode> csvToVariableCodes(InputStream inputStream) throws IOException {
        try (
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        ) {
            Set<String> variableCodeValues = new LinkedHashSet<>(); // To store unique Variable_code values in insertion order
            Set<VariableCode> variableCodes = new LinkedHashSet<>(); // To store unique VariableCode objects in insertion order

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String variableCodeValue = csvRecord.get("Variable_code");
                // Check if the value already exists in the set
                if (!variableCodeValues.contains(variableCodeValue)) {
                    // If not, create a new VariableCode object, set its value, and add it to the set
                    VariableCode variableCode = new VariableCode();
                    variableCode.setVariableCode(variableCodeValue);
                    variableCodes.add(variableCode);
                    variableCodeValues.add(variableCodeValue); // Add the value to the set of seen values
                }
            }
            return variableCodes;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse file: " + e.getMessage());
        }
    }
}
