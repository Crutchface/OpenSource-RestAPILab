package org.oosd.restapidemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
public class Employee {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
        @NotBlank(message = "First Name cant be blank")
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 chars")

    private String firstName;
    private String lastName;
    @Email(message = "Email must be in email format ")
    private String email;
//    @Pattern(regexp = "\\d{10}", message = "Must be all digits totalling 10")
    private String phoneNumber;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emergency_contact_id", referencedColumnName = "id")
    private EmergencyContact emergencyContact;

    @JsonBackReference
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE , CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "dept_id", referencedColumnName = "id")
    private Department department;


    @ManyToMany( cascade = { CascadeType.PERSIST, CascadeType.MERGE , CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonManagedReference
    private Set<Projects> projects;

    String filePath;


    public Employee() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Employee(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Set<Projects> getProjects() {
        return projects;
    }

    public void setProjects(Set<Projects> projects) {
        this.projects = projects;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


}
