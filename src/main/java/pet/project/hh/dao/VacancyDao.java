package pet.project.hh.dao;

import pet.project.hh.models.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VacancyDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Vacancy> getAllVacancies() {
        String sql = "select * from vacancies";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> getAllActiveVacancies() {
        String sql = "select * from vacancies where is_active = true";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> getVacanciesWithApplicant(Long applicantId) {
        String sql = "select v.* " +
                "from responded_applicants ra " +
                "inner join resumes r on ra.resume_id = r.id " +
                "inner join vacancies v on ra.vacancy_id = v.id " +
                "where r.applicant_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), applicantId);
    }

    public List<Vacancy> getVacanciesWithCategory(Long categoryId) {
        String sql = "select * from vacancies where category_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), categoryId);
    }

    public void createVacancy(Vacancy vacancy) {
        String sql = "insert into vacancies (name, description, category_id, salary, experience_min, experience_max, is_active, author_id, created_time, updated_time) " +
                "values (:name, :description, :category_id, :salary, :experience_min, :experience_max, :is_active, :author_id, :created_time, :updated_time)";
        namedParameterJdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("name", vacancy.getName())
                        .addValue("description", vacancy.getDescription())
                        .addValue("category_id", vacancy.getCategory())
                        .addValue("salary", vacancy.getSalary())
                        .addValue("experience_min", vacancy.getExperienceMin())
                        .addValue("experience_max", vacancy.getExperienceMax())
                        .addValue("is_active", vacancy.isActive())
                        .addValue("author_id", vacancy.getAuthor())
                        .addValue("created_time", vacancy.getCreatedTime())
                        .addValue("updated_time", vacancy.getUpdatedTime())
        );
    }

    public void deleteVacancy(Long vacancyId) {
        String sql = "delete from vacancies where id = ?";
        jdbcTemplate.update(sql, vacancyId);
    }

    public Optional<Vacancy> getVacancyById(Long vacancyId) {
        String sql = "SELECT * FROM vacancies WHERE id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new BeanPropertyRowMapper<>(Vacancy.class),
                                vacancyId
                        )
                )
        );
    }

    public void updateVacancy(Vacancy vacancy, Long id) {
        String sql = "UPDATE vacancies SET name = ?, description = ?, category_id = ?, salary = ?, experience_min = ?, experience_max = ?, is_active = ?, author_id = ?, created_time = ?, updated_time = ? WHERE id = ?";

        Vacancy updatedVacancy = getVacancyById(id).orElseThrow();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, vacancy.getName());
            ps.setString(2, vacancy.getDescription());
            ps.setLong(3, vacancy.getCategory().getId());
            ps.setDouble(4, vacancy.getSalary());
            ps.setInt(5, vacancy.getExperienceMin());
            ps.setInt(6, vacancy.getExperienceMax());
            ps.setBoolean(7, vacancy.isActive());
            ps.setLong(8, vacancy.getAuthor().getId());
            ps.setTimestamp(9, Timestamp.valueOf(updatedVacancy.getCreatedTime()));
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(11, id);
            return ps;
        });
    }
}
