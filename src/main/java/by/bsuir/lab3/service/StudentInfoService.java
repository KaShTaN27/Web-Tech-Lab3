package by.bsuir.lab3.service;

import by.bsuir.lab3.exception.ResourceAlreadyExistsException;
import by.bsuir.lab3.exception.ResourceNotFoundException;
import by.bsuir.lab3.model.StudentInfo;
import by.bsuir.lab3.repository.StudentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

    private final StudentInfoRepository studentInfoRepository;

    public StudentInfo save(StudentInfo studentInfo) {
        if (studentInfoRepository.existsByNameAndSurnameAndPatronymic(studentInfo.getName(),
                studentInfo.getSurname(),studentInfo.getPatronymic()))
            throw new ResourceAlreadyExistsException("Student info with such fio already exists");
        return studentInfoRepository.save(studentInfo);
    }

    public StudentInfo getById(Long id) {
        return studentInfoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Student info with id %d does not exists", id)));
    }

    public StudentInfo update(StudentInfo newStudentInfo) {
        var oldStudentInfo = getById(newStudentInfo.getId());
        oldStudentInfo.setAge(newStudentInfo.getAge());
        oldStudentInfo.setCourse(newStudentInfo.getCourse());
        return studentInfoRepository.save(oldStudentInfo);
    }
}
