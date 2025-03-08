package org.oosd.restapidemo.service;

import org.oosd.restapidemo.entity.Department;
import org.oosd.restapidemo.entity.EmergencyContact;
import org.oosd.restapidemo.entity.Employee;
import org.oosd.restapidemo.repository.DepartmentRepo;
import org.oosd.restapidemo.repository.EmergencyContactRepo;
import org.oosd.restapidemo.repository.EmployeeRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// Buisiness logic relating to the employee

@Service
public class EmployeeService {


    // Needs to connect to the DB
    private EmployeeRepo employeeRepo;
    private EmergencyContactRepo emergencyContactRepo;
    private DepartmentRepo departmentRepo;

    public EmployeeService(EmployeeRepo employeeRepo, EmergencyContactRepo emergencyContactRepo, DepartmentRepo departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.emergencyContactRepo = emergencyContactRepo;
        this.departmentRepo = departmentRepo;
    }
    // Get All employees
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // Return 1 Employee
    public Employee getEmployeeById(int id) {
        return employeeRepo.findById((long) id).orElse(null);
    }

    // Update an employee
//    public void updateEmployee(Employee employee) {
//        Optional<Employee> employee1 = employeeRepo.findById((long) employee.getId());
//        if (employee1.isPresent()) {
//            employeeRepo.save(employee);
//        }
//    }

    // Create employee
    public void createEmployee(Employee employee) {
        if(employee.getEmergencyContact()!= null) {
            employee.getEmergencyContact().setEmployee(employee);
        }
        employeeRepo.save(employee);

    }


    public void updateEmployee(int empId, Employee employee) {
       Employee empExist = employeeRepo.findById((long)empId).orElse(null);
       if (empExist != null) {
           empExist.setFirstName(employee.getFirstName());
           empExist.setLastName(employee.getLastName());
           empExist.setEmail(employee.getEmail());
           empExist.setPhoneNumber(employee.getPhoneNumber());

           if (employee.getEmergencyContact() != null) {
               EmergencyContact emergencyContact = employee.getEmergencyContact();
               emergencyContact.setId(empExist.getEmergencyContact().getId());
               emergencyContact.setEmployee(empExist);
               empExist.setEmergencyContact(emergencyContact);
           }
       }
       employeeRepo.save(empExist);
    }

    public void deleteEmployee(int empId) {

        employeeRepo.deleteById((long) empId);
    }


    // EmergencyContactRepo contactRepo is in here because its associated
    // Delete emergency contact  without deleting employee

    public void deleteEmergContact(int id){
        EmergencyContact ec = emergencyContactRepo.findById((long)id).orElse(null);
        if (ec != null) {
            Employee employee = ec.getEmployee();
            // Disconnect the ec from the employee
            employee.setEmergencyContact(null);
            employeeRepo.save(employee);

        }
        emergencyContactRepo.delete(ec);
    }

    public void addEmployeeToDept(int empId, int depId) {
        Employee employee = employeeRepo.findById((long)empId).orElse(null);
        Department department = departmentRepo.findById((long)depId).orElse(null);
        if (employee != null && department != null) {
            employee.setDepartment(department);
            employeeRepo.save(employee);
        }

    }


    public String uploadImage(int employee_id, MultipartFile file) {
        try {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            Employee employee = employeeRepo.findById((long)employee_id).orElse(null);
            String fileName = employee.getFirstName() + "_" + employee.getLastName() + extension;

            if (employee == null) {
                return "Employee Not found! ";

            }
            if (employee.getFilePath() != null) {
                return "An image already exists for this user! ";
            }
            String directory = System.getProperty("user.dir");
            File profileImage = new File(directory, "ProfileImages");
            if (!profileImage.exists()) {
                profileImage.mkdir();
            }
            Path filePath = Paths.get(profileImage.getAbsolutePath() ,fileName);
            file.transferTo(filePath.toFile());

            String dbPath = "/ProfileImages/" + file.getOriginalFilename();
            employee.setFilePath(dbPath);
            employeeRepo.save(employee);

            return filePath.toString();



        } catch (IOException e) {
            return e.getMessage();
        }

    }

    public String updateImage(int employee_id, MultipartFile file) {
        try {
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            Employee employee = employeeRepo.findById((long)employee_id).orElse(null);
            String fileName = employee.getFirstName() + "_" + employee.getLastName() + extension;
            if (employee == null) {
                return "Employee Not found! ";

            }
            if (employee.getFilePath() == null) {
                return "This user has no image to update";
            }
            String directory = System.getProperty("user.dir");
            File profileImage = new File(directory, "ProfileImages");
            if (!profileImage.exists()) {
                profileImage.mkdir();
            }
            Path filePath = Paths.get(profileImage.getAbsolutePath() ,fileName);
            file.transferTo(filePath.toFile());

            String dbPath = "/ProfileImages/" + file.getOriginalFilename();
            employee.setFilePath(dbPath);
            employeeRepo.save(employee);

            return filePath.toString();



        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
