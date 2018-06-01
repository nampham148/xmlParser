@Grab('org.hsqldb:hsqldb:2.3.4')
@GrabConfig(systemClassLoader=true)

import groovy.sql.Sql

def url = "jdbc:hsqldb:../db/company"
def user = 'sa'
def password = ''
def driver = "org.hsqldb.jdbcDriver"
def sql = Sql.newInstance(url, user, password, driver)


//sql.execute "UPDATE EMPLOYEES SET EMAIL = 'helloworld@groovy.com' WHERE EMPLOYEEID = 10"

sql.eachRow("SELECT * FROM EMPLOYEES") {
    println it
}

sql.close()
