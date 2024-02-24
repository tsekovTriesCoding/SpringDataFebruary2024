import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P03GetMinionNames {
    public static final String GET_ALL_MINIONS_NAMES_AND_AGE_BY_VILLAIN_ID = "SELECT m.name, m.age FROM minions AS m" +
            " JOIN minions_villains AS mv ON m.id = mv.minion_id" +
            " JOIN villains AS v ON mv.villain_id = v.id" +
            " WHERE v.id = ?";
    public static final String GET_VILLAIN_NAME_BY_ID = "SELECT name FROM villains WHERE id = ?";
    public static final String VILLAIN_DOES_NOT_EXIST = "No villain with ID %d exists in the database.";
    public static final String VILLAIN_FORMAT = "Villain: %s%n";
    public static final String MINION_FORMAT = "%d. %s %d%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        int villainId = new Scanner(System.in).nextInt();

        PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_NAME_BY_ID);

        villainStatement.setInt(1, villainId);

        ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.printf(VILLAIN_DOES_NOT_EXIST, villainId);
            connection.close();
            return;
        }

        String villainName = villainSet.getString(Constants.COLUMN_LABEL_NAME);

        System.out.printf(VILLAIN_FORMAT, villainName);

        PreparedStatement minionsStatement = connection.prepareStatement(GET_ALL_MINIONS_NAMES_AND_AGE_BY_VILLAIN_ID);

        minionsStatement.setInt(1, villainId);

        ResultSet minionsSet = minionsStatement.executeQuery();

        for (int i = 1; minionsSet.next(); i++) {
            String minionName = minionsSet.getString(Constants.COLUMN_LABEL_NAME);
            int minionAge = minionsSet.getInt(Constants.COLUMN_LABEL_AGE);
            System.out.printf(MINION_FORMAT, i, minionName, minionAge);
        }

        connection.close();
    }
}
