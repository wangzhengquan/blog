

本练习采用下面四个C文件， addvec.c  multvec.c  和 vector.h 是库文件， main.c是引用库函数的文件

vector.h 
```c
/* prototypes for libvector */
void addvec(int *x, int *y, int *z, int n);
void multvec(int *x, int *y, int *z, int n);
int getcount();

```
/addvec.c
```c
int addcnt = 0;

void addvec(int *x, int *y, int *z, int n)
{
  int i;

  addcnt++;

  for (i = 0; i < n; i++)
    z[i] = x[i] + y[i];
}
```
multvec.c 
```c
int multcnt = 0;

void multvec(int *x, int *y, int *z, int n)
{
  int i;

  multcnt++;

  for (i = 0; i < n; i++)
    z[i] = x[i] * y[i];
}

```
main.c 
```c
#include <stdio.h>
#include "vector.h"

int x[2] = {1, 2};
int y[2] = {3, 4};
int z[2];

int main()
{
  addvec(x, y, z, 2);
  printf("z = [%d %d]\n", z[0], z[1]);
  return 0;
}

```
## Linking with Static Libraries
编译库文件，并打包成静态库
```bash
#  create a static library libvector.a
gcc -c addvec.c multvec.c
ar rcs libvector.a addvec.o multvec.o
```

构建引用静态库的可执行文件
```bash
# build the executable
gcc -o prog main.c ./libvector.a
```

或者用下面的命令也是一样的
```bash
 
gcc -o prog main2.c  -L.  -lvector
```
参数‘-lvector’是libvector.a的简略表示方式，参数'-L' 告诉linker在当前目录下寻找libvector.a.   

执行命令 `objdump -d prog` 可以发现只有addvec模块被合并到最后的prog可执行文件中了，而multvec模块 里的方法因为没有被引用到所以没有被合并进去，这样做可以减小可执行文件的大小
 

运行

```bash
$ ./prog 
  z = [4 6]
```
如果链接的过程加 '-static' 选项表示针对系统库引用也以静态库的方式引入，如下所示：

```bash
#  The -static argument tells the compiler driver that the linker should build a fully linked executable object file that can be loaded into memory and run without any further linking at load time.
gcc -static -o prog main.c -L. -lvector
```



## Dynamic Linking with Shared Libraries

利用静态链接方式构建的执行文件是可以直接加载执行的，而动态链接裤构建的可执行文件是一个半链接文件需要在运行的时候再进行一次链接处理方可执行。所以动态链接裤构建的可执行文件里面还保存着一些relocation的信息。
 
下面删除./libvector.a， 然后测试一下动态链接库的构建过程。

编译可共享的libvector.so 库文件
```bash
gcc -shared -fpic -o libvector.so addvec.c multvec.c
```
The -fpic flag directs the compiler to generate position-independent code. The -shared flag directs the linker to create a shared object file.   

我们已经创建完成了so库文件，然后就可以把它链接的我们的程序中。
构建引用动态库的可执行文件
```bash
 
gcc -o prog2 main.c ./libvector.so
```
这就构建了一个可以在运行时进行链接的可执行文件。
 
运行成功

```bash
$ ./prog2

z = [4 6]
```

但是假如我用下面的方式构建引用动态库的可执行文件，会发生什么呢？

```bash
gcc -o prog3 main.c -L. -lvector
```

运行，发现报错了

```bash
$ ./prog3
  ./prog3: error while loading shared libraries: libvector.so: cannot open shared object file: No such file or directory
```

在命令行中输入`ldd prog3`，有如下输出信息

```bash
$ ldd prog3
      linux-vdso.so.1 (0x00007ffcf5ba8000)
      libvector.so => not found
      libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f15d3199000)
      /lib64/ld-linux-x86-64.so.2 (0x00007f15d378c000)
```

对比`ldd prog2`,

```bash
$ ldd prog2
      linux-vdso.so.1 (0x00007ffc79fa5000)
      ./libvector.so (0x00007f472cad5000)
      libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f472c6e4000)
      /lib64/ld-linux-x86-64.so.2 (0x00007f472ced9000)
```

发现“./libvector.so”哪里显示“not found”，意思是"prog3"找不到链接库。原来还需设置动态链接库的寻找路径。

可以把“./libvector.so”所在的路径加入到系统的“/etc/ld.so.conf”文件中。加入之后还需要执行`sudo ldconfig` 命令让新路径生效。

或者， 也可以用`export 动态链接库所在的路径`的方式，如

```bash
export LD_LIBRARY_PATH=/home/vagrant/test
```

完成路径设置后，在执行ldd命令
```bash
$ ldd ./prog3
      linux-vdso.so.1 (0x00007ffe22fa3000)
      libvector.so => /home/vagrant/test/libvector.so (0x00007fe25cd03000)
      libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007fe25c912000)
      /lib64/ld-linux-x86-64.so.2 (0x00007fe25d107000)
```
这时候可以发现libvector.so已经可以找到了

再一次运行

```bash
$ ./prog3
  z = [4 6]
```
 