import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class P07PrintAllMinionNames {
    private static final String GET_ALL_MINIONS_NAMES = "SELECT name FROM minions";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_MINIONS_NAMES);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> minionsNames = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString(Constants.COLUMN_LABEL_NAME);
            minionsNames.add(name);
        }

        int firstHalfIndex = 0;
        int secondHalfIndex = minionsNames.size() - 1;
        for (int i = 0; i < minionsNames.size() / 2; i++) {
            System.out.println(minionsNames.get(firstHalfIndex));
            System.out.println(minionsNames.get(secondHalfIndex));

            firstHalfIndex++;
            secondHalfIndex--;
        }

        if (firstHalfIndex == secondHalfIndex) {
            System.out.println(minionsNames.get(firstHalfIndex));
        }

    }
}
