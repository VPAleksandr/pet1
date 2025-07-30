package pet.project.hh.dao;

import pet.project.hh.models.UserAvatar;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAvatarDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<UserAvatar> getImageById(long imageId) {
        String sql = "select * from movie_images where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                sql,
                                new BeanPropertyRowMapper<>(UserAvatar.class),
                                imageId
                        )
                )
        );
    }

    public void save(Long movieId, String filename) {
        String sql = "insert into movie_images (movie_id, file_name) values (?, ?)";
        jdbcTemplate.update(sql, movieId, filename);
    }

    public Optional<UserAvatar> findByMovieId(long movieId) {
        String sql = "select * from movie_images where movie_id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserAvatar.class), movieId)
                )
        );
    }
}
