import java.sql.Connection;
import java.sql.DriverManager;

public class MssqlTest
{
    //   <!-- connection-url>jdbc:microsoft:sqlserver://ipreohost:1433;DatabaseName=brs_tm3;lockTimeout=10000;loginTimeout=10;</connection-url -->
    // tm3wrk:arma98
    // tm3mp:mogul02
    public static void main(String[] args) throws Throwable
    {
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //String connectionUrl = "jdbc:microsoft:sqlserver://ipreohost:1433;DatabaseName=brs_tm3;";
        //String connectionUrl = "jdbc:microsoft:sqlserver://10.187.252.129:1433;DatabaseName=brs_tm3;";
        String connectionUrl = "jdbc:sqlserver://10.187.252.129:1433;DatabaseName=brs_tm3;";
        Connection con = DriverManager.getConnection(connectionUrl);
            System.out.println("hello");
    }
}
