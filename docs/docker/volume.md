
## [postgresql in docker](https://docs.docker.com/get-started/docker-concepts/running-containers/persisting-container-data/)

1. Start a container using the Postgres image with the following command:
```bash
docker volume create postgres_data

docker run --name db -e POSTGRES_PASSWORD=123456 -d -p 5432:5432 -v postgres_data:/var/lib/postgresql/data postgres

```
This will start the database in the background, configure it with a password, and attach volume `postgres_data` to the directory `/var/lib/postgresql/data` where PostgreSQL will persist the database files.

2. Connect to the database by using the following command:

```bash
docker exec -ti db psql -U postgres
```
This command connects you to the PostgreSQL database running inside the `db` container using the `psql` client, with the username `postgres`.

3. In the PostgreSQL command line, run the following to create a database table and insert two records:

```sql
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    description VARCHAR(100)
);
INSERT INTO tasks (description) VALUES ('Finish work'), ('Have fun');
```
4. Verify the data is in the database by running the following in the PostgreSQL command line:
```sql
SELECT * FROM tasks;
```
5. Exit out of the PostgreSQL shell by running the following command:
```sql
\q
```
6. Stop and remove the database container. Remember that, even though the container has been deleted, the data is persisted in the postgres_data volume.
```bash
docker stop db
docker rm db
```
7. Start a new container by running the following command, attaching the same volume with the persisted data:
```bash
docker run --name=new-db -d -v postgres_data:/var/lib/postgresql/data postgres
```
You might have noticed that the POSTGRES_PASSWORD environment variable has been omitted. That’s because that variable is only used when bootstrapping a new database.

8. Verify the database still has the records by running the following command:
```bash
docker exec -ti new-db psql -U postgres -c "SELECT * FROM tasks"
```