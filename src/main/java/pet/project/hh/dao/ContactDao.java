package pet.project.hh.dao;

import pet.project.hh.models.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Contact> getContactsByResumeId(Long resumeId) {
        String sql = "select * from contacts where resume_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class), resumeId);
    }

    public Contact getContactById(Long id) {
        String sql = "select * from contacts where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Contact.class), id);
    }

    public List<Contact> getContactByResumeId(Long resumeId) {
        String sql = "select * from contacts where resume_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Contact.class), resumeId);
    }

    public void deleteContactsByResumeId(Long resumeId) {
        String sql = "delete from contacts where resume_id = ?";
        jdbcTemplate.update(sql, resumeId);
    }

    public void createContactForResume(Contact contact) {
        String sql = "insert into contacts (type_id, resume_id, contact_value) values (:typeId, :resumeId, :contactValue)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("typeId", contact.getType())
                .addValue("resumeId", contact.getResume())
                .addValue("contactValue", contact.getContactValue());
        namedParameterJdbcTemplate.update(sql, params);
    }

}
