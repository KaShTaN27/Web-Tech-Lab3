package by.bsuir.lab3.service;

import by.bsuir.lab3.exception.NoAccessException;
import by.bsuir.lab3.exception.ResourceAlreadyExistsException;
import by.bsuir.lab3.exception.ResourceNotFoundException;
import by.bsuir.lab3.model.StudentInfo;
import by.bsuir.lab3.model.StudentInfoAccess;
import by.bsuir.lab3.repository.StudentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.bsuir.lab3.model.AccessStatus.EDIT;
import static by.bsuir.lab3.model.AccessStatus.REQUEST_VIEW;
import static by.bsuir.lab3.model.AccessStatus.VIEW;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

    private final EmployeeService employeeService;
    private final StudentInfoRepository studentInfoRepository;

    public StudentInfo save(StudentInfo studentInfo) {
        if (studentInfoRepository.existsByNameAndSurnameAndPatronymic(studentInfo.getName(),
                studentInfo.getSurname(), studentInfo.getPatronymic()))
            throw new ResourceAlreadyExistsException("Student info with such fio already exists");
        return studentInfoRepository.save(studentInfo);
    }

    public StudentInfo getById(Long id) {
        return studentInfoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Student info with id %d does not exists", id)));
    }

    public StudentInfo updateStudentInfo(StudentInfo newStudentInfo, Principal principal) {
        var info = getById(newStudentInfo.getId());
        var hasAccess = info.getInfoAccesses().stream()
                .filter(access -> access.getStatus() == EDIT)
                .map(StudentInfoAccess::getEmployee)
                .anyMatch(employee -> employee.getEmail().equals(principal.getName()));
        if (!hasAccess)
            throw new NoAccessException();
        return update(newStudentInfo);
    }

    public StudentInfo update(StudentInfo newStudentInfo) {
        var oldStudentInfo = getById(newStudentInfo.getId());
        oldStudentInfo.setAge(newStudentInfo.getAge());
        oldStudentInfo.setCourse(newStudentInfo.getCourse());
        return studentInfoRepository.save(oldStudentInfo);
    }

    public Map<Long, String> getAllStudentInfoHeaders() {
        return studentInfoRepository.findAll().stream()
                .map(info -> Map.entry(info.getId(), concatFullName(info).toString()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private StringBuilder concatFullName(StudentInfo studentInfo) {
        return new StringBuilder(studentInfo.getSurname())
                .append(" ")
                .append(studentInfo.getName())
                .append(" ")
                .append(studentInfo.getPatronymic());
    }

    public StudentInfo getStudentInfo(Long id, Principal principal) {
        var studentInfo = getById(id);
        var hasAccess = studentInfo.getInfoAccesses().stream()
                .filter(access -> access.getStatus() != REQUEST_VIEW)
                .map(StudentInfoAccess::getEmployee)
                .anyMatch(employee -> employee.getEmail().equals(principal.getName()));
        if (!hasAccess)
            throw new NoAccessException();
        return studentInfo;
    }

    public List<StudentInfoAccess> getAllAccessibleStudentInfos(Principal principal) {
        var employee = employeeService.getEmployeeByEmail(principal.getName());
        return employee.getStudentInfos().stream()
                .filter(access -> access.getStatus() == VIEW || access.getStatus() == EDIT)
                .toList();
    }
}
