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


## Delete all tables

### Method 1: The Easiest & Safest (Drop and Recreate the Database)

If your goal is to completely reset the database to a clean slate (no tables, no views, no functions, etc.), the best practice is to drop and recreate the entire database. This is cleaner and often faster than scripting the deletion of individual objects.

**You cannot drop the database you are currently connected to.** You must connect to a different database (like the default `postgres` database) to perform this operation.

1.  **Connect to a different database:**
    ```bash
    psql -U your_username -d postgres
    ```

2.  **Drop your target database:**
    ```sql
    DROP DATABASE my_database_name;
    ```
    If there are active connections, you may get an error. You can force the drop by first terminating all connections:
    ```sql
    -- WARNING: This will kick everyone out of the database
    SELECT pg_terminate_backend(pid)
    FROM pg_stat_activity
    WHERE datname = 'my_database_name';

    -- Then drop it
    DROP DATABASE my_database_name;
    ```

3.  **Recreate the database:**
    ```sql
    CREATE DATABASE my_database_name OWNER your_username;
    ```

---

### Method 2: The Scripting Method (Using `DROP TABLE`)

If you must delete all tables but leave the database and other objects (like custom types or functions) intact, you need to generate and run a script. You cannot simply run `DROP TABLE *`.

This script queries the system catalogs to get a list of all tables and then constructs a `DROP TABLE` command for each one.

#### A) Deleting All Tables in the `public` Schema (Most Common)

Connect to your target database using `psql` or your favorite SQL client and run the following command. This will delete all tables in the `public` schema.

```sql
DO $$ DECLARE
    r RECORD;
BEGIN
    -- Loop through all tables in the 'public' schema
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        -- Execute the DROP TABLE command with CASCADE
        EXECUTE 'DROP TABLE IF EXISTS public.' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;
```

**Explanation of the key parts:**

*   `DO $$ ... $$;` creates an "anonymous block" of procedural code that runs once.
*   `FOR r IN (...) LOOP ... END LOOP;` loops through the results of a query.
*   `SELECT tablename FROM pg_tables WHERE schemaname = 'public'` gets the names of all tables in the `public` schema.
*   `EXECUTE` runs a command that is constructed as a string.
*   `quote_ident(r.tablename)` is **very important**. It correctly handles table names that have spaces, capital letters, or special characters by wrapping them in double quotes.
*   `CASCADE` is the magic key. It automatically drops any objects that depend on the table (like foreign key constraints, views, etc.). Without `CASCADE`, your `DROP TABLE` commands would fail if tables are linked by foreign keys.
*   `IF EXISTS` prevents the script from failing if a table somehow gets deleted during the process.

#### B) Deleting All Tables in ALL Schemas (Except System Schemas)

If you have tables spread across multiple schemas, you can adapt the script to loop through all of them, excluding the system schemas (`pg_catalog`, `information_schema`).

```sql
DO $$ DECLARE
    r RECORD;
BEGIN
    -- Loop through all tables in all schemas (excluding system schemas)
    FOR r IN (
        SELECT table_schema, table_name
        FROM information_schema.tables
        WHERE table_schema NOT IN ('pg_catalog', 'information_schema')
        AND table_type = 'BASE TABLE'
    ) LOOP
        -- Execute the DROP TABLE command with CASCADE
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.table_schema) || '.' || quote_ident(r.table_name) || ' CASCADE';
    END LOOP;
END $$;
```
This version queries `information_schema.tables` which is the SQL standard way to get metadata, and it constructs the fully qualified name `schema_name.table_name`.

---

### Method 3: The `TRUNCATE` Alternative (Emptying Tables, Not Deleting)

If you want to **delete all the data** from the tables but **keep the table structures**, indexes, and constraints, you should use `TRUNCATE` instead of `DROP`.

This is much faster than `DELETE FROM ...` for large tables.

```sql
DO $$ DECLARE
    r RECORD;
BEGIN
    -- Loop through all tables in the 'public' schema
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        -- Execute the TRUNCATE command
        EXECUTE 'TRUNCATE TABLE public.' || quote_ident(r.tablename) || ' RESTART IDENTITY CASCADE';
    END LOOP;
END $$;
```

**Key `TRUNCATE` options:**

*   `RESTART IDENTITY`: This resets the sequences associated with identity columns (e.g., `SERIAL` or `IDENTITY`), so the next inserted row will start at 1 again.
*   `CASCADE`: This will also truncate tables that have foreign key references to the target tables.
     |