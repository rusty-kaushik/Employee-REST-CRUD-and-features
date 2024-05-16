package com.ak.Employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class Employee {
    @Id
    private String id;
    private String name;
    private int age;
    private int salary;
    private Date joiningDate;

    public Employee() {
    }

    public Employee(String id, String name, int age, int salary, Date joiningDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.joiningDate = joiningDate;
    }
    public Employee(String id, String name, int age, int salary, String joiningDate) throws ParseException {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.joiningDate = formatter.parse(joiningDate);
    }

    public Employee(String name, int age, int salary, Date joiningDate) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.joiningDate = joiningDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge( int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary( int salary) {
        this.salary = salary;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
}
