##  Variable And Pointer

什么是变量？    
变量如`int i = 2`,包含类型int, 名称i, 值2，其中类型表示变量的长度，名称表示变量的RAM地址,值表示RAM上存储的数据。

什么是指针？    
指针也是一个变量，它与普通变量的区别是，存储在指针变量里的值是另外一个变量的RAM地址。


## Const Pointer

> 只需要记住， 只有`* const` 才表示constant pointer  

1.  constant pointer to a non-constant char
```C++
char * const ptr;
```

2. non-constant pointer to a constant char
```c++
const char * ptr;
char const * ptr;
```

3. constant pointer to a constant char

```C++
const char * const ptr;
char const * const ptr;
```

Here, the const to the right of the asterisk declares ptr to be const: ptr can’t be made to point to a different location, nor can it be set to null.  The const to the left of the asterisk says that what ptr points to—the character string—is const, hence can’t be modified.

[The Clockwise/Spiral Rule](https://c-faq.com/decl/spiral.anderson.html)

## keyword volatile

Declare global variables with volatile. Consider a handler and main routine that share a global variable g. The handler updates g, and main periodically reads g. To an optimizing compiler, it would appear that the value of g never changes in main, and thus it would be safe to use a copy of g that is cached in a register to satisfy every reference to g. In this case, the main function would never see the updated values from the handler.

You can tell the compiler not to cache a variable by declaring it with the volatile type qualifier. For example:
```c
volatile int g;
```
The volatile qualifier forces the compiler to read the value of g from memory each time it is referenced in the code. In general, as with any shared data structure, each access to a global variable should be protected by temporarily blocking signals.


## array and struct

```C
typedef struct  {
	char *name;
	int age;
	float weight;

} Student;

const Student students[100] = {
	[10] = {.name = 'ni', .age=23, .weight = '100.0'},
	[50] = {.name = 'wzq', .age=21, .weight = '120.0'}
};
```


