@Grab('org.hsqldb:hsqldb:2.2.8')
@GrabConfig(systemClassLoader=true)

import groovy.sql.Sql

def url = "jdbc:hsqldb:../db/company"
def user = 'sa'
def password = ''
def driver = "org.hsqldb.jdbcDriver"
def sql = Sql.newInstance(url, user, password, driver)

sql.eachRow("""SELECT * FROM EMPLOYEES """) {row ->
    println row
}

sql.close()
