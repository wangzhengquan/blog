## log in to PostgreSQL with the `psql`
```bash
psql -U postgres
```

## Create a database:
```sql
CREATE DATABASE mydb;
```

## List all databases:
```sql
\l
```

## Switch to the `mydb` Database
```sql
\c mydb
```

## You can verify that you are using the correct database by running:
```sql
SELECT current_database();
```

## show tables
```sql
\dt
```

## In the PostgreSQL command line, run the following to create a database table and insert two records:

```sql
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    description VARCHAR(100)
);
INSERT INTO tasks (description) VALUES ('Finish work'), ('Have fun');
```
## Verify the data is in the database by running the following in the PostgreSQL command line:
```sql
SELECT * FROM tasks;
```
## Exit out of the PostgreSQL shell by running the following command:
```sql
\q
```