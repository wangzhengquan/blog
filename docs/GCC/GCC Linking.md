本练习采用下面四个C文件， addvec.c  multvec.c  和 vector.h 是库文件， main.c是引用库函数的文件

vector.h

```c
/* prototypes for libvector */
void addvec(int *x, int *y, int *z, int n);
void multvec(int *x, int *y, int *z, int n);
int getcount();
```

addvec.c

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
 
gcc -o prog main.c  -L.  -lvector
```

参数‘-lvector’是libvector.a的简略表示方式，参数'-L.' 告诉linker在当前目录下寻找libvector.a.

执行命令 `objdump -d prog` 可以发现只有addvec模块被合并到最后的prog可执行文件中了，而multvec模块 里的方法因为没有被引用到所以没有被合并进去，这样做可以减小可执行文件的大小

运行

```bash
$ ./prog 
  z = [4 6]
```

如果链接的过程加 '-static' 选项表示针对系统库引用也以静态库的方式引入. The '-static' argument tells the compiler driver that the linker should build a fully linked executable object file that can be loaded into memory and run without any further linking at load time. 如下所示：

```bash
gcc -static -o prog main.c -L. -lvector
```

## Dynamic Linking with Shared Libraries

静态库云动态的库区别是什么呢？


1. 引用静态库编译生成的是一个完全链接的执行文件，运行的时候加载器可以直接拷贝到内存运行；而引用动态共享库编译生成的是一个部分链接的可执行文件，加载器在加载该文件的时候需要先交由链接器进行进一步链接处理（reloacation），链接处理完后才可运行。
2. 引用静态库编译，静态库中被目标执行文件所引用的模块都会被拷贝到目标执行文件中来；而引用动态库进行编译动态库里被引用的模块不会被拷贝，在运行时动态库里面的模块只有一份全局拷贝，所有执行文件在运行时都引用同一份拷贝。

下面删除'libvector.a'， 然后测试一下动态链接库的构建过程。

编译可共享的libvector.so 库文件

```bash
gcc -shared -fpic -o libvector.so addvec.c multvec.c
```

The '-fpic' flag directs the compiler to generate position-independent code. The '-shared' flag directs the linker to create a shared object file.

我们已经创建完成了so库文件，然后就可以把它链接的我们的程序中。
构建引用动态库的可执行文件

```bash
gcc -o prog2 main.c ./libvector.so
```

这就构建了一个可以在运行时进行链接的可执行文件。

执行`ldd prog2`查看动态链接库的链接状态。

```bash
$ ldd prog2
      linux-vdso.so.1 (0x00007ffc79fa5000)
      ./libvector.so (0x00007f472cad5000)
      libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f472c6e4000)
      /lib64/ld-linux-x86-64.so.2 (0x00007f472ced9000)
```

运行，成功。

```bash
$ ./prog2
  z = [4 6]
```

但是假如我用下面的方式对用动态库进行链接，

```bash
gcc -o prog3 main.c -L. -lvector
```

或者,同样是是前面的那种方式，但是libvector.so前面的路径'./'去掉

```bash
gcc -o prog2 main.c libvector.so
```

可以编译成功，但是，运行，发现报错了。

```bash
$ ./prog3
  ./prog3: error while loading shared libraries: libvector.so: cannot open shared object file: No such file or directory
```

在命令行中输入`ldd prog3`，查看链接状态

```bash
$ ldd prog3
      linux-vdso.so.1 (0x00007ffcf5ba8000)
      libvector.so => not found
      libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f15d3199000)
      /lib64/ld-linux-x86-64.so.2 (0x00007f15d378c000)
```

发现“[libvector.so](http://libvector.so)”那里显示“not found”，[意思是找不到libvector.so](http://xn--libvector-kc6n777ai75b4tc30g8uq.so)。为什么会这样呢，原来通过这种方式编译生成的目标执行文件没有路径信息，‘-L.’仅仅是在编译的时候用到的，在加载运行的时候链接器首先要到系统设定的动态链接库查找目录中查找引用到的so文件以进行链接处理（relocation），而我们的libvector.so文件所在目录不在系统设定的动态链接库查找目录中，所以找不到。静态链接库为什么不会出现这个问题呢， 因为引用静态链接库进行编译时，生成的是一个完全链接的可执行文件，该文件是可以直接加载（拷贝）到内存运行的；而引用动态链接库进行编译生成的是一个部分链接的可执行文件，加载器在加载这个可执行文件的时候，要先交给链接器进行链接处理（relocation）,链接处理完成后再开始运行这个程序。

如何解决呢，因为动态链接库查找目录是在`/etc/ld.so.conf`文件中设定的，可以把“./libvector.so”所在目录的路径加入到系统的“/etc/ld.so.conf”文件中，加入之后还需要执行`sudo ldconfig` 命令让新路径生效。

或者， 也可以用`export directory_of_library`的方式，如

```bash
export LD_LIBRARY_PATH=/home/vagrant/test
```

完成动态链接库路径设置后，再执行ldd命令，这时候可以发现libvector.so已经可以找到了。

```bash
$ ldd ./prog3
      linux-vdso.so.1 (0x00007ffe22fa3000)
      libvector.so => /home/vagrant/test/libvector.so (0x00007fe25cd03000)
      libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007fe25c912000)
      /lib64/ld-linux-x86-64.so.2 (0x00007fe25d107000)
```


再一次运行，成功。

```bash
$ ./prog3
  z = [4 6]
```


