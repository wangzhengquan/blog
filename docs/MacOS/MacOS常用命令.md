
## 网络

 ```
 netstat -f inet -n -p TCP UD
 ```

## 查看端口占用

```bash
lsof -i:8080

kill  $(lsof -t -i:8080)

# 查找并关闭指定端口号的进程
lsof -i:8000 -i:5000 | awk '{if(NR>1)print $2}' | uniq | xargs kill
netstat -anvp tcp | grep 8000 | awk '{print $9}'

```

## 输出到剪切版
```
echo hello | pbcopy
```

## Empty Terminal on Mac using keyboard shortcut
```
⌘ + K 
```