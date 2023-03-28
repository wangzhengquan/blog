## Linux 安装包查找
[https://pkgs.org](https://pkgs.org)

## 系统硬件工具
### 查看各硬件的型号
```bash
lspci
```
### CPU
```bash
# 查看cup信息
cat /proc/cpuinfo  

# 查看各级cache大小
getconf -a | grep CACHE


sudo dmidecode  -t 4

# get thread or cpu core count 
nproc
```

### 磁盘

参考：[鸟哥的Linux私房菜-第七章](http://linux.vbird.org/linux_basic/0230filesystem.php#lsblk) 

列出系统所有的装置，包括未挂载的
```bash
lsblk -p

lsblk -o NAME,PARTTYPE,MOUNTPOINT
```

查看目前已挂载的装置/磁盘的剩余空间
```bash
# 查看目前挂载的装置/磁盘剩余空间
df -Th 
# 查看 /etc 所在磁盘的剩余空间
df -h /etc
```

查看文件占用大小
```bash
# 查看/home目录下各文件占用大小
du -sh /home/*
# 查看 /home目录占用大小
du -sh /home
```

查看磁盘分区文件系统类型
```bash
sudo parted /dev/sda print
```

旧有的MBR分区使用的“磁盘分区”命令
```bash
# 硬盘分区工具 fdisk, cfdisk, sfdisk, GParted
# 查看磁盘分区
fdisk -l
# 磁盘分区
fdisk /dev/sda
```

Create file system
```bash
# ext4 file system
mkfs -v -t ext4 /dev/<xxx>
# create swap partition file system
mkswap /dev/<yyy>
# enabled 
/sbin/swapon -v /dev/<yyy>
```

 检查和修复损坏的硬盘

```bash
fsck -C -f -t ext3 /dev/sda1
```

检查硬盘扇区有没有坏轨

```bash
badblocks -sv /dev/sda
```

观察文件系统

```bash
dumpe2fs 
```

查看硬盘信息 制造商序列号等

```bash
sudo hdparm -i /dev/sda
# or
sudo smartctl -a /dev/sda
```


### 制作启动硬盘

如果在linux上用 `lsblk -p` 查看USB设备，如果是macos要用`diskutil list`查看, 查找到我的USB设备是/dev/disk3， 然后用dd命令把系统安装镜像烧录到USB上

```bash
dd if=ubuntu-16.04.5-desktop-amd64.iso of=/dev/disk3
```

如果提示 “ Resource busy”, 在linux上用 `umount /dev/disk3`, 在macos上用`diskutil umount /dev/disk3` 取消挂载。

###  loop dev
```bash
dd if=/dev/zero of=./rootfs.ext3 bs=1M count=32
mkfs.ext3 rootfs.ext3
sudo mount -o loop rootfs.ext3  /test/
```

### 查看initramfs
```bash
#看一下 initramfs 裡面的內容有些啥資料？
$ lsinitrd /boot/initramfs-4.18.0-193.el8.x86_64.img

$ mkdir /dev/shm/initramfs
$ cd /dev/shm/initramfs
# 取得 initramfs 前面應該要去除的容量
$ cpio -i -d --no-absolute-filenames   -I /boot/initramfs-4.18.0-193.el8.x86_64.img
  198 blocks
# 這個重點就是在前面的字元佔了幾個 block 容量，每個 block 容量為 512bytes，
# 每一個 initramfs 檔案的前置字元容量都不相同，所以需要先找出來去除才行！

$ dd if=/boot/initramfs-4.18.0-193.el8.x86_64.img of=initramfs.gz bs=512 skip=198

$ file initramfs.gz
  initramfs.gz: gzip compressed data, from Unix, last modified: Mon May  4 17:56:47 2015,max compression

# 3. 從上面看到檔案是 gzip 壓縮檔，所以將它解壓縮後，再查閱一下檔案的類型！
$ gzip -d initramfs.gz

$ file initramfs
  initramfs: ASCII cpio archive (SVR4 with no CRC)

# 解開後又產生一個 cpio 檔案，得要將它用 cpio 的方法解開！加上不要絕對路徑的參數較保險！  
$ cpio -i -d -H newc --no-absolute-filenames < initramfs
```

## 系统信息

### 查看系统信息

Type any one of the following command to find os name and version in Linux:
```bash
cat /etc/os-release
lsb_release -a
hostnamectl
```

Type the following command to find Linux kernel version
```
uname -r
```

```bash
# 查看系统信息
cat /proc/version 
# 系统架构
arch
file /bin/ls 
# 依据long类型的位数判断安装系统的位数， 一般64位操作系统long类型是64位
get_conf LONG_BIT
```

## man -  Manual Pages
If you want the man page for a single program/command, you can run:
```bash
man command_name | less
```
Hit q to exit the man page and get back to your terminal prompt.

查找系统内跟passwd有关的说明
```bash
man -f passwd
```

If you want to search the man pages for a command that pertains to a keyword:
```bash
man -k single_keyword | less
```
This command will search the manual pages for a command with the keyword 'single_keyword'. Forget how to open files in Vim? You can search for 'editor' and get a list of all editor-related commands on your system.

### 系统进程
```bash
ps -f -o pid,ppid,tty,stat,wchan,args

# 查看7979 和 29825 进程
ps -p 7979 -p 29825 -o "pid ppid sid tty cmd"
# 杀掉kucker名字的进程
ps -ef | grep "kucker" | awk  '{print $2}' | xargs -i sudo kill -9 {}

```

## 网络
### 查询域名IP
```bash
dig +norecurse @a.root-servers.net any yahoo.com
dig +norecurse @e.gtld-servers.net any yahoo.com
dig +norecurse @ns1.yahoo.com any yahoo.com

nslookup yahoo.com
nslookup -type=NS yahoo.com
nslookup yahoo.com  ns2.yahoo.com
```

### netstat

```bash
netstat -a --inet
netstat -tlunp # The -tlnp options specify that netstat should display TCP connections (-t), show the local address and port numbers in numeric form (-n), and display the PID and name of the process that is occupying each port (-p).


# 查找并关闭指定端口的进程
netstat -tulnp | grep :${PORT} | awk  '{print $7}' | awk -F/  '{print $1}' | xargs -i kill  {}
netstat -tlnp | grep :${PORT} | awk '{print $7}' | cut -d'/' -f1 | xargs -i kill  {}
```

### tcpdump
```
tcpdump -t -N 'port 80'
```


## 文件

### 文件权限

```bash
# 改变所属群组  change group
chgrp [-R] 群组名 dirname/filename ...

# 改变档案拥有者 change owner
chown [-R] 账号名 档案戒目彔
chown [-R] 账号名:组名 档案戒目彔

# 改变权限
# 数字类型改变档案权限
chmod [-R] 740 档案目彔

# 符号类型改变档案权限
#  		|	u	|	+(加入) 	|	r	|
# chmod	|	g 	|	-(除去)	|	w	|	档案戒目彔
# 		|	o	|	=(设定)	|	x	|
# 		|	a	|			|		|
chmod u=rwx,go=rx .bashrc
chmod a-x .bashrc

```

### 文件管理
```bash
# 展示目录列表
ls -ld 目录
# 回到上次访问的目录
cd - 
# 进入wzq用户家目录
cd wzq~
```


### grep

替换或删除跨越多个目录的多个文件里的指定文本
```bash
# 替换当前目录下所有文件里的‘window’为‘linux’
grep -rl 'windows' ./ | xargs sed -i 's/windows/linux/g'
# Mac下sed命令的"-i"选项后面需要加字符串表示备份文件的后缀
grep -rl 'windows' ./ | xargs sed -i ''  's/windows/linux/g'

#删除出现‘windows’的行
grep -rl 'windows' ./ | xargs sed -i ''   '/windows/d'

```
统计某个词语在文件中出现的次数
```bash
# count the number of occurrence of a word in a text file
grep -o -i 'word' test.txt | wc -l
```

### find
```bash
find ./ -name "*nng*" -exec rm -rf {}

find /usr/{lib,libexec} -name \*.la -delete

find /usr -depth -name $(uname -m)-lfs-linux-gnu\* | xargs rm -rf

find $(1) -not -type d -and -not -type l -print0 | xargs -0r chmod $(FILE_MODE)
```

### diff
```bash
方法一：
diff -bur [oldDir] [newDir]
方法二：
rsync -rcnv [oldDir] [newDir]
```

Split the screen to two columns for comparing convenience
```bash
diff -y [oldDir] [newDir]
```

## sed
 [https://www.grymoire.com/Unix/Sed.html](https://www.grymoire.com/Unix/Sed.html)

## 压缩与打包
```bash
[dmtsai@study ~]$ tar [-z|-j|-J] [cv] [-f 待建立的新檔名] filename... <==打包與壓縮
[dmtsai@study ~]$ tar [-z|-j|-J] [tv] [-f 既有的 tar檔名]             <==察看檔名
[dmtsai@study ~]$ tar [-z|-j|-J] [xv] [-f 既有的 tar檔名] [-C 目錄]   <==解壓縮
選項與參數：
-c  ：建立打包檔案，可搭配 -v 來察看過程中被打包的檔名(filename)
-t  ：察看打包檔案的內容含有哪些檔名，重點在察看『檔名』就是了；
-x  ：解打包或解壓縮的功能，可以搭配 -C (大寫) 在特定目錄解開
      特別留意的是， -c, -t, -x 不可同時出現在一串指令列中。
-z  ：透過 gzip  的支援進行壓縮/解壓縮：此時檔名最好為 *.tar.gz
-j  ：透過 bzip2 的支援進行壓縮/解壓縮：此時檔名最好為 *.tar.bz2
-J  ：透過 xz    的支援進行壓縮/解壓縮：此時檔名最好為 *.tar.xz
      特別留意， -z, -j, -J 不可以同時出現在一串指令列中
-v  ：在壓縮/解壓縮的過程中，將正在處理的檔名顯示出來！
-f filename：-f 後面要立刻接要被處理的檔名！建議 -f 單獨寫一個選項囉！(比較不會忘記)
-C 目錄    ：這個選項用在解壓縮，若要在特定目錄解壓縮，可以使用這個選項。

其他後續練習會使用到的選項介紹：
-p(小寫) ：保留備份資料的原本權限與屬性，常用於備份(-c)重要的設定檔
-P(大寫) ：保留絕對路徑，亦即允許備份資料中含有根目錄存在之意；
--exclude=FILE：在壓縮的過程中，不要將 FILE 打包！ 
```

壓　縮：tar -jcv -f filename.tar.bz2 要被壓縮的檔案或目錄名稱
查　詢：tar -jtv -f filename.tar.bz2
解壓縮：tar -jxv -f filename.tar.bz2 -C 欲解壓縮的目錄


实例：
```bash
# 解压tar.bz2文件
tar xvfj ./linux-2.6.23.tar.bz2 
tar xvfJ ./linux-2.6.23.tar.xz
tar xvfz ./linux-2.6.23.tar.gz
tar -xf ./linux-2.6.23.tar.*


# 解压python-3.9.0-docs-html.tar.bz2到/usr/share/doc/python-3.9.0/html
tar --strip-components=1 \
--no-same-owner \
--no-same-permissions \
-C /usr/share/doc/python-3.9.0/html \
-xvf ../python-3.9.0-docs-html.tar.bz2

# --no-same-owner and --no-same-permissions
# Ensure the installed files have the correct ownership and permissions.
# Without these options, using tar will install the package files with the upstream creator's values.

# 当前目录压缩打包到/home/wzq/lfs/lfs-2.tar.xz
tar -cJpf /home/wzq/lfs/lfs-2.tar.xz .
# 解压导当前目录
tar -xpf $HOME/lfs-temp-tools-10.0-systemd.tar.xz
```


## User

### add user 

add the new user vivi
```bash
groupadd vivi
useradd -s /bin/bash -g vivi -m -d /home/vivi vivi
# useradd -s /bin/bash -g vivi -m -k /dev/null vivi
passwd vivi
```
The command adds an entry to the /etc/passwd, /etc/shadow, /etc/group and /etc/gshadow files.

Or, Simply by
```bash
useradd -s /bin/bash -m vivi
passwd vivi
```

Or, adduser is an interactive command-line tool available by default in most Linux distributions.
```bash
adduser vivi
```

[More about create user](https://linuxize.com/post/how-to-create-users-in-linux-using-the-useradd-command/)

### add user to sudoers 
Most Linux systems, including Ubuntu, have a user group for sudo users. To grant the new user elevated privileges, add them to the sudo group.
```bash
usermod -aG sudo vivi
```
The -aG option tells the system to append the user to the specified group. (The -a option is only used with G.)

Or
```bash
sudo adduser vivi sudo
```
[More about sudo](https://jumpcloud.com/blog/how-to-create-a-new-sudo-user-manage-sudo-access-on-ubuntu-20-04)

### delete user

```bash
userdel -r vivi

```

`-r` Remove Linux user account including home directory and mail spool

### Verify user information
```bash
lslogins vivi

id vivi
```

### View the groups a user belongs to
```bash
groups vivi
```

### Gain root shell
```bash
sudo -s
```
or
```bash
sudo -i
```



## Terminal
### CLI Shortcuts
* `[ctrl]+u` / `[ctrl]+k` 删除行内光标所在位置之前/后的内容
* `<ctrl> + a` `<ctrl> + e` will move the cursor to the beginning / end of the current line
* `alt-f` / `alt-b`  可以以单词为单位向前/后移动光标
* `ctrl-w` 删除你键入的最后一个单词
* `ctrl-l` 清屏
* `<ctrl> + r` will let you search through your recently used commands

你喜欢的话，可以执行 `set -o vi` 来使用 vi 风格的快捷键，而执行 `set -o emacs` 可以把它改回来。

### history
```bash
# number  ：執行第幾筆指令的意思；
!number
# command ：由最近的指令向前搜尋『指令串開頭為 command』的那個指令，並執行；
!command
# 執行上一個指令(相當於按↑按鍵後，按 Enter)
!!  

```

### 重定向
```bash
# 將指令的資料全部寫入名為 list 的檔案中
find /home -name .bashrc > list 2>&1 
```


## wget
```bash
wget --input-file=downloadListFile --continue --directory-prefix=target-directory
```
## curl
```bash
 
#-o, --output <file>: Write  output  to <file> instead of stdout.
curl https://github.com/ginuerzh/gost/releases/download/v2.11.1/gost-linux-amd64-2.11.1.gz --output gost.gz
 
# -O, --remote-name: Write output to a local file named like the remote file we get
curl -O https://github.com/ginuerzh/gost/releases/download/v2.11.1/gost-linux-amd64-2.11.1.gz
```



## 关机/重启

```bash
# 未保存的内存数据写入硬盘
sync
# 立刻关机，其中 now 相当亍时间为 0 的状态
shutdown -h now
# 系统在今天的 20:25 分会关机，若在 21:25 才下达此挃令，则隑天才关机
shutdown -h 20:25
# 系统再过十分钟后自劢关机
shutdown -h +10
# 系统立刻重新启劢
shutdown -r now
# 再过三十分钟系统会重新启劢，幵显示后面的讯息给所有在在线的使用者
shutdown -r +30 'The system will reboot'
# 仅发出警告，系统并不会关机啦!吓唬人!
shutdown -k now 'This system will reboot'
# 重新启劢，关机，断电
reboot, halt, poweroff
```

Reference:
>[ Linux system administration](https://developer.ibm.com/tutorials/l-lpic1-map/)


> https://github.com/jlevy/the-art-of-command-line/blob/master/README-zh.md#%E4%BB%85%E9%99%90-os-x-%E7%B3%BB%E7%BB%9F