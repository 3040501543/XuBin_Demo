package com.lambdaDemo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamApi {


    public static void main(String[] args) {
        Employee e1 = new Employee(1,23,"M","Rick","Beethovan");
        Employee e2 = new Employee(2,13,"F","Martina","Hengis");
        Employee e3 = new Employee(3,43,"M","Ricky","Martin");
        Employee e4 = new Employee(4,26,"M","Jon","Lowman");
        Employee e5 = new Employee(5,19,"F","Cristine","Maria");
        Employee e6 = new Employee(6,75,"M","David","Feezor");
        Employee e7 = new Employee(7,77,"F","Melissa","Roy");
        Employee e8 = new Employee(8,79,"M","Alex","Gussin");
        Employee e9 = new Employee(9,15,"F","Neetu","Singh");
        Employee e10 = new Employee(10,80,"M","Naveen","Jain");
        List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);

        Integer total = employees.stream().map(Employee::getAge).reduce(0,Integer::sum);
        System.out.println(total); //346
        Integer total2 = employees.stream().parallel().map(Employee::getAge).reduce(0,Integer::sum);
        System.out.println(total2); //346
                                                                                        //初始值，累加器，合并器
        Integer total3 = employees.stream().parallel().map(Employee::getAge).reduce(0,Integer::sum,Integer::sum);
        System.out.println(total3); //346



        System.out.println(employees.stream().anyMatch(e -> e.getId() > 100));

        boolean isExistAgeThan70 = employees.stream().anyMatch(Employee.ageGreaterThan70);
        System.out.println(isExistAgeThan70);

        Optional<Employee> employeeOptional
                =  employees.stream().filter(e -> e.getAge() > 76).findFirst();
        System.out.println(employeeOptional.get());
    }
}
