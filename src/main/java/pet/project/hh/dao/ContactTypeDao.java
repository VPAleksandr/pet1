package pet.project.hh.dao;

import pet.project.hh.models.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactTypeDao {

    private final JdbcTemplate jdbcTemplate;

    public List<ContactType> getAllContactTypes() {
        String sql = "select * from contact_types";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ContactType.class));
    }
}
