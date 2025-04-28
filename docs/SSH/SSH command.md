## SSH Login
```bash
ssh  wzq@192.168.1.5
```
## scp (Secure Copy)
Sometimes, you may want to get individual files or entire folders from the Hive machines onto your local system, or vice versa. You can do this by using scp:
```bash
scp <source> <destination>
```
For example, If I wanted to copy from my local machine to a Remote machine, I would use:
```bash
scp ~/Downloads/example.txt wzq@192.168.1.5:~/some_folder/
```
scp by default only works with files. To copy folders, you need to tell scp to “recursively” copy the folder and all its contents, which you can do with the -r flag:

```bash
scp -rp ~/Downloads wzq@192.168.1.5:~/some_folder/
```

## SSH tunneling (local port forwarding)
This command is used to create a local port forwarding (also known as SSH tunneling) between your local machine and a remote server (cloud instance) using the SSH protocol.
```bash
ssh -L 5000:localhost:5000 <remote_user>@<remote_host>
```

or
```bash
ssh -L 5900:127.0.0.1:5900 -C -N -l remote_user remote_host
```

Here’s what this ssh command’s options mean:

- The `-L` switch specifies the port bindings. In this case you’re binding port 5900 of the remote connection to port 5900 on your local machine.
- The `-C` switch enables compression to help minimize resource consumption and speed things up.
- The `-N` switch tells ssh that you don’t want to execute a remote command.
- The `-l` switch specifies the remote login name.  