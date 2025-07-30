package pet.project.hh.repository;

import pet.project.hh.models.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("SELECT v FROM Vacancy v WHERE v.isActive = true")
    Page<Vacancy> findAllActiveVacancies(Pageable pageable);

    List<Vacancy> findAllByCategoryId(Long categoryId);

    List<Vacancy> findByAuthor_Id(Long authorId);
}
