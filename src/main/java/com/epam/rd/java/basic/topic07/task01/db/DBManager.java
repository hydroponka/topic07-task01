package com.epam.rd.java.basic.topic07.task01.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.epam.rd.java.basic.topic07.task01.db.entity.*;

import static com.epam.rd.java.basic.topic07.task01.db.Fields.*;

public class DBManager {
	private static DBManager instance;
	private Connection connection;

	private DBManager(Connection connection) {
		this.connection = connection;
	}

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null){
			try (InputStream inputStream = new FileInputStream("app.properties")){
				Properties properties = new Properties();
				properties.load(inputStream);
				String connectionUrl = properties.getProperty("connection.url");
				Connection connection = DriverManager.getConnection(connectionUrl);
				instance = new DBManager(connection);
			}catch (FileNotFoundException e) {
				throw new DBException(e);
			} catch (IOException e) {
				throw new DBException(e);
			} catch (SQLException e) {
				throw new DBException(e);
			}
		}
		return instance;
	}

	public List<User> findAllUsers() throws DBException {
		try (Statement statement = connection.createStatement()){
			ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
			List<User> users = new ArrayList<>();
			while (resultSet.next()){
				int id = resultSet.getInt(USER_ID);
				String login = resultSet.getString(USER_LOGIN);
				users.add(new User(id, login));
			}
			return users;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public boolean insertUser(User user) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (login) VALUES (?)")) {
			preparedStatement.setString(1, user.getLogin());
			int result = preparedStatement.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public User getUser(String login) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE (login) = (?)")){
			preparedStatement.setString(1,login);
			ResultSet resultSet = preparedStatement.executeQuery();
			int id = resultSet.getInt(USER_ID);
			String userLogin = resultSet.getString(USER_LOGIN);
			return new User(id,userLogin);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public Team getTeam(String name) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teams WHERE (name) = (?)")){
			preparedStatement.setString(1,name);
			ResultSet resultSet = preparedStatement.executeQuery();
			int id = resultSet.getInt(TEAM_ID);
			String teamName = resultSet.getString(TEAM_NAME);
			return new Team(id,teamName);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public List<Team> findAllTeams() throws DBException {
		try (Statement statement = connection.createStatement()){
			ResultSet resultSet = statement.executeQuery("SELECT * FROM teams");
			List<Team> teams = new ArrayList<>();
			while (resultSet.next()){
				int id = resultSet.getInt(TEAM_ID);
				String name = resultSet.getString(TEAM_NAME);
				teams.add(new Team(id, name));
			}
			return teams;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public boolean insertTeam(Team team) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO teams (name) VALUES (?)")) {
			preparedStatement.setString(1, team.getName());
			int result = preparedStatement.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

}
