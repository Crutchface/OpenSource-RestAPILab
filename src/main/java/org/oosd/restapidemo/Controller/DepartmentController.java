package org.oosd.restapidemo.Controller;

import org.oosd.restapidemo.entity.Department;
import org.oosd.restapidemo.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @GetMapping("/{dept}")
    public Department getDepartments(@PathVariable int dept){
        return departmentService.getDepartmentById(dept);
    }

    @PostMapping()
    public ResponseEntity<Department> addDepartment(@RequestBody Department department){
        departmentService.createDepartment(department);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }
    @PutMapping("/{dept}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int dept, @RequestBody Department department ){
        departmentService.updateDepartment(dept, department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @DeleteMapping ("/{dept}")
    public ResponseEntity<Void> deleteDept(@PathVariable int dept){
        departmentService.deleteDepartment(dept);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // OR YOU COULD USE THIS
//    @DeleteMapping("/employee")
//    public ResponseEntity<Void> deleteEmployee(@RequestBody int employee){
//        employeeService.deleteEmployee(employee);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    // Delete Emergency contact. No cascade to employee
//    @DeleteMapping ("/employee/remove-emergencycontact/{id}")
//    public ResponseEntity<Void> deleteEmergencyContact(@PathVariable int id){
//        employeeService.deleteEmergContact(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }




}
