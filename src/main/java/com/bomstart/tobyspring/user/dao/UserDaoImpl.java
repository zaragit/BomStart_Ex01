package com.bomstart.tobyspring.user.dao;

import com.bomstart.tobyspring.user.domain.User;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao{
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    /**
     * 유저 조회
     * @param id
     * @return User - 유저 정보
     */
    @Override
    public User selectUser(String id) throws EmptyResultDataAccessException {
        return this.jdbcTemplate.queryForObject("SELECT id, name, password FROM user WHERE id = ? ", new Object[]{id}, userMapper);
    }

    /**
     * 모든 유저 조회
     * @return List<User> - user 테이블의 모든 유저정보
     */
    @Override
    public List<User> selectUsers() {
        return this.jdbcTemplate.query("SELECT id, name, password FROM user ORDER BY id", userMapper);
    }

    /**
     *  유저 등록
     * @param user
     */
    @Override
    public void createUser(User user) throws DuplicateKeyException{
        this.jdbcTemplate.update("INSERT INTO user(id, name, password) VALUES(?,?,?)",
                user.getId(), user.getName(), user.getPassword());
    }

    /**
     * 유저 정보 수정
     * @param user
     */
    @Override
    public void updateUser(User user) throws EmptyResultDataAccessException {
        this.jdbcTemplate.update("UPDATE user SET name = ?, password = ? WHERE id = ?"
                , user.getName(), user.getPassword(), user.getId());
    }

    @Override
    public void deleteUser(String id) throws EmptyResultDataAccessException {
        this.jdbcTemplate.update("DELETE FROM user WHERE id = ? ", id);
    }

}
