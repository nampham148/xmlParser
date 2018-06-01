<b>groovy main.groovy</b>

<h3> Dependencies:</h3>
<p> Heroku: https://devcenter.heroku.com/articles/heroku-cli </p>
<p> Postgresql: https://jdbc.postgresql.org/download/postgresql-42.2.2.jar</p>

<h3>Set up environment variables:</h3>
<p>export CLASSPATH=$CLASSPATH:[file path to postgresql jar file]</p>
<p>export DATABASE_URL=$(heroku config:get DATABASE_URL -a nampham)</p>

<h3>Use CLI to interact with remote db:</h3>
<p>heroku pg:psql -a nampham</p>
