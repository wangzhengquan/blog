## New branch
```
git checkout -b lab2 origin/lab2
```
The git checkout -b command shown above actually does two things: it first creates a local branch lab2 that is based on the origin/lab2 branch provided by the course staff, and second, it changes the contents of your lab directory to reflect the files stored on the lab2 branch. 

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
git diff oldcheckpoint:filename newcheckpoint:filename
```
