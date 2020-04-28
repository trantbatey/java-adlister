import com.codeup.adlister.dao.Config;

public class DaoFactory {
    private static Ads adsDao;
    private static Config conf;

    public static Ads getAdsDao() {
        if (adsDao == null) {
            conf = new Config();
            adsDao = new MySQLAdsDao(conf);
        }
        return adsDao;
    }
}
