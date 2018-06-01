import java.sql.*
import groovy.sql.Sql

class syncOnlDB {
    static List keys = ['firstname', 'lastname', 'contactno', 'email', 'city', 'state', 'zip']

    private static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"))

        String username = dbUri.getUserInfo().split(":")[0]
        String password = dbUri.getUserInfo().split(":")[1]
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require"

        return DriverManager.getConnection(dbUrl, username, password)
    }

    private static boolean compareRecord(row1, row2) {
        if (row1 == null || row2 == null) {
            return false
        }
        for (key in keys) {
            if (row1[key] != row2[key]) {
                return false
            }
        }
        return true
    }

    void sync() {
        //remote Sql instance
        Connection heroku_db = getConnection()
        def sqlRemote = new Sql(heroku_db)

        //local Sql instance
        def add = new dbAdder()
        def sqlLocal = add.sqlInstance()

        //use the two instances
        sqlLocal.eachRow("SELECT * FROM EMPLOYEES") { row ->
            if (row['REMOTEID'] == null) {
                sqlRemote.execute """
                                    INSERT INTO employees (localid, firstname, lastname, contactno, email, city, state, zip)
                                    VALUES (${row.EMPLOYEEID}, ${row.FIRSTNAME}, ${row.LASTNAME}, ${row.CONTACTNO}, ${row.EMAIL},
                                            ${row.CITY}, ${row.STATE}, ${row.ZIP});
                                """
                def remoteID = sqlRemote.firstRow("SELECT max(employeeid) FROM employees").max
                sqlLocal.execute """
                                    UPDATE EMPLOYEES SET REMOTEID = ${remoteID} WHERE EMPLOYEEID = ${row.EMPLOYEEID};
                                """
            } else {
                def remoteRow = sqlRemote.firstRow("SELECT * FROM employees WHERE employeeid = ${row.REMOTEID}")
                if (!compareRecord(remoteRow, row)) {
                    sqlRemote.execute"""
                                         UPDATE employees SET firstname = ${row.FIRSTNAME}, lastname = ${row.LASTNAME}, 
                                            contactno = ${row.CONTACTNO}, email = ${row.EMAIL}, city = ${row.CITY}, 
                                            state = ${row.STATE}, zip = ${row.ZIP} WHERE employeeid = ${row.REMOTEID};
                                         """
                }
            }
        }

        sqlRemote.eachRow("SELECT * FROM EMPLOYEES") { row ->
            def corresRow = sqlLocal.firstRow("SELECT * FROM employees WHERE EMPLOYEEID = ${row.localid}")
            if (!corresRow) {
                sqlRemote.execute "DELETE FROM employees WHERE employeeid = ${row.employeeid}"
            }
        }

        sqlRemote.close()
        sqlLocal.close()
    }

    static void main(String[] args) {


    }
}