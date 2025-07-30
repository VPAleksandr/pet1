package pet.project.hh.dao;

import pet.project.hh.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public User getUserById(Long id) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    public void redactUser(User user) {
        String sql = "update users set name = ?, surname = ?, age = ? where id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            return ps;
        });
    }

    public List<User> getUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> getEmployers() {
        String sql = "select * from users where account_type = 2";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> getUserByName(String name) {
        String nameLow = name.toLowerCase();
        String sql = "select * from users where lower(name) like ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), "%" + nameLow + "%");
    }

    public Optional<User> getUserByEmail(String email) {
        String emailLow = email.toLowerCase();
        String sql = "select * from users where email = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), emailLow);
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserByNumber(String number) {
        String sql = "select * from users where phone_number = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), number);
        return Optional.ofNullable(user);
    }

    public List<User> getUsersForVacancy(Long vacancyId) {
        String sql = "select u.* " +
                "from responded_applicants ra " +
                "inner join resumes r on ra.resume_id = r.id " +
                "inner join users u on r.applicant_id = u.id " +
                "where ra.vacancy_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), vacancyId);
    }

    public void registerUser(User user) {
        String sql = "insert into users (name, surname, age, email, password, phone_number, avatar, account_type, enabled) " +
                "values (:name, :surname, :age, :email, :password, :phoneNumber, :avatar, :accountType, :enabled)";

        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        if (user.getSurname() != null) {
            params.put("surname", user.getSurname());
        } else {
            params.put("surname", "");
        }
        params.put("age", user.getAge());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("phoneNumber", user.getPhoneNumber());
        params.put("avatar", user.getAvatar());
        params.put("accountType", user.getType());
        params.put("enabled", true);

        namedParameterJdbcTemplate.update(sql, params);
    }


}
