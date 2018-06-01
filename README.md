groovy main.groovy

Dependencies:
Heroku: https://devcenter.heroku.com/articles/heroku-cli
Postgresql: https://jdbc.postgresql.org/download/postgresql-42.2.2.jar

Set up environment variables:
export CLASSPATH=$CLASSPATH:[file path to postgresql jar file]
export DATABASE_URL=$(heroku config:get DATABASE_URL -a nampham)

Use CLI to interact with remote db:
heroku pg:psql -a nampham
