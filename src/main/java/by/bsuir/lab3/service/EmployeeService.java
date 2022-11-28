package by.bsuir.lab3.service;

import by.bsuir.lab3.exception.ResourceAlreadyExistsException;
import by.bsuir.lab3.exception.ResourceNotFoundException;
import by.bsuir.lab3.model.Employee;
import by.bsuir.lab3.model.Role;
import by.bsuir.lab3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EmployeeService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public Employee save(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail()))
            throw new ResourceAlreadyExistsException(
                    String.format("Employee with email %s already exists", employee.getEmail()));
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Employee with id %d does not exists", id)));
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Employee with email %s does not exists", email)));
    }

    public Employee updateRoleById(Long id, Role role) {
        Employee employee = getEmployeeById(id);
        employee.setRole(role);
        return employeeRepository.save(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var employee = getEmployeeByEmail(email);
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(employee.getRole().name()));
        return new User(employee.getEmail(), employee.getPassword(), authorities);
    }
}
