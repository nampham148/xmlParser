<p>groovy main.groovy</p>

<p> Dependencies:</p>
<p> Heroku: https://devcenter.heroku.com/articles/heroku-cli </p>
<p> Postgresql: https://jdbc.postgresql.org/download/postgresql-42.2.2.jar</p>

<p>Set up environment variables:</p>
<p>export CLASSPATH=$CLASSPATH:[file path to postgresql jar file]</p>
<p>export DATABASE_URL=$(heroku config:get DATABASE_URL -a nampham)</p>

<p>Use CLI to interact with remote db:</p>
<p>heroku pg:psql -a nampham</p>
