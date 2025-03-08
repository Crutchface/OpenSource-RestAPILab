package org.oosd.restapidemo.service;

import org.oosd.restapidemo.entity.Department;
import org.oosd.restapidemo.entity.Employee;
import org.oosd.restapidemo.repository.DepartmentRepo;
import org.oosd.restapidemo.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private DepartmentRepo departmentRepo;
    private EmployeeRepo employeeRepo;

    public DepartmentService(DepartmentRepo departmentRepo, EmployeeRepo employeeRepo) {
        this.departmentRepo = departmentRepo;
        this.employeeRepo = employeeRepo;
    }

    // Read 1
    public Department getDepartmentById(int id) {
       return departmentRepo.findById((long)id).orElse(null);
    };
    // Read all
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    };
    // Create
    public void createDepartment(Department department) {
        departmentRepo.save(department);

    }
    // Update
    public void updateDepartment(int dept, Department department) {
        Department existingDepartment = departmentRepo.findById((long)department.getId()).orElse(null);
        if (existingDepartment != null) {
            existingDepartment.setName(department.getName());
        }
            departmentRepo.save(existingDepartment);

    }
    //Delete
    public void deleteDepartment(int id) {
        Department delDepartment = departmentRepo.findById((long)id).orElse(null);
        if (delDepartment != null) {
            if (delDepartment.getEmployees() != null) {
                for (Employee employee : delDepartment.getEmployees()) {
                    employee.setDepartment(null);
                    employeeRepo.save(employee);
                }

            }
            departmentRepo.delete(delDepartment);

        }
    }
}
