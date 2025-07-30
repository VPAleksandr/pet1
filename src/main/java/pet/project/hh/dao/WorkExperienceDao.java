package pet.project.hh.dao;

import pet.project.hh.models.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<WorkExperience> getExperiencesByResumeId(Long resumeId) {
        String sql = "select * from work_experience_info where resume_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WorkExperience.class), resumeId);
    }

    public void deleteWorkExperiencesByResumeId(Long resumeId) {
        String sql = "delete from work_experience_info where resume_id = ?";
        jdbcTemplate.update(sql, resumeId);
    }

    public void createWorkExperienceForResume(WorkExperience workExperience) {
        String sql = "insert into work_experience_info (resume_id, years, company_name, position, responsibilities) values (:resumeId, :years, :companyName, :position, :responsibilities)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("resumeId", workExperience.getResume())
                .addValue("years", workExperience.getYears())
                .addValue("companyName", workExperience.getCompanyName())
                .addValue("position", workExperience.getPosition())
                .addValue("responsibilities", workExperience.getResponsibilities());
        namedParameterJdbcTemplate.update(sql, params);
    }
}
