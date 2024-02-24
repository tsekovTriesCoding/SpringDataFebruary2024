import java.sql.*;
import java.util.Scanner;

public class P09IncreaseAgeStoredProcedure {
    private static final String GET_OLDER_PROCEDURE = "CALL usp_get_older (?)";
    private static final String GET_MINION_NAME_AND_AGE_BY_ID = "SELECT name, age FROM minions WHERE id = ?";
    private static final String MINION_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final int id = scanner.nextInt();

        final Connection connection = Utils.getSQLConnection();

        final CallableStatement getOlderStoredProcedureStatement = connection.prepareCall(GET_OLDER_PROCEDURE);
        getOlderStoredProcedureStatement.setInt(1, id);
        getOlderStoredProcedureStatement.execute();

        final PreparedStatement minionStatement = connection.prepareStatement(GET_MINION_NAME_AND_AGE_BY_ID);
        minionStatement.setInt(1, id);

        final ResultSet minionSet = minionStatement.executeQuery();
        minionSet.next();

        System.out.printf(MINION_FORMAT,
                minionSet.getString(Constants.COLUMN_LABEL_NAME),
                minionSet.getInt(Constants.COLUMN_LABEL_AGE));
    }
}
