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
The `docker build` command is quite simple. it takes an optional tag name with the `-t` flag, and the location of the directory containing the `Dockerfile`. the `.` indicates the current directory:
```bash
docker build -t wangzhengquan/dev-in-ubuntu:1.0 .
```

## Start an  container with that image

Now that we have an image, let’s run the container with that image.

```bash
docker run --name mydev  -v /Users/wzq:/home/wzq -w /home/wzq -i -t wangzhengquan/dev-in-ubuntu:1.0 bash
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

If you encounter a login error  in macos, try to remove `~/.docker/config.json` file and relogin.
 
Now all you have to do is:
```bash
docker push wangzhengquan/dev-in-ubuntu:1.0
```



