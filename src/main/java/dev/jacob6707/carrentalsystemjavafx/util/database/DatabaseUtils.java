package dev.jacob6707.carrentalsystemjavafx.util.database;

import dev.jacob6707.carrentalsystemjavafx.exception.DatabaseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

public class DatabaseUtils {
    private static final String DB_PROPERTIES_LOCATION = "src/main/resources/db.properties";

    private DatabaseUtils() {}

    /**
     * Creates a database connection using the database properties file
     * @return Connection to the database
     * @throws IOException if there is an error reading the database properties file
     */
    public static Connection createConnection() throws IOException {
        try (Reader reader = new FileReader(DB_PROPERTIES_LOCATION)) {
            Properties props = new Properties();
            props.load(reader);

            String url = props.getProperty("jdbc_url");
            String username = props.getProperty("jdbc_username");
            String password = props.getProperty("jdbc_password");

            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create database connection", e);
        }
    }

    /**
     * Closes the database connection
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to close database connection", e);
        }
    }

    /**
     * Executes a query with parameters; returns a list of result rows
     * @param query SQL query with '?' placeholders
     * @param params parameters to fill into the query
     * @throws IOException if there is an error reading the database properties file
     */
    public static List<Map<String, Object>> runQuery(String query, Object... params) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.put(metaData.getColumnName(i).toLowerCase(), rs.getObject(i));
                }
                result.add(row);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to run query " + query, e);
        }

        return result;
    }

    /**
     * Executes an update query with parameters
     * @param query SQL query with '?' placeholders
     * @param params parameters to fill into the query
     * @throws IOException if there is an error reading the database properties file
     */
    public static void runUpdate(String query, Object... params) throws IOException {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to run update " + query, e);
        }
    }

    public static boolean tableExists(String tableName) {
        try {
            return runQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?", tableName).getFirst().get("count") != null;
        } catch (IOException e) {
            throw new DatabaseException("Failed to check if the table exists", e);
        }
    }

    public static void createTableFromSql(Path tableSchema) {
        try {
            String sql = Files.readString(tableSchema);
            Connection connection = createConnection();

            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
            connection.close();

        } catch (IOException | SQLException e) {
            throw new DatabaseException("Failed to create table from SQL file", e);
        }
    }
}
