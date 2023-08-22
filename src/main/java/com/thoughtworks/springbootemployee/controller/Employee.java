package com.thoughtworks.springbootemployee.controller;

public class Employee {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Long companyId;


    public Employee() {
    }

    public Employee(long companyId, long id, String name, Integer age, String gender, Integer salary) {
        this.companyId = companyId;
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
