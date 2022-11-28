package by.bsuir.lab3.controller;

import by.bsuir.lab3.model.StudentInfo;
import by.bsuir.lab3.model.StudentInfoAccess;
import by.bsuir.lab3.service.StudentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/infos")
public class StudentInfoController {

    private final StudentInfoService studentInfoService;

    @GetMapping("/headers")
    public ResponseEntity<Map<Long, String>> getAllStudentInfoHeaders() {
        return ResponseEntity.ok(studentInfoService.getAllStudentInfoHeaders());
    }

    @GetMapping
    public ResponseEntity<List<StudentInfoAccess>> getAllAccessibleInfos(Principal principal) {
        return ResponseEntity.ok(studentInfoService.getAllAccessibleStudentInfos(principal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentInfo> getStudentInfoById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(studentInfoService.getStudentInfo(id, principal));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATOR', 'ADMIN')")
    public ResponseEntity<StudentInfo> saveStudentInfo(@RequestBody StudentInfo studentInfo) {
        return ResponseEntity.ok(studentInfoService.save(studentInfo));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('CREATOR', 'ADMIN')")
    public ResponseEntity<StudentInfo> updateStudentInfo(@RequestBody StudentInfo studentInfo, Principal principal) {
        return ResponseEntity.ok(studentInfoService.updateStudentInfo(studentInfo, principal));
    }
}
