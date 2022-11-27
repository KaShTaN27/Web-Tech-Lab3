package by.bsuir.lab3;

import by.bsuir.lab3.model.AccessStatus;
import by.bsuir.lab3.model.Employee;
import by.bsuir.lab3.model.Role;
import by.bsuir.lab3.model.StudentInfo;
import by.bsuir.lab3.model.StudentInfoAccess;
import by.bsuir.lab3.repository.EmployeeRepository;
import by.bsuir.lab3.repository.StudentInfoAccessRepository;
import by.bsuir.lab3.repository.StudentInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class Lab3Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

    @Bean
    CommandLineRunner run(EmployeeRepository employeeRepository, StudentInfoRepository studentInfoRepository, StudentInfoAccessRepository infoAccessRepository) {
        return args -> {
            var employee = new Employee("name", "email", "password", Role.ADMIN);
            var studentInfo = new StudentInfo("name", "surname", "patronymic", (short) 12, (short) 3, "sss");
            var studentInfoAccess = new StudentInfoAccess(studentInfo, employee, AccessStatus.EDIT);
            studentInfoRepository.save(studentInfo);
            employeeRepository.save(employee);
            infoAccessRepository.save(studentInfoAccess);
            Optional<StudentInfoAccess> byId = infoAccessRepository.findById(studentInfoAccess.getId());
            Optional<Employee> byId1 = employeeRepository.findById(employee.getId());
            Employee employee1 = byId1.get();
        };
    }

}
