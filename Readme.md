
# TODO API-WEB

API service using maven, java and postgreSQL.

cd into the build folder and run

``` bash
docker-compose up
```

This will build a container for the postgreSQL database. The datasource used.

cd to project root dir and run to install required dependencies.

```bash
make install
```

next, deploy the project to wildfly

```bash
make deploy

```

HTTP requests urls

```bash
GET: http://0.0.0.0:8080/todo-app/todo/todo/

CREATE: http://0.0.0.0:8080/todo-app/todo/todo/create/

GET: http://0.0.0.0:8080/todo-app/todo/todo/one/?id={todo-id}

UPDATE: http://0.0.0.0:8080/todo-app/todo/todo/update/?id={todo-id}

DELETE: http://0.0.0.0:8080/todo-app/todo/todo/delete/?id={todo-id}
```
