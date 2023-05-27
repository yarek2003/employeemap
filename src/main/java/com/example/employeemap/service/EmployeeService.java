package com.example.employeemap.service;

import com.example.employeemap.Employee;
import com.example.employeemap.exceptions.EmployeeAlreadyAddedException;
import com.example.employeemap.exceptions.EmployeeNotFoundException;
import com.example.employeemap.exceptions.EmployeeStorageIsFullException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    public static final int MAX_SIZE = 100;

    private final Map<String, Employee> employees;

    public EmployeeService() {
        this.employees = new HashMap<>();
    }

    public Employee add (String firstName, String lastName){
        if (employees.size()>MAX_SIZE){
            throw new EmployeeStorageIsFullException();
        }
        String key = firstName + " " + lastName;
        if (employees.containsKey(key)){
            throw new EmployeeAlreadyAddedException();
        }
        Employee employee = new Employee(firstName, lastName);
        employees.put(key, employee);
        return employee;
    }
    public Employee del (String firstName, String lastName){
        String key = firstName + " " + lastName;
        if (!employees.containsKey(key)){
            throw new EmployeeNotFoundException();
        }
        Employee removed = employees.remove(key);
        return removed;
    }

    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.containsKey(employee.getKey())){
            throw new EmployeeNotFoundException();
        }
        return employees.get(employee.getKey());
    }


    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employees.values());
    }
}
