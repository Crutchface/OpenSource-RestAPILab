package org.oosd.restapidemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;

import java.util.Date;
import java.util.Set;

@Entity
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Min(value = 2, message = "Projects should be more than 2 years ")
    @Max(value = 10, message = "Projects cant be more than 10 years")
    private int duration;
    @Future
    private Date startDate;

    @ManyToMany(mappedBy = "projects", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonBackReference
    private Set<Employee> employees;

    public Projects() {
    }

    public Projects(String name, int duration, Date startDate) {
        this.name = name;
        this.duration = duration;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Min(value = 2, message = "Projects should be more than 2 years ")
    @Max(value = 10, message = "Projects cant be more than 10 years")
    public int getDuration() {
        return duration;
    }

    public void setDuration(@Min(value = 2, message = "Projects should be more than 2 years ") @Max(value = 10, message = "Projects cant be more than 10 years") int duration) {
        this.duration = duration;
    }

    public @Future Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@Future Date startDate) {
        this.startDate = startDate;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Projects{" +
                "startDate=" + startDate +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
