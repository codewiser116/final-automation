package cashwise.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DbUtils provides utility methods for database operations using JDBC.
 * It supports both Oracle and PostgreSQL databases.
 */
public class DbUtils {

    private static final Logger logger = LogManager.getLogger(DbUtils.class);

    private Connection connection;
    private String dbType;

    /**
     * Constructs a DbUtils instance with the specified database properties.
     *
     * @param dbProperties The database properties.
     * @throws SQLException           If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
     */
    public DbUtils(Properties dbProperties) throws SQLException, ClassNotFoundException {
        String dbUrl = dbProperties.getProperty("db.url");
        String dbUser = dbProperties.getProperty("db.user");
        String dbPassword = dbProperties.getProperty("db.password");
        this.dbType = dbProperties.getProperty("db.type").toLowerCase();

        // Load the appropriate JDBC driver
        if (dbType.equals("oracle")) {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } else if (dbType.equals("postgresql")) {
            Class.forName("org.postgresql.Driver");
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }

        // Establish the database connection
        this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        logger.info("Database connection established to " + dbType + " database at " + dbUrl);
    }

    /**
     * Executes a SELECT query and returns the results as a list of rows.
     * Each row is represented as a list of Objects.
     *
     * @param query The SQL query to execute.
     * @return A list of rows, where each row is a list of column values.
     * @throws SQLException If a database access error occurs.
     */
    public List<List<Object>> executeSelectQuery(String query) throws SQLException {
        logger.info("Executing SELECT query: " + query);
        List<List<Object>> results = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    row.add(value);
                }

                results.add(row);
            }
        }

        logger.info("Query executed successfully. Number of rows returned: " + results.size());
        return results;
    }

    /**
     * Executes an INSERT, UPDATE, or DELETE statement.
     *
     * @param sql The SQL statement to execute.
     * @return The number of affected rows.
     * @throws SQLException If a database access error occurs.
     */
    public int executeUpdate(String sql) throws SQLException {
        logger.info("Executing UPDATE statement: " + sql);
        int affectedRows;

        try (Statement statement = connection.createStatement()) {
            affectedRows = statement.executeUpdate(sql);
        }

        logger.info("Statement executed successfully. Number of affected rows: " + affectedRows);
        return affectedRows;
    }

    /**
     * Executes a prepared statement with parameters and returns the results.
     *
     * @param query      The SQL query with parameter placeholders.
     * @param parameters The parameters to set in the query.
     * @return A list of rows, where each row is a list of column values.
     * @throws SQLException If a database access error occurs.
     */
    public List<List<Object>> executePreparedSelectQuery(String query, List<Object> parameters) throws SQLException {
        logger.info("Executing prepared SELECT query: " + query);
        List<List<Object>> results = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setPreparedStatementParameters(preparedStatement, parameters);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                int columnCount = resultSet.getMetaData().getColumnCount();

                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>();

                    for (int i = 1; i <= columnCount; i++) {
                        Object value = resultSet.getObject(i);
                        row.add(value);
                    }

                    results.add(row);
                }
            }
        }

        logger.info("Prepared query executed successfully. Number of rows returned: " + results.size());
        return results;
    }

    /**
     * Executes a prepared UPDATE statement with parameters.
     *
     * @param sql        The SQL statement with parameter placeholders.
     * @param parameters The parameters to set in the statement.
     * @return The number of affected rows.
     * @throws SQLException If a database access error occurs.
     */
    public int executePreparedUpdate(String sql, List<Object> parameters) throws SQLException {
        logger.info("Executing prepared UPDATE statement: " + sql);
        int affectedRows;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            setPreparedStatementParameters(preparedStatement, parameters);

            affectedRows = preparedStatement.executeUpdate();
        }

        logger.info("Prepared statement executed successfully. Number of affected rows: " + affectedRows);
        return affectedRows;
    }

    /**
     * Commits the current transaction.
     *
     * @throws SQLException If a database access error occurs.
     */
    public void commit() throws SQLException {
        connection.commit();
        logger.info("Transaction committed");
    }

    /**
     * Rolls back the current transaction.
     *
     * @throws SQLException If a database access error occurs.
     */
    public void rollback() throws SQLException {
        connection.rollback();
        logger.info("Transaction rolled back");
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Failed to close database connection", e);
            }
        }
    }

    /**
     * Sets auto-commit mode for the database connection.
     *
     * @param autoCommit True to enable auto-commit, false to disable.
     * @throws SQLException If a database access error occurs.
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
        logger.info("Auto-commit set to: " + autoCommit);
    }

    /**
     * Sets the parameters for a PreparedStatement.
     *
     * @param preparedStatement The PreparedStatement object.
     * @param parameters        The list of parameters.
     * @throws SQLException If a database access error occurs.
     */
    private void setPreparedStatementParameters(PreparedStatement preparedStatement, List<Object> parameters)
            throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            Object param = parameters.get(i);
            preparedStatement.setObject(i + 1, param);
        }
    }

    /**
     * Retrieves a single value from the database.
     *
     * @param query The SQL query to execute.
     * @return The single value as an Object.
     * @throws SQLException If a database access error occurs.
     */
    public Object getSingleValue(String query) throws SQLException {
        logger.info("Executing query to get single value: " + query);
        Object value = null;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                value = resultSet.getObject(1);
            }
        }

        logger.info("Retrieved value: " + value);
        return value;
    }

    /**
     * Checks if a record exists in the database.
     *
     * @param query The SQL query to check for existence.
     * @return True if the record exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean recordExists(String query) throws SQLException {
        logger.info("Checking if record exists with query: " + query);
        boolean exists = false;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            exists = resultSet.next();
        }

        logger.info("Record exists: " + exists);
        return exists;
    }

    /**
     * Executes a batch of SQL statements.
     *
     * @param sqlStatements A list of SQL statements to execute.
     * @return An array of update counts containing one element for each command in the batch.
     * @throws SQLException If a database access error occurs.
     */
    public int[] executeBatch(List<String> sqlStatements) throws SQLException {
        logger.info("Executing batch of SQL statements");
        int[] updateCounts;

        try (Statement statement = connection.createStatement()) {

            for (String sql : sqlStatements) {
                statement.addBatch(sql);
            }

            updateCounts = statement.executeBatch();
        }

        logger.info("Batch executed successfully");
        return updateCounts;
    }

    /**
     * Executes a callable statement (stored procedure or function).
     *
     * @param sql        The SQL for the callable statement.
     * @param parameters The parameters to set in the statement.
     * @throws SQLException If a database access error occurs.
     */
    public void executeCallableStatement(String sql, List<Object> parameters) throws SQLException {
        logger.info("Executing callable statement: " + sql);

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {

            setCallableStatementParameters(callableStatement, parameters);

            callableStatement.execute();
        }

        logger.info("Callable statement executed successfully");
    }

    /**
     * Sets the parameters for a CallableStatement.
     *
     * @param callableStatement The CallableStatement object.
     * @param parameters        The list of parameters.
     * @throws SQLException If a database access error occurs.
     */
    private void setCallableStatementParameters(CallableStatement callableStatement, List<Object> parameters)
            throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            Object param = parameters.get(i);
            callableStatement.setObject(i + 1, param);
        }
    }

    /**
     * Gets the database connection.
     *
     * @return The Connection object.
     */
    public Connection getConnection() {
        return this.connection;
    }
}
