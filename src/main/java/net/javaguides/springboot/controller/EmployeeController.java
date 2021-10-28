package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @GetMapping("employees")
    public List<Employee> getAllEmployee(){
        return this.repository.findAll();
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" +employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return this.repository.save(employee);
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException{
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" +employeeId));
        employee.setEmail(employeeDetails.getEmail());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        return ResponseEntity.ok(this.repository.save(employee));
    }

    @DeleteMapping("employee/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException{
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" +employeeId));
        this.repository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
