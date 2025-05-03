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

参考： https://docs.docker.com/get-started/docker-concepts/running-containers/sharing-local-files