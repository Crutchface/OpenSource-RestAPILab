package org.oosd.restapidemo.Controller;

import org.oosd.restapidemo.entity.Employee;
import org.oosd.restapidemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    // Declare employee Service
    private EmployeeService employeeService;
    //Dependency Injection
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public String helloAPI(){
        return "Hello World";
    }
    @GetMapping("/employee")
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployees();
    }
    @GetMapping("/employee/{emp_id}")
    public Employee getEmployee(@PathVariable int emp_id){
        return employeeService.getEmployeeById(emp_id);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        employeeService.createEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
    @PutMapping("/employee/{emp_id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable int emp_id){
        employeeService.updateEmployee(emp_id, employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping ("/employee/{emp_id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int emp_id){
        employeeService.deleteEmployee(emp_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // OR YOU COULD USE THIS
//    @DeleteMapping("/employee")
//    public ResponseEntity<Void> deleteEmployee(@RequestBody int employee){
//        employeeService.deleteEmployee(employee);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    // Delete Emergency contact. No cascade to employee
    @DeleteMapping ("/employee/remove-emergencycontact/{id}")
    public ResponseEntity<Void> deleteEmergencyContact(@PathVariable int id){
        employeeService.deleteEmergContact(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/employee/{employeeid}/assigndept/{deptid}")
    public void assignEmployeeToDepartment(@PathVariable int employeeid, @PathVariable int deptid){
        employeeService.addEmployeeToDept(employeeid, deptid);
    }

    @PostMapping("/employee/{employee_id}/profile")
    public ResponseEntity<String> uploadEmployeePic(@PathVariable int employee_id, @RequestParam("image")MultipartFile file){
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
        }

        try {
            // Assuming the file name will be determined inside the service method (or default logic)
            String uploadImage = employeeService.uploadImage(employee_id, file);
            return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @PutMapping("/employee/{employee_id}/profile")
    public ResponseEntity<String> updateEmployeePic(@PathVariable int employee_id, @RequestParam("image")MultipartFile file){
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
        }
        try {
            // Assuming the file name will be determined inside the service method (or default logic)
            String uploadImage = employeeService.updateImage(employee_id, file);
            return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

}
