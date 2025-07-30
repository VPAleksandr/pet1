package pet.project.hh.dao;

import pet.project.hh.models.Education;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Education> getEducationByResumeId(Long resumeId) {
        String sql = "select * from educations where resume_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Education.class), resumeId);
    }

    public void deleteEducationByResumeId(Long resumeId) {
        String sql = "delete from educations where resume_id = ?";
        jdbcTemplate.update(sql, resumeId);
    }

    public Education getEducationById(Long educationId) {
        String sql = "select * from educations where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Education.class), educationId);
    }

    public void createEducation(Education education) {
        String sql = "insert into educations (resume_id, institution, program, start_date, end_date, degree) values (:resumeId, :institution, :program, :startDate, :endDate, :degree)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("resumeId", education.getResume())
                .addValue("institution", education.getInstitution())
                .addValue("program", education.getProgram())
                .addValue("startDate", education.getStartDate())
                .addValue("endDate", education.getEndDate())
                .addValue("degree", education.getDegree());
        namedParameterJdbcTemplate.update(sql, params);
    }


}
