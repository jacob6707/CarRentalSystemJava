package dev.jacob6707.carrentalsystemjavafx.util.database;

import dev.jacob6707.carrentalsystemjavafx.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseUtils {
    private static final Logger log = LoggerFactory.getLogger(DatabaseUtils.class);
    private static final String DB_PROPERTIES_PATH = "db.properties";

    private DatabaseUtils() {}

    /**
     * Creates a database connection using the database properties file
     * @return Connection to the database
     * @throws IOException if there is an error reading the database properties file
     */
    public static Connection createConnection() throws IOException {
        try (InputStream inputStream = DatabaseUtils.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_PATH)) {
            Properties props = new Properties();
            props.load(inputStream);

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
    public static int runUpdate(String query, Object... params) throws IOException {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            int count = preparedStatement.executeUpdate();
            preparedStatement.close();
            return count;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to run update " + query, e);
        }
    }

    public static boolean tableExists(String tableName) {
        try {
            Number count = (Number) runQuery("SELECT COUNT(*) AS COUNT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?", tableName.toUpperCase()).getFirst().get("count");
            return count.intValue() > 0;
        } catch (IOException e) {
            throw new DatabaseException("Failed to check if the table exists", e);
        }
    }

    public static void createTableFromSql(Path tableSchema) {
        try {
            InputStream inputStream = DatabaseUtils.class.getClassLoader().getResourceAsStream(tableSchema.getFileName().toString());
            if (inputStream == null) throw new FileNotFoundException("Table schema file not found");
            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            Connection connection = createConnection();

            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.close();
            connection.close();

            log.debug("Created table from SQL file: {}", tableSchema);
        } catch (IOException | SQLException e) {
            throw new DatabaseException("Failed to create table from SQL file", e);
        }
    }

    public static int backupTable(String tableName) {
        try {
            if (!tableExists(tableName)) return -1;
            runUpdate("DROP TABLE IF EXISTS " + tableName + "_bk");
            int count = runUpdate("CREATE TABLE " + tableName + "_bk AS SELECT * FROM " + tableName);
            log.debug("Backed up {} rows from table {}", count, tableName);
            return count;
        } catch (IOException e) {
            throw new DatabaseException("Failed to back up table " + tableName, e);
        }
    }

    public static int restoreTable(String tableName) {
        try {
            if (!tableExists(tableName) || !tableExists(tableName + "_bk")) return -1;
            runUpdate("SET REFERENTIAL_INTEGRITY FALSE");
            runUpdate("DELETE FROM " + tableName);
            int count = runUpdate("INSERT INTO " + tableName + " SELECT * FROM " + tableName + "_bk");
            log.debug("Restored {} rows to table {}", count, tableName);
            // Cascades deletes from rentals for specific tables
            if (tableName.equalsIgnoreCase("customers") || tableName.equalsIgnoreCase("vehicles"))
                runUpdate("DELETE FROM rentals WHERE " + tableName.substring(0, tableName.length()-1) + "_id NOT IN (SELECT id FROM " + tableName + ")");
            if (tableName.equalsIgnoreCase("rentals")) runUpdate("DELETE FROM rentals WHERE customer_id NOT IN (SELECT id FROM customers) OR vehicle_id NOT IN (SELECT id FROM vehicles)");
            runUpdate("SET REFERENTIAL_INTEGRITY TRUE");
            return count;
        } catch (IOException e) {
            throw new DatabaseException("Failed to restore table " + tableName, e);
        }
    }
}
