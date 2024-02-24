import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P05ChangeTownNamesCasing {
    private static final String GET_TOWNS_BY_COUNTRY = "SELECT name" +
            " FROM towns" +
            " WHERE country = ?";

    public static final String SET_TOWN_NAME_TO_UPPERCASE_LETTERS = "UPDATE towns" +
            " SET name = UPPER(name)" +
            " WHERE country = ?";
    private static final String NOT_TOWNS_AFFECTED_FORMAT = "No town names were affected.";
    private static final String COUNT_OF_AFFECTED_TOWNS_FORMAT = "%d town names were affected.%n";

    public static void main(String[] args) throws SQLException {
        Connection sqlConnection = Utils.getSQLConnection();

        Scanner scanner = new Scanner(System.in);

        String country = scanner.nextLine();

        PreparedStatement preparedStatement = sqlConnection.prepareStatement(SET_TOWN_NAME_TO_UPPERCASE_LETTERS);
        preparedStatement.setString(1, country);
        int updatedTownsCount = preparedStatement.executeUpdate();

        if (updatedTownsCount == 0) {
            System.out.println(NOT_TOWNS_AFFECTED_FORMAT);
            sqlConnection.close();
            return;
        }

        System.out.printf(COUNT_OF_AFFECTED_TOWNS_FORMAT, updatedTownsCount);

        PreparedStatement selectAllTownsStatement = sqlConnection.prepareStatement(GET_TOWNS_BY_COUNTRY);
        selectAllTownsStatement.setString(1, country);
        ResultSet allTownsSet = selectAllTownsStatement.executeQuery();

        List<String> towns = new ArrayList<>();

        while (allTownsSet.next()) {
            towns.add(allTownsSet.getString(Constants.COLUMN_LABEL_NAME));
        }

        System.out.println(towns);

        sqlConnection.close();
    }
}
