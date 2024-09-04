import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class MySQLExample {
    public static void main(String[] args) {
        // 1. Establish a connection to the MySQL database
        String url = "jdbc:mysql://localhost:3306/mydatabase"; // Replace with your database URL
        String user = "root"; // Replace with your MySQL username
        String password = "password"; // Replace with your MySQL password

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);

            // 2. Create a statement object to send SQL queries to the database
            statement = connection.createStatement();

            // 3. Execute a SQL query and retrieve the result set
            String query = "SELECT * FROM users"; // Replace with your query
            resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
