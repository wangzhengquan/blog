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

## Dockerfile

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


## References
- [Docker for beginners][1] 
- [Change Docker Desktop settings on Mac][2]  
- [Docker commandline][3]  
- [Docker Cloud][4]  

[1]: https://github.com/docker/labs/blob/master/beginner/readme.md
[2]: https://docs.docker.com/desktop/settings/mac/
[3]: https://docs.docker.com/engine/reference/commandline/run/
[4]: https://hub.docker.com/
