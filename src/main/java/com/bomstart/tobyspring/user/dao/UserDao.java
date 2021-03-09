package com.bomstart.tobyspring.user.dao;

import com.bomstart.tobyspring.user.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("devandy");
        user.setName("youngjinmo");
        user.setPassword("12345678");

        dao.add(user);

        System.out.println(user.getId()+" 등록성공 !!");
    }

    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bomstartDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1q2w3e4r";

    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO user(id, name, password) VALUES(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

}
