import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAdsDao implements Ads {

    private Connection connection;
    private static final String baseInsert = "INSERT INTO ads (user_id, title, description) VALUES ";

    public MySQLAdsDao(Config config) {

        // The driver MUST BE registered with the DriverManager
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName(config.getDriver()).getConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        // After the driver has been registered with the DriverManager, you can obtain a Connection instance that
        // is connected to a particular database
        try {
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUsername(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    @Override
    public List<Ad> all() {

        ArrayList<Ad> adsList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM ads";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                Ad ad = new Ad(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("title"),
                        rs.getString("description")
                        );
                adsList.add(ad);
            }

        } catch (SQLException e) {
            System.err.println("Error reading Ads data.");
        }
        if (adsList.isEmpty()) {
            generateAds();
        }
        return adsList;
    }

    @Override
    public Long insert(Ad ad) {
        try {
            Statement statement = connection.createStatement();
            String cmd = baseInsert + "(" + ad.getUserId() + ", '"
                    + ad.getTitle() + "', '" + ad.getDescription() + "');";
            statement.executeUpdate(cmd);
        } catch (SQLException e) {
            System.err.println("Error inserting Ads data\n" +
                    "user_id: " + ad.getUserId() + "\n" +
                    "title:   " + ad.getTitle() + "\n" +
                    "Description: \n" +
                    ad.getDescription() + "\n");
        }
        return null;
    }


    private void generateAds() {
        insert(new Ad(
                1,
                1,
                "playstation for sale",
                "This is a slightly used playstation"
        ));
        insert(new Ad(
                2,
                1,
                "Super Nintendo",
                "Get your game on with this old-school classic!"
        ));
        insert(new Ad(
                3,
                2,
                "Junior Java Developer Position",
                "Minimum 7 years of experience required. You will be working in the scripting language for Java, JavaScript"
        ));
        insert(new Ad(
                4,
                2,
                "JavaScript Developer needed",
                "Must have strong Java skills"
        ));
    }
}
