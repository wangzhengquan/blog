## 设置共享文件夹
- 查看共享目录名称
```
vmware-hgfsclient   
```

- 挂载到对应目录 
```
vmhgfs-fuse .host:共享目录名称 /mnt/目录名称
```