
假设远程终端的IP地址为 “192.168.83.11”

## 1. 生成私钥文件

客户端终端下输入以下命令
```bash
ssh-keygen  -N "" -t rsa -f ~/.ssh/id_rsa
```
每次执行 ssh-keygen -t rsa 产生的私钥文件都会不同

如果文件 "~/.ssh/id_rsa" 存在，会提示是否覆盖该文件，此时可选择"n"不覆盖该文件而使用已有的id_rsa文件。如果选择"y"则会重新生成  "~/.ssh/id_rsa" 文件。接下来会提示输入passphrase，回车确定使用空的passphrase，再次回车确认（这里也可以输出passphrase，相当于ssh时登录的密码）。然后会重新生成id_rsa文件和id_rsa.pub文件.

## 2.将本地的public key 拷贝到远程终端上
```
scp ~/.ssh/id_rsa.pub wzq@192.168.83.11:~/Downloads
```
## 3.将本地的public key追加到远程终端authorized_keys文件中
```
cat ~/Downloads/id_rsa.pub >> ~/.ssh/authorized_keys
```



Alternatively, Step 2 and 3 can be replaced with one step as following.
```
ssh-copy-id -i ~/.ssh/id_rsa.pub wzq@192.168.83.11
```

## 4.测试是否配置生效
```
ssh wzq@192.168.83.11
```
