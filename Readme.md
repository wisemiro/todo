
# TODO API-WEB

API service using maven, java and postgreSQL.

cd into the build folder and run

``` bash
docker-compose up
```

This will build a containers for the postgreSQL database, Wildfly server & PgAdmin. 

cd into the config folder, in the DatabaseBootstrap file. 
Change the postgres address to your container address.
i.e 
<pre>
jdbc:postgresql://[POSTGRES_CONTAINER_ADDRESS]:5432/todo
</pre>

Deploy the project to wildfly

```bash
make deploy
```
<br/><br/>

Access console dashboard at: http://0.0.0.0:9990/

<br/><br/>

HTTP requests urls

```bash
GET: http://0.0.0.0:8080/todo-app/todo/todo/

CREATE: http://0.0.0.0:8080/todo-app/todo/todo/create/

GET: http://0.0.0.0:8080/todo-app/todo/todo/one/?id={todo-id}

UPDATE: http://0.0.0.0:8080/todo-app/todo/todo/update/?id={todo-id}

DELETE: http://0.0.0.0:8080/todo-app/todo/todo/delete/?id={todo-id}
```
