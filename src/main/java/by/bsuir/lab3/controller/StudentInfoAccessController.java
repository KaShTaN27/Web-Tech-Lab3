package by.bsuir.lab3.controller;

import by.bsuir.lab3.dto.RequestAccessDto;
import by.bsuir.lab3.model.StudentInfoAccess;
import by.bsuir.lab3.service.StudentInfoAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/access")
public class StudentInfoAccessController {

    private final StudentInfoAccessService infoAccessService;

    @PostMapping
    public ResponseEntity<StudentInfoAccess> requestAccess(@RequestBody RequestAccessDto requestAccessDto,
                                                           Principal principal) {
        return ResponseEntity.ok(infoAccessService.requestAccess(requestAccessDto, principal));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StudentInfoAccess> submitRequestedAccess(@RequestBody RequestAccessDto accessDto) {
        return ResponseEntity.ok(infoAccessService.submitRequest(accessDto));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Boolean> rejectRequestedAccess(@RequestBody RequestAccessDto accessDto) {
        return ResponseEntity.ok(infoAccessService.rejectAccess(accessDto));
    }
}
