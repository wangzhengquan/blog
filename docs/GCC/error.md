## C++ 引用 C 库

如果在C++里引用C库，include 头需要包裹在`extern "C" {}`中，否则会报错 `undefined reference to XXXX`。例如：
```C++
extern "C"
{
// 如果没有extern "C"，则会报错：undefined reference to `avfilter_register_all'
#include <stdio.h>

#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>

}
```
