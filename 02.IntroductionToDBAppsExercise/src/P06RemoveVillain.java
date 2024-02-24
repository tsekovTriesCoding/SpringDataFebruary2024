import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06RemoveVillain {
    private static final String GET_VILLAIN_BY_ID =
            "SELECT * FROM villains" +
                    " WHERE id  = ?";
    private static final String MISSING_VILLAIN_MESSAGE = "No such villain was found";
    private static final String DELETE_FROM_MINIONS_VILLAINS_BY_VILLAIN_ID =
            "DELETE FROM minions_villains" +
                    " WHERE villain_id = ?";
    private static final String DELETE_VILLAIN_BY_ID =
            "DELETE FROM villains" +
                    " WHERE id = ?";
    private static final String DELETED_VILLAIN_FORMAT = "%s was deleted%n";
    private static final String DELETE_MINIONS_COUNT_FORMAT = "%d minions released";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int villainId = scanner.nextInt();

        Connection sqlConnection = Utils.getSQLConnection();
        PreparedStatement preparedStatement = sqlConnection.prepareStatement(GET_VILLAIN_BY_ID);
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println(MISSING_VILLAIN_MESSAGE);
            sqlConnection.close();
            return;
        }

        sqlConnection.setAutoCommit(false);

        try {
            PreparedStatement deleteMinionsStatement = sqlConnection.prepareStatement
                    (DELETE_FROM_MINIONS_VILLAINS_BY_VILLAIN_ID);

            PreparedStatement deleteVillainStatement = sqlConnection.prepareStatement(DELETE_VILLAIN_BY_ID);

            deleteMinionsStatement.setInt(1, villainId);
            int deletedMinionsCount = deleteMinionsStatement.executeUpdate();

            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();

            String villainName = resultSet.getString(Constants.COLUMN_LABEL_NAME);

            sqlConnection.commit();

            System.out.printf(DELETED_VILLAIN_FORMAT, villainName);
            System.out.printf(DELETE_MINIONS_COUNT_FORMAT, deletedMinionsCount);
        } catch (SQLException e) {
            e.printStackTrace();

            sqlConnection.rollback();
        }

        sqlConnection.close();
    }
}
