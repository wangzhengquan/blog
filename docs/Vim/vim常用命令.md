
## 帮助
* vimtutor 查看使用说明
* `:help feature` 获取feature 的相关信息
* `:help doc-file-list`获取帮助文件的完整列表
* CONTROL+] 打开链接
* CONTROL+o 返回
* :q 退出帮助文档

## Mode 
 Vim is a modal editor. It has a normal mode, visual mode ,insert mode, and other modes.

## Undo/Redo

 * u: undo last change
 * Ctrl_R: Redo


## 浏览
* ctrl+d 下翻半屏 d=down
* ctrl+u 上翻半屏 u=up
* ctrl+b上翻一页 b=backward
* ctrl+f下翻一页 f=forward

* 滚一行 ctrl-e ctrl-y

* zz 让光标所在的行居屏幕中央
* zt 让光标所在的行居屏幕最上一行 t=top
* zb 让光标所在的行居屏幕最下一行 b=bottom


## 光标移动
* w 向前移动一个字符
* b 向后移动一个字符
* $ 移动到行尾
* 0或^  移动到行首
* gg 移动到文档头
* G 移动到文档尾
* `行号＋G` 跳转到某行，例如到第6行`6G`
* `:行号`, 跳转到某行， 例如到第6行 `:6`
* ( 或 { 移动到段落头
* ) 或 } 移动到段落尾
* e 移动到当前字的尾部
* ge移动到前一个字的尾部
* %  跳转到配对的符号上，配对符号例如: "", (), <>, {}
* [[  跳转到当前或者上一代码块(函数定义、类定义等)的开头去(但要求代码块中'{'必须单独占一行)
* ][  跳转到当前代码块(函数定义、类定义等)的结尾去(但要求代码块中'{'必须单独占一行)
* ]]  跳转到下一代码块(函数定义、类定义等)的开头去(但要求代码块中'{'必须单独占一行)
* [/  跳到注释开头（只对/* */注释有效）
* ]/ 跳到注释结尾（只对/* */注释有效）
* gD  跳转到当前文件内标识符首次出现的位置，可用于跳转到全部变量的定义处；查找局部变量时，要是多个函数中定义了该局部变量，使用gD进行查找，找到的变量定义可能不是你需要的
* gd 跳转到当前函数内标识符首次出现的位置，可用于跳转到局部变量的定义处
* `''`  跳转到光标上次停靠的地方, 是两个`'`, 而不是一个`"`


## 复制粘贴

复制=Yank, 删除=Delete,  粘贴=Put

* Yank(Y) 复制到通用缓冲区
* Delete（D）删除并放到通用缓冲区
* Put(P) 把通用缓冲区的内容插入的当前文本

例如：

* yy 复制当前行 
* n+yy 复制当前行和后面n-1行 
* gg + yG 复制全部 
* "ayy  复制到缓冲区a
* "ap 粘贴缓冲区a到文本 

> Delete命令的模式同Yank一样

这里介绍一下关于寄存器(registers)的操作。` “＋字母＋yy `把当前行复制到字母的命名寄存器, ` “＋字母＋p `把对应字母名字的寄存器的内容黏贴到当前文档。 更多关于寄存器的介绍参见`:help registers`

## 编辑

### 删除
d+要删除的位置， 例如：

* dw 删除到字的末尾
* daw 删除一个单词
* d3aw 删除3个单词
* dw 删除到字的尾部
* db 删除到字的开始
* dd 删除一行
* x 或 dl 删除单个字符
* d0 或 d^ 从当前位置删除到行的头部
* d$ 或 D 删除到行尾
* d) 或 d} 从当前位置删除到段落尾
* d( 或 d{ 从当前位置删除到段落头
* d/text 删除到text单词的下一次出现
* cw 删除到字的末尾并进入插入模式

插入模式下,

* ctrl+H 字符删除
* ctrl+U 行删除
* ctrl+W 字删除

 
### 对配对符号内的内容进行操作
配对符号包括`"", (), {}` 等

以下命令可以对符号内的内容进行操作。

* `ci+符号` 更改配对符号中的文本内容
* `di+符号` 删除配对符号中的文本内容
* `yi+符号` 复制配对符号中的文本内容
* `vi+符号` 选中这些配对符号中的文本内容

另外如果把上面的`i`改成`a`可以连配对符号一起操作。

例如，要删除双引号内`""`内的内容,  `111"222"333`， 将光标移到双引号内，按下`di" ` ,删除双引号内的内容，变成 `111""333`，若要连同双引号一同删除，则要按下` da" ` ,文本会修改为： 111333

### Select text inside a tag
Tag is the tag in html or xml, for example `<div> ... </div>`,
We can select a text within an html or xml tag by using visual selection `v` and text object `it` .

1. Go to normal mode by pressing ESC
2. Type vit from anywhere within the html or xml section
3. This will visually select all text inside the tag.


All other text objects can also be used to operate on the text inside the tag


* `vit` - select all text inside the tag
* `cit` - delete text inside the tag and place in insert mode
* `dit` - delete text inside the tag and remain in normal mode
* `cat` - delete around tag and place in insert mode
* `dat` - delete text around the tag and remain in normal mode

### 格式化

* `\==` 格式化当前行
* `n+==` 格式化当前和后面n-1行
* `gg=G` 或者 `=` 格式化全文

### 缩进

插入模式下：

* CTRL+T 右缩进
* CTRL+D 左缩进

命令模式下：

* `>`  增加缩进,`x>` 表示增加以下x行的缩进
* `<`  减少缩进,`x<` 表示减少以下x行的缩进

### 代码注释
** 方法一 **  
** 多行注释：**

进入命令行模式，按`ctrl + v`进入 visual block模式，然后按j或k选中多行，把需要注释的行标记起来，按大写字母I，再插入注释符，例如`//`，按esc键就会全部注释了。

** 取消多行注释：**

进入命令行模式，按·ctrl + v·进入 visual block模式，按字母l横向选中列的个数，例如 ·//· 需要选中2列，按字母j，或	者k选中注释符号，按d键就可删除注释符。

** 方法二 替换命令 **
** 插入注释 **

` :起始行号,结束行号s/^/注释符/g `

例如：`:27,30s#^#//#g ` 就是在27 - 30行添加 // 注释`

** 取消注释 **

·:起始行号,结束行号s/^注释符//g·

例如： `:27,30s#^//##g` 就是在27 - 30行删除 // 注释


### 自动补全
`crl+p 和 crl+n`



## 替换
在命令行模式下

```
:[g] [address] s/search-string/replacement-string[/option]
```
其中

* addess：代表行
* s: 表示替换当前字符
* S: 表示替换当前行
* ~: 表示修改大小写

例如：

* ` :s/p1/p2/g ` 将当前行中所有p1均用p2替代
*  ` :1,.s/p1/p2/g ` 将当前行之前的所有行的字符串p1替换为字符串p2
* ` :1,$s/p1/p2/g` 将所有出现的字符串p1替换为字符串p2
* ` :g/chaper/s/ten/10/`将第一次出现的包含字符串chaper的所有行中的字符串ten替换为字符串10
* `:%s/<ten>/10/g` 将所有出现的字ten替换为字符串10
* `:.,.+10s/every/each/g`将出现的从当前行到后续10行内的每个字符串every替换为字符串each
* ` :s/<short\>/"&"/` 将当前行中的字short替换为"short"(即将当前行中的字short用引号括起来)
* `:n1,n2s/p1/p2/g` 将第n1至n2行中所有p1均用p2替代
* `:n1,n2 co n3` 将n1行到n2行之间的内容拷贝到第n3行下
* `:n1,n2 m n3` 将n1行到n2行之间的内容移至到第n3行下
* `:n1,n2 d` 将n1行到n2行之间的内容删除



## 查找
* `/pattern`从光标开始处向文件尾搜索pattern
* `?pattern`从光标开始处向文件首搜索pattern
* 鼠标移动的一个单词上,按 `*` 在全文中查找与之相同的单词

> n：在同一方向重复上一次搜索命令，N：在反方向上重复上一次搜索命令




## 标记
`ma` 设置一个名字为`a`的标记

` ‘a ` (单引号+标记a)定位到标记a的位置所在行的起始位置

 `a(反引号+标记a)将光标定位到标记a所在的的精确位置 

` d'a ` 删除当前行到标记a所在行之间的文本

` d`a ` 删除当前行到a标记处的所有文本

在命令中可以作为行号使用 ，例如： `'m,.s/the/THE/g`


## 编辑指定文件
* ` :e[!] [filename] ` 编辑filename指定的文件，如果不指定filename，继续编辑当前文件(load文件到当前缓冲区即编辑区，可做刷新文件用)
* `:e#` 关闭当前文件，打开上次编辑的文件
* ` :n filename` 打开新文件

## vim中执行shell

* `:sh` 进入命令行模式， 按`CONTROL+D` 或输入 `exit` 命令退出命令模式
* `:!command ` ,vim中执行command命令
* `!!command` ,执行command命令，并把输出结果替换当前行
* `!+行地址+ command ` ,对指定行执行command命令，并用输出结果替换

 
## 重复上次命令
`. `重复上次操作
 



​