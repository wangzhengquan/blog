## New branch
```bash
## 
git checkout -b <new-branch> <start-point>
## same as
git switch -c <new-branch> <start-point>

```

## 把某个文件恢复到某个提交点
```bash
git checkout <提交哈希值> -- <文件路径>
```
撤销恢复： 如果你不小心恢复了错误的文件或版本，并且还没有提交，你可以使用 `git restore <文件路径>` 来撤销工作目录的更改，或者使用 `git restore --staged <文件路径>` 来撤销暂存区的更改。

## 本地所有已追踪且已修改的但没有的提交的，都返回到原来的状态
```bash
# revert all local uncommitted changes (should be executed in repo root):
git checkout .
# revert uncommitted changes only to particular file or directory
git checkout dir | file
```

## 删除未追踪的文件
```
git clean -df
```	
- `-n` 不实际删除，只是进行演练，展示将要进行的操作，有哪些文件将要被删除。（可先使用该命令参数，然- 后再决定是否执行）
- `-f` 删除文件
- `-i` 显示将要删除的文件
- `-d` 递归删除目录及文件（未跟踪的）
- `-q` 仅显示错误，成功删除的文件不显示


## 放弃所有未提交的修改
```bash
# 先查看要放弃的修改的文件
git clean -ndf
# 确认无误后执行下面的命令
git checkout . && git clean -df
```


## commit的回退
```
# 返回到某个节点且不保留修改，已有的改动会丢失。
git reset --hard checkpoint 
# 返回到某个节点并保留修改。 已有的改动会保留在未提交中，git status或git diff可看。
git reset --soft checkpoint
```

## 比较两个提交点的文件
```bash
git diff oldcheckpoint:filename newcheckpoint:filename
```

## Mmerge specific files from another branch
```bash
git checkout checkpoint file
```
or
```bash
git checkout --patch checkpoint file
```


## git log
查看某个文件的提交历史
```bash
git log -- <文件路径>
```

beautfull log
```bash
git log --graph --decorate --oneline --simplify-by-decoration --all

git log --oneline --graph --decorate --all
```

说明：  
- `--decorate` 标记会让git log显示每个commit的引用(如:分支、tag等)
- `--oneline` 一行显示
- `--simplify-by-decoration` 只显示被branch或tag引用的commit
- `--all`表示显示所有的branch，这里也可以选择，比如我指向显示分支ABC的关系，则将--all替换为branchA branchB branchC


My two cents: I have two aliases I normally throw in my ~/.gitconfig file:
```bash
[alias]
lg1 = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(dim white)- %an%C(reset)%C(auto)%d%C(reset)' --all
lg2 = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold cyan)%aD%C(reset) %C(bold green)(%ar)%C(reset)%C(auto)%d%C(reset)%n''          %C(white)%s%C(reset) %C(dim white)- %an%C(reset)'
lg = lg1
```

then, you can
```bash
git lg
```

## 查看谁修改的代码
```
git blame controllers/admin/contract.js 
```

## Tag

```bash
# Creating tag
git tag -a v1.1 9fceb02 -m "hight performance，hight rpc "

git push origin v1.1
git push origin --tags

# Delete tag
git tag -d <tagname>
 git push origin --delete <tagname>
```

## References
> gitignore: https://github.com/github/gitignore/tree/main
>https://www.atlassian.com/git/tutorials/undoing-changes/git-reset
>https://www.atlassian.com/git/tutorials/
>https://git-scm.com/book/en/v2
>https://www.atlassian.com/git/tutorials/atlassian-git-cheatsheet
>http://goo.gl/cLBs3D
>http://think-like-a-git.net/
>https://eagain.net/articles/git-for-computer-scientists/
>https://mirrors.edge.kernel.org/pub/software/scm/git/docs/user-manual.html
>https://docs.github.com/en/get-started/using-git/about-git
