##  Variable And Pointer

什么是变量？    
变量如`int i = 2`,包含类型int, 名称i, 值2，其中类型表示变量的长度，名称表示变量的RAM地址,值表示RAM上存储的数据。

什么是指针？    
指针也是一个变量，它与普通变量的区别是，存储在指针变量里的值是另外一个变量的RAM地址。


## Const Pointer

只需要记住， 只有`* const` 才表示constant pointer  

```C++
// constant pointer to a non-constant int
int * const p;

// non-constant pointer to a constant int
const int * p;
int const * p;

// constant pointer to a constant int
const int * const p;
int const * const p;
```

[The Clockwise/Spiral Rule](https://c-faq.com/decl/spiral.anderson.html)

## keyword volatile

Declare global variables with volatile. Consider a handler and main routine that share a global variable g. The handler updates g, and main periodically reads g. To an optimizing compiler, it would appear that the value of g never changes in main, and thus it would be safe to use a copy of g that is cached in a register to satisfy every reference to g. In this case, the main function would never see the updated values from the handler.

You can tell the compiler not to cache a variable by declaring it with the volatile type qualifier. For example:
```
volatile int g;
```
The volatile qualifier forces the compiler to read the value of g from memory each time it is referenced in the code. In general, as with any shared data structure, each access to a global variable should be protected by temporarily blocking signals.



