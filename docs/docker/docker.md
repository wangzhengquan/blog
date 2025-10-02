

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
## mount
```
docker run -it --mount type=bind,src="$(pwd)",target=/src ubuntu bash
```

## download
**docker for macos catalina**
- [version=4.9.1 release-date=2022-06-16](https://desktop.docker.com/mac/main/amd64/81317/Docker.dmg)

 **download specific version**
Find in [release-notes](https://github.com/docker/docs/blob/51b3996c903f474aa68b32cfc74370e1cd2c982a/content/desktop/release-notes.md) the build_path of the specific version and substitute for {build_path} in "https://desktop.docker.com/mac/main/amd64/{build_path}/Docker.dmg" to get an download link.

 [direct download links](https://gist.github.com/kupietools/2f9f085228d765da579f0f0702bec33c)
 
## play-with-docker
https://labs.play-with-docker.com
https://training.play-with-docker.com/

## References
- [Get started](https://docs.docker.com/get-started/workshop/02_our_app/)
- [Docker for beginners](https://github.com/docker/labs/blob/master/beginner/readme.md)
- [Change Docker Desktop settings on Mac](https://docs.docker.com/desktop/settings/mac/)  
- [Docker commandline](https://docs.docker.com/engine/reference/commandline/run/) 
- [Docker Cloud](https://hub.docker.com/)  
- [Dockerfile](https://docs.docker.com/reference/dockerfile/)
- [kernel namespaces and cgroups](https://medium.com/@saschagrunert/demystifying-containers-part-i-kernel-space-2c53d6979504)

