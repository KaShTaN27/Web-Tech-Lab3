package by.bsuir.lab3.repository;

import by.bsuir.lab3.model.StudentInfoAccess;
import by.bsuir.lab3.model.StudentInfoAccessKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoAccessRepository extends JpaRepository<StudentInfoAccess, StudentInfoAccessKey> {
}
