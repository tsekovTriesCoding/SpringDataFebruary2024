import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class P02GetVillainsNames {
    private static final String GET_VILLAINS_NAMES = "SELECT" +
            " v.name, COUNT(DISTINCT mv.minion_id) AS minions_count" +
            " FROM villains AS v" +
            " JOIN minions_villains AS mv ON v.id = mv.villain_id" +
            " GROUP BY v.id" +
            " HAVING `minions_count` > ?" +
            " ORDER BY `minions_count` DESC";

    private static final String COLUMN_LABEL_MINION_COUNT = "minions_count";
    private static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        PreparedStatement statement = connection.prepareStatement(GET_VILLAINS_NAMES);

        statement.setInt(1, 15);

        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            final String villainName = resultSet.getString(Constants.COLUMN_LABEL_NAME);
            final int minionsCount = resultSet.getInt(COLUMN_LABEL_MINION_COUNT);

            System.out.printf(PRINT_FORMAT, villainName, minionsCount);
        }

        connection.close();
    }
}