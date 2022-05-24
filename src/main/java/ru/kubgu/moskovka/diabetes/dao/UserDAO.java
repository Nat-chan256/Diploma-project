package ru.kubgu.moskovka.diabetes.dao;

import org.springframework.stereotype.Component;
import ru.kubgu.moskovka.diabetes.entity.User;

import java.sql.*;

@Component
public class UserDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/diabetes_users";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(User user){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getGender());
            statement.setDate(4, new java.sql.Date(user.getBirthDate().getTime()));
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User show(String login){
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                return user;
            }

            user = new User();
            user.setBirthDate(resultSet.getDate("birthdate"));
            user.setGender(resultSet.getString("gender"));
            user.setLogin(resultSet.getString("login"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setPassword(resultSet.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
