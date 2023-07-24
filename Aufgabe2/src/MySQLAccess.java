import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public void storeShortText(ShortText shortText) throws Exception {
        try {
            preparedStatement = connect
                    .prepareStatement("insert into  orchestra.short_text values (?, ?, ?)");
            preparedStatement.setString(1, shortText.getMatnr());
            preparedStatement.setString(2, shortText.getLang());
            preparedStatement.setString(3, shortText.getText());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    public void storeMaterial(Material material) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Set up the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/orchestra?"
                            + "user=ORCHESTRA&password=ORCHESTRA");

            statement = connect.createStatement();
            preparedStatement = connect
                    .prepareStatement("insert into  orchestra.material values (?, ?)");
            preparedStatement.setString(1, material.getMatNr());
            preparedStatement.setDouble(2, material.getPrice());
            preparedStatement.executeUpdate();

            for(ShortText shortText: material.getShortText()){
                storeShortText(shortText);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    private void close() {
        try {

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}