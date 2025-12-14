package service;

import model.Employee;
import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee emp);
    void updateEmployee(Employee emp);
    void deleteEmployee(int id);
    List<Employee> getAllEmployees();
}
