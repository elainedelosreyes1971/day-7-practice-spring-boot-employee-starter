package com.thoughtworks.springbootemployee.model;

public class Employee {
    public static final int MIN_VALID_AGE = 18;
    public static final int MAX_VALID_AGE = 65;
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Long companyId;
    private Boolean activeStatus;

    public Employee() {

    }

    public Employee(String name, Integer age, String gender, Integer salary) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Employee(Long companyId, Long id, String name, Integer age, String gender, Integer salary) {
        this.companyId = companyId;
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.activeStatus = getActiveStatus();
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

    public void merge(Employee employee) {
        this.age = employee.age;
        this.salary = employee.salary;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean hasInvalidAge() {
        return getAge() < MIN_VALID_AGE || getAge() > MAX_VALID_AGE;
    }
}
