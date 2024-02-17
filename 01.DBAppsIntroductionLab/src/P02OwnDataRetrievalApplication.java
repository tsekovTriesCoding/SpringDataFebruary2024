import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class P02OwnDataRetrievalApplication {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username default (root): ");
        String user = sc.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.println();

        System.out.print("Enter password default (empty):");
        String password = sc.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo", props);

        PreparedStatement stmt =
                connection.prepareStatement("SELECT u.first_name, u.last_name, " +
                        "(SELECT COUNT(*) FROM users_games WHERE user_id = u.id) AS games_count\n" +
                        "FROM users AS u\n" +
                        "WHERE user_name = ?");

        String username = sc.nextLine();
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            System.out.printf("User: %s%n%s %s has played %d games", username, rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("games_count"));
        } else {
            System.out.println("No such user exists");
        }
        connection.close();

    }

}