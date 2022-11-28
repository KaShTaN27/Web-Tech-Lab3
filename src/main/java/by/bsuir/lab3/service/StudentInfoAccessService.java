package by.bsuir.lab3.service;

import by.bsuir.lab3.dto.RequestAccessDto;
import by.bsuir.lab3.exception.ResourceNotFoundException;
import by.bsuir.lab3.exception.UndefinedRequestedAccessException;
import by.bsuir.lab3.model.AccessStatus;
import by.bsuir.lab3.model.StudentInfoAccess;
import by.bsuir.lab3.model.StudentInfoAccessKey;
import by.bsuir.lab3.repository.StudentInfoAccessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static by.bsuir.lab3.model.AccessStatus.REQUEST_EDIT;
import static by.bsuir.lab3.model.AccessStatus.REQUEST_VIEW;

@Service
@RequiredArgsConstructor
public class StudentInfoAccessService {

    private final EmployeeService employeeService;
    private final StudentInfoService studentInfoService;
    private final StudentInfoAccessRepository infoAccessRepository;

    public StudentInfoAccess getAccessById(StudentInfoAccessKey key) {
        return infoAccessRepository.findById(key).orElseThrow(() ->
                new ResourceNotFoundException("Requested access does not exists"));
    }

    public StudentInfoAccess requestAccess(RequestAccessDto requestAccessDto, Principal principal) {
        validateRequestedAccess(requestAccessDto.getRequestedAccess());
        var employee = employeeService.getEmployeeByEmail(principal.getName());
        var studentInfo = studentInfoService.getById(requestAccessDto.getStudentInfoId());
        var infoAccess = new StudentInfoAccess(studentInfo, employee, AccessStatus.valueOf(requestAccessDto.getRequestedAccess()));
        return infoAccessRepository.save(infoAccess);
    }

    private void validateRequestedAccess(String requestedAccess) {
        if (AccessStatus.valueOf(requestedAccess) != REQUEST_EDIT &&
        AccessStatus.valueOf(requestedAccess) != REQUEST_VIEW)
            throw new UndefinedRequestedAccessException();
    }

    public StudentInfoAccess submitRequest(RequestAccessDto accessDto) {
        var accessKey = new StudentInfoAccessKey(accessDto.getStudentInfoId(), accessDto.getEmployeeId());
        var infoAccess = getAccessById(accessKey);
        var requestedStatus = infoAccess.getStatus().name().substring(8);
        infoAccess.setStatus(AccessStatus.valueOf(requestedStatus));
        return infoAccessRepository.save(infoAccess);
    }

    @Transactional
    public boolean rejectAccess(RequestAccessDto accessDto) {
        removeStudentInfoReferenceFromEmployee(accessDto.getEmployeeId(), accessDto.getStudentInfoId());
        removeEmployeeReferenceFromStudentInfo(accessDto.getEmployeeId(), accessDto.getStudentInfoId());
        var accessKey = new StudentInfoAccessKey(accessDto.getStudentInfoId(), accessDto.getEmployeeId());
        infoAccessRepository.deleteById(accessKey);
        return true;
    }

    private void removeEmployeeReferenceFromStudentInfo(Long employeeId, Long infoId) {
        var studentInfo = studentInfoService.getById(infoId);
        studentInfo.getInfoAccesses().removeIf(access -> access.getEmployee().getId().equals(employeeId));
    }

    private void removeStudentInfoReferenceFromEmployee(Long employeeId, Long infoId) {
        var employee = employeeService.getEmployeeById(employeeId);
        employee.getStudentInfos().removeIf(access -> access.getStudentInfo().getId().equals(infoId));
    }
}
