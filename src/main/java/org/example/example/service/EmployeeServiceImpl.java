package org.example.example.service;

import org.example.example.entity.Employee;
import org.example.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getName() == null) {
            throw new IllegalArgumentException("Employee name cannot be null");
        }
        if (employee.getSalary() <= 0) {
            throw new IllegalArgumentException("Salary must be greater than 0");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        if (updatedEmployee.getName() == null) {
            throw new IllegalArgumentException("Employee name cannot be null");
        }
        if (updatedEmployee.getSalary() <= 0) {
            throw new IllegalArgumentException("Salary must be greater than 0");
        }
        existing.setName(updatedEmployee.getName());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }
}