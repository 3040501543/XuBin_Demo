package com.lambdaDemo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Predicate;

@Data
@AllArgsConstructor
public class Employee {

    private Integer id;
    private Integer age;   //年龄
    private String gender;  //性别
    private String firstName;
    private String lastName;

    public static Predicate<Employee> ageGreaterThan70 = e->e.getAge()>70;

    public static Predicate<Employee> genderM = e->e.getGender().equals("M");
}