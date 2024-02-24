import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class P08IncreaseMinionsAge {
    private static final String UPDATE_MINION_BY_ID = "UPDATE minions" +
            " SET age = age + 1, name = LOWER(name)" +
            " WHERE id = ?";
    private static final String GET_ALL_MINIONS_NAME_AND_AGE = "SELECT name, age" +
            " FROM minions";
    private static final String MINION_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = Utils.getSQLConnection();

        int[] minionsId = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        ResultSet minionsSet = getMinionsSet(connection, minionsId);

        while (minionsSet.next()) {
            System.out.printf(MINION_FORMAT, minionsSet.getString(Constants.COLUMN_LABEL_NAME),
                    minionsSet.getInt(Constants.COLUMN_LABEL_AGE));
        }

        connection.close();
    }

    private static ResultSet getMinionsSet(Connection connection, int[] minionsId) throws SQLException {
        PreparedStatement updateMinionStatement = connection.prepareStatement(UPDATE_MINION_BY_ID);

        for (int id : minionsId) {
            updateMinionStatement.setInt(1, id);

            updateMinionStatement.executeUpdate();
        }

        PreparedStatement getMinionsStatement = connection.prepareStatement(GET_ALL_MINIONS_NAME_AND_AGE);
        return getMinionsStatement.executeQuery();
    }


}
