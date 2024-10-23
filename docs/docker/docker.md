## Editing source code in an IDE on the host while running and testing the code in a container.

创建一个ubuntu容器，并把本地工作目录挂载到容器中

```bash
docker run --name mydev  -v /Users/wzq:/home/wzq -w /home/wzq -i -t wangzhengquan/dev-in-ubuntu:1.0 bash
```

现在宿主机和容器就可以共享同一个目录下的文件，可以用宿主机上的编辑工具编写代码并保存，然后在容器里编译并运行代码。

如果关闭了，下一次再次运行这个容器

```bash
docker start -i mydev
```

在已经运行起来的容器中再打开一个bash

```bash
docker exec -it mydev bash 
```

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

## Managing volumes
The following commands will be helpful to manage volumes:

- `docker volume ls` : list all volumes
- `docker volume rm <volume-name-or-id>` : remove a volume (only works when the volume is not attached to any containers)
- `docker volume prune` : remove all unused (unattached) volumes

## docker port
```bash
docker port
```

## remove all container
```
docker rm -f $(docker ps -aq)
```
## remove all images
```
docker rmi $(docker images -a -q)
```

## Dockerfile
[Dockerfile][6] is a simple way to automate the image creation process.
```
FROM ubuntu:22.04
CMD bash

# Install Ubuntu packages.
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get -y update && \
    apt-get -y install \
      build-essential \
      clang-12 \
      clang-format-12 \
      clang-tidy-12 \
      cmake \
      doxygen \
      git \
      g++-12 \
      gdb \
      pkg-config \
      zlib1g-dev \
      vim

```

## Build the image
The `docker build` command is quite simple - it takes an optional tag name with the `-t` flag, and the location of the directory containing the `Dockerfile` - the `.` indicates the current directory:
```bash
docker build -t wangzhengquan/dev-in-ubuntu:1.0 .
```

## Push your image
Now that you've created and tested your image, you can push it to [Docker Cloud][4].
First you have to login to your Docker Cloud account, to do that:
```bash
docker login
```
Or
```bash
docker login -u wangzhengquan
```
Enter `YOUR_USERNAME` and `password` when prompted.
 
Now all you have to do is:
```bash
docker push wangzhengquan/dev-in-ubuntu:1.0
```


## login error in macos
If you encounter a login error, try to remove '~/.docker/config.json' file and relogin.
```bash
rm -rf ~/.docker/config.json
```

## play-with-docker
https://labs.play-with-docker.com
https://training.play-with-docker.com/


## mount
```
docker run -it --mount type=bind,src="$(pwd)",target=/src ubuntu bash
```

## download
docker for macos catalina: https://desktop.docker.com/mac/main/amd64/93002/Docker.dmg

## References
- [Get started][1] 
- [Docker for beginners][2] 
- [Change Docker Desktop settings on Mac][3]  
- [Docker commandline][4]  
- [Docker Cloud][5]  
- [Dockerfile][6]

[2]: https://docs.docker.com/get-started/
[1]: https://github.com/docker/labs/blob/master/beginner/readme.md
[3]: https://docs.docker.com/desktop/settings/mac/
[4]: https://docs.docker.com/engine/reference/commandline/run/
[5]: https://hub.docker.com/
[6]: https://docs.docker.com/reference/dockerfile/



[kernel namespaces and cgroups]:(https://medium.com/@saschagrunert/demystifying-containers-part-i-kernel-space-2c53d6979504)

