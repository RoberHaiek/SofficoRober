import java.sql.*;
import java.util.logging.Logger;

public class MySQLAccess {

    private static MySQLAccess instance;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private static final Logger logger = Logger.getLogger(MySQLAccess.class.getName());

    private MySQLAccess() {}

    public static MySQLAccess getInstance() {
        if (instance == null) {
            instance = new MySQLAccess();
        }
        return instance;
    }

    public synchronized void storeShortText(ShortText shortText){
        try {
            preparedStatement = connect
                    .prepareStatement("insert into  orchestra.short_texts values (?, ?, ?)");
            preparedStatement.setString(1, shortText.getMatnr());
            preparedStatement.setString(2, shortText.getLang());
            preparedStatement.setString(3, shortText.getText());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            logger.severe(new MessageCode.DBExceptionBuilder()
                    .withTableName("short_texts")
                    .withAction("insert")
                    .withErrorMessage(e.getMessage())
                    .build().getMessage());
        }
    }

    public synchronized void storeMaterial(Material material) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName(Constants.DRIVER_NAME);
            // Set up the connection with the DB
            connect = DriverManager
                    .getConnection(Constants.DATABASE_URL);

            statement = connect.createStatement();
            if(isExists(material.getMatNr())) {
                preparedStatement = connect.prepareStatement("update orchestra.materials set price = ? where matnr = ?");
                preparedStatement.setDouble(1, material.getPrice());
                preparedStatement.setString(2, material.getMatNr());
            } else{
                preparedStatement = connect
                        .prepareStatement("insert into  orchestra.materials values (?, ?)");
                preparedStatement.setString(1, material.getMatNr());
                preparedStatement.setDouble(2, material.getPrice());
            }
            preparedStatement.executeUpdate();

            for (ShortText shortText : material.getShortText()) {
                storeShortText(shortText);
            }

            logger.info(new MessageCode.StoredSuccessfully()
                    .withMaterialName(material.getMatNr())
                    .withTableName("orchestra.materials")
                    .build().getMessage());

        } catch (Exception e) {
            logger.severe(new MessageCode.DBExceptionBuilder()
                    .withTableName("materials")
                    .withAction("insert")
                    .withErrorMessage(e.getMessage())
                    .build().getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            close();
        }
    }

    private boolean isExists(String matnr) throws SQLException {
        PreparedStatement checkStmt = connect.prepareStatement("SELECT COUNT(*) FROM orchestra.materials WHERE matnr = ?");
        checkStmt.setString(1, matnr);
        ResultSet resultSet = checkStmt.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) > 0;
    }

    private void close() {
        try {
            if (statement != null)
                statement.close();
            if (connect != null)
                connect.close();
        } catch (Exception e) {
            logger.severe(new MessageCode.ClosingExceptionBuilder()
                    .withErrorMessage(e.getMessage())
                    .build().getMessage());
            e.printStackTrace();
        }
    }

}
