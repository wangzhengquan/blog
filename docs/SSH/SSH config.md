对 ssh 设置做一些小优化可能是很有用的，例如这个 "~/.ssh/config" 文件包含了防止特定网络环境下连接断开、压缩数据、多通道等选项
```
TCPKeepAlive=yes
ServerAliveInterval=15
ServerAliveCountMax=6
Compression=yes
ControlMaster auto
ControlPath /tmp/%r@%h:%p
ControlPersist yes
```