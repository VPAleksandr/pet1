package pet.project.hh.dao;

import pet.project.hh.models.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Resume> getAllResumes() {
        String sql = "select * from resumes";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> getAllActiveResumes() {
        String sql = "select * from resumes where is_active = true";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> getAllResumeByCategory(Long categoryId) {
        String sql = "select * from resumes where category_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryId);
    }

    public List<Resume> getAllResumeByUser(Long userId) {
        String sql = "select * from resumes where applicant_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), userId);
    }

    public Optional<Resume> getResumeById(Long resumeId) {
        String sql = "select * from resumes where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new BeanPropertyRowMapper<>(Resume.class),
                                resumeId
                        )
                )
        );
    }

    public void deleteResume(Long resumeId) {
        String sql = "delete from resumes where id = ?";
        jdbcTemplate.update(sql, resumeId);
    }

    public Long createResumeAndReturnId(Resume resume) {
        String sql = "INSERT INTO resumes (applicant_id, category_id, name, salary, is_active, created_time, updated_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, resume.getApplicant().getId());
            ps.setLong(2, resume.getCategory().getId());
            ps.setString(3, resume.getName());
            ps.setDouble(4, resume.getSalary());
            ps.setBoolean(5, resume.isActive());
            ps.setTimestamp(6, Timestamp.valueOf(resume.getCreatedTime() != null ? resume.getCreatedTime() : LocalDateTime.now()));
            ps.setTimestamp(7, Timestamp.valueOf(resume.getUpdatedTime() != null ? resume.getUpdatedTime() : LocalDateTime.now()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void updateResume(Resume resume, Long id) {
        String sql = "UPDATE resumes SET applicant_id = ?, category_id = ?, name = ?, salary = ?, is_active = ?, created_time = ?, updated_time = ? WHERE id = ?";

        Resume updatedResume = getResumeById(id).orElseThrow();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, resume.getApplicant().getId());
            ps.setLong(2, resume.getCategory().getId());
            ps.setString(3, resume.getName());
            ps.setDouble(4, resume.getSalary());
            ps.setBoolean(5, resume.isActive());
            ps.setTimestamp(6, Timestamp.valueOf(updatedResume.getCreatedTime()));
            ps.setTimestamp(7, Timestamp.valueOf(resume.getUpdatedTime() != null ? resume.getUpdatedTime() : LocalDateTime.now()));
            ps.setLong(8, id);
            return ps;
        });
    }

}
