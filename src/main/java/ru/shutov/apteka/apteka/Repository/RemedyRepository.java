package ru.shutov.apteka.apteka.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shutov.apteka.apteka.Model.Remedy;

public interface RemedyRepository extends JpaRepository<Remedy, Long> {
}
