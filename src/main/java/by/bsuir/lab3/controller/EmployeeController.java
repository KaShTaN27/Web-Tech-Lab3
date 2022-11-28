package by.bsuir.lab3.controller;

import by.bsuir.lab3.model.Employee;
import by.bsuir.lab3.model.Role;
import by.bsuir.lab3.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee> updateRoleById(@PathVariable Long id, @RequestParam String role) {
        return ResponseEntity.ok(employeeService.updateRoleById(id, Role.valueOf(role)));
    }
}
