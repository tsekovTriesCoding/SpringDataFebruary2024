import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P04AddMinion {
    public static final String GET_TOWN_BY_NAME = "SELECT * FROM towns" +
            " WHERE name = ?";
    public static final String GET_VILLAIN_BY_NAME = "SELECT *" +
            " FROM villains" +
            " WHERE name = ?";
    public static final String ADDED_TOWN_TO_DATABASE = "Town %s was added to the database.%n";
    private static final String INSERT_INTO_TOWNS = "INSERT INTO TOWNS (name)" +
            " VALUE(?)";
    private static final String INSERT_VILLAIN = "INSERT INTO villains (name, evilness_factor)" +
            " VALUE(?, ?)";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String ADDED_VILLAIN_TO_DATABASE = "Villain %s was added to the database.%n";
    private static final String GET_LAST_MINION = "SELECT * FROM minions" +
            " ORDER BY id DESC" +
            " LIMIT 1";
    private static final String INSERT_INTO_MINIONS = "INSERT INTO minions(name, age, town_id)" +
            " VALUE (?, ? ,?)";
    private static final String COLUMN_LABEL_ID = "id";
    private static final String INSERT_INTO_MINIONS_VILLAINS =
            "INSERT INTO minions_villains (minion_id, villain_id)" +
                    " VALUE (?, ?)";
    private static final String PRINT_RESULT = "Successfully added %s to be minion of %s.";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String[] minionInfo = input.split(" ");
        String townName = minionInfo[3];

        final PreparedStatement townStatement = connection.prepareStatement(GET_TOWN_BY_NAME);
        townStatement.setString(1, townName);

        final ResultSet townSet = townStatement.executeQuery();

        insertMissingTown(townSet, connection, townName);

        String[] villainInfo = scanner.nextLine().split(": ");

        String villainName = villainInfo[1];

        final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_BY_NAME);

        villainStatement.setString(1, villainName);

        final ResultSet villainSet = villainStatement.executeQuery();

        insertMissingVillain(villainSet, connection, villainName);

        final PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_INTO_MINIONS);
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        final ResultSet newTownSet = townStatement.executeQuery();
        newTownSet.next();
        int townId = newTownSet.getInt(COLUMN_LABEL_ID);

        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setInt(3, townId);

        insertMinionStatement.executeUpdate();

        final PreparedStatement lastMinionStatement = connection.prepareStatement(GET_LAST_MINION);
        final ResultSet lastMinionSet = lastMinionStatement.executeQuery();
        lastMinionSet.next();

        int minionId = lastMinionSet.getInt(COLUMN_LABEL_ID);

        final PreparedStatement lastVillainStatement = connection.prepareStatement(GET_VILLAIN_BY_NAME);
        lastVillainStatement.setString(1, villainName);

        final ResultSet lastVillainSet = lastVillainStatement.executeQuery();
        lastVillainSet.next();
        int villainId = lastVillainSet.getInt(COLUMN_LABEL_ID);

        setMinionToHisMasterVillain(connection, minionId, villainId);

        System.out.printf(PRINT_RESULT, minionName, villainName);

        connection.close();
    }

    private static void setMinionToHisMasterVillain(Connection connection, int minionId, int villainId) throws SQLException {
        PreparedStatement insertIntoMinionsVillainsStatement = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);
        insertIntoMinionsVillainsStatement.setInt(1, minionId);
        insertIntoMinionsVillainsStatement.setInt(2, villainId);
        insertIntoMinionsVillainsStatement.executeUpdate();
    }

    private static void insertMissingVillain(ResultSet villainSet, Connection connection, String villainName) throws SQLException {
        if (!villainSet.next()) {
            PreparedStatement insertVillainStatement = connection.prepareStatement(INSERT_VILLAIN);
            insertVillainStatement.setString(1, villainName);
            insertVillainStatement.setString(2, EVILNESS_FACTOR);

            insertVillainStatement.executeUpdate();

            System.out.printf(ADDED_VILLAIN_TO_DATABASE, villainName);
        }
    }

    private static void insertMissingTown(ResultSet townSet, Connection connection, String townName) throws SQLException {
        if (!townSet.next()) {
            PreparedStatement insertTownStatement = connection.prepareStatement(INSERT_INTO_TOWNS);
            insertTownStatement.setString(1, townName);

            insertTownStatement.executeUpdate();

            System.out.printf(ADDED_TOWN_TO_DATABASE, townName);
        }
    }
}
