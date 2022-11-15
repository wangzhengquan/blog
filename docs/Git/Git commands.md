## How to "merge" specific files from another branch
```bash
git checkout branch file
```
or
```bash
git checkout --patch branch file
```

## 查看谁修改的代码
```
git blame controllers/admin/contract.js 
```

## 比较两个提交点的文件
```
git diff 83f5ca6637280eecbb654eea1b78abef27e1c85c:filename 07d03e76072b0533e147425c8d259eef3cb0b44f:filename
```
