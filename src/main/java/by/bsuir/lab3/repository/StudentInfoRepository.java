package by.bsuir.lab3.repository;

import by.bsuir.lab3.model.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {
    boolean existsByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);
}
