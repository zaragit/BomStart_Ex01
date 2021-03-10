package com.bomstart.tobyspring.user.dao;

import com.bomstart.tobyspring.user.domain.User;
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
    DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 유저 조회
     * @param id
     * @return User - 유저 정보
     */
    @Override
    public User selectUser(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;

        try {
            conn = this.dataSource.getConnection();
            ps = conn.prepareStatement("SELECT * FROM user WHERE id = ? ");
            ps.setString(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try{rs.close();} catch(Exception e){}
            if (ps != null) try{ps.close();} catch(Exception e){}
            if (conn != null) try{conn.close();} catch(Exception e){}
            return user;
        }
    }

    /**
     * 모든 유저 조회
     * @return List<User> - user 테이블의 모든 유저정보
     */
    @Override
    public List<User> selectUsers() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<User> users = new ArrayList<>();

        try {
            conn = this.dataSource.getConnection();

            ps = conn.prepareStatement("SELECT * FROM user");

            rs = ps.executeQuery();
            rs.next();

            while(rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }


            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) try{rs.close();} catch(Exception e){}
            if (ps != null) try{ps.close();} catch(Exception e){}
            if (conn != null) try{conn.close();} catch(Exception e){}
            return users;
        }
    }

    /**
     * 유저 정보 수정
     * @param user
     */
    @Override
    public void updateUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;

        StringBuffer query = new StringBuffer();
        query.append("UPDATE user SET ");
        if (user.getName() != null)
            query.append("name = ? ");
        if (user.getPassword() != null)
            query.append("password = ? ");
        query.append("WHERE id = ? ");

        try {
            conn = this.dataSource.getConnection();

            ps = conn.prepareStatement(query.toString());

            int idx = 1;
            if (user.getName() != null)
                ps.setString(idx++, user.getName());
            if (user.getPassword() != null)
                ps.setString(idx++, user.getPassword());
            ps.setString(idx, user.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if ( ps != null ) try{ps.close();} catch(Exception e){}
            if ( conn != null ) try{conn.close();} catch(Exception e){}
        }
    }

}
