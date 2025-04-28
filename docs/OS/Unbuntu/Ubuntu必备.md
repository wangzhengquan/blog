
###  开发必备编译环境
```bash
# 编译环境
apt-get install build-essential make automake autoconf libtool-bin

apt-get install  git qemu-system-x86 vim-gnome gdb cgdb eclipse-cdt make diffutils exuberant-ctags tmux cscope meld qgit gitg gcc-multilib gcc-multilib g++-multilib
# 最小需要的安装包
sudo apt-get install build-essential libncurses-dev bison flex libssl-dev libelf-dev

# On 64-bit machines, you may need to install a 32-bit support library.
apt-get install gcc-multilib

# openssl安装
apt-get install openssl libssl-dev

apt-get install zlib1g-dev libsdl1.2-dev libesd0-dev 
 
```

 
### 设置笔记本合上盖子时不进入休眠
修改 Login Manager的配置文件。

```bash
sudo vim /etc/systemd/logind.conf
```
然后将其中的：

```bash
#HandleLidSwitch=suspend
```
改成：
```bash
HandleLidSwitch=ignore
```
然后重启服务：

```bash
sudo restart systemd-logind
# or 
service systemd-logind restart
# or 直接重启
sudo shutdown -r now
```

## 安装haroopad（Markdown编辑器）
[官网：http://pad.haroopress.com](http://pad.haroopress.com/)
```bash
$ wget https://bitbucket.org/rhiokim/haroopad-download/downloads/haroopad-v0.13.1-ia32.deb

$ sudo apt-get install gdebi

$ sudo gdebi haroopad-v0.13.1-ia32.deb
```

## 安装deb文件
```bash
#  安装
sudo  dpkg  -i   deb文件名
# 根据经验，通常情况下会报依赖关系的错误，我们可以使用以下的命令修复安装。
sudo  apt-get  install  -f
# 查看已安装
sudo  dpkg  -l
# 卸载
sudo dpkg  -r  软件名
```

## terminal 显示git分支
in file `~/.bashrc`
```bash
parse_git_branch() {                                                                                                                                                                                    
  git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/ (\1)/'
}   
   
PS1="${debian_chroot:+($debian_chroot)}\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\e[91m\]\$(parse_git_branch)\[\033[00m\]\$ "
#PS1="\u@\h \[\e[32m\]\w \[\e[91m\]\$(parse_git_branch)\[\e[00m\]$ "   

```


## ubuntu18更换国内源
```bash
cat >> /etc/apt/sources.list << "EOF"
deb http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse
EOF

apt-get update
apt-get upgrade
```


## 热点 (Hotspot)

## 设置热点为永久激活
修改热点配置以防止超时关闭
```bash
sudo nmcli connection modify Hotspot connection.autoconnect yes
```
## 禁用自动超时
检查是否有 connection.auth-retries 或类似超时参数，并将其设置为较大值
```sh
sudo nmcli connection modify Hotspot-1 connection.auth-retries 999
```

### 删除热点
#### 方法 1：通过命令行删除热点
1. 查看已存在的热点
```bash
nmcli connection show
```
2. 删除热点(替换 Hotspot 为你的热点名称或UUID)
```sh
sudo nmcli connection delete Hotspot
```
3. 重启 NetworkManager
```sh
sudo systemctl restart NetworkManager
```
#### 方法 2：手动删除配置文件
进入 NetworkManager 配置目录 `/etc/NetworkManager/system-connections/`, `ls`找到热点文件并删除，重启 NetworkManager。





