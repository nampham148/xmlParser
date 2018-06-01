groovy dbAdder.groovy [filePath]

export CLASSPATH=$CLASSPATH:/home/nam/Documents/Rikkeisoft/learn_heroku_db/postgresql-42.2.2.jar
export DATABASE_URL=$(heroku config:get DATABASE_URL -a nampham)
heroku pg:psql -a nampham
