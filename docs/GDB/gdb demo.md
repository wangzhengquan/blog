## Source Code

map.c

```c
#include <stdio.h>
#include <stdlib.h>

/* A statically allocated variable */
int foo;

/* UNCOMMENT THIS LINE for 3.4.3*/
extern int recur(int i);


/* A statically allocated, pre-initialized variable */
volatile int stuff = 7;

int main(int argc, char *argv[]) {
    /* A stack allocated variable */
    volatile int n = 3;

    int arr[] = {1, 2, 3, 4, 5};
    
    if(argc=2)  
      n = atoi(argv[1]);

    /* Dynamically allocate some stuff */
    char *buf1 = malloc(100);
    /* ... and some more stuff */
    volatile char *buf2 = malloc(100);

    sprintf(buf1, "hello world");
    recur(n);
    return 0;
}
```

recurse.c

```c
#include <stdio.h>

int recur(int i) {
    /* A stack allocated variable within a recursive function */
    int j = i;
    printf("%i\n", i);

    if (i > 0) {
        return recur(i - 1);
    }

    return 0;
}
```

## Compiling

You have to tell your compiler to compile your code with symbolic debugging information included. Here's how to do it with gcc, with the -g switch:

```
gcc -g -Wall map.c recurse.c -o map

# -g: This option adds debugging info in the operating system's native format.
   e.g. stabs, COFF, XCOFF, or DWARF.

# -ggdb: It produces debugging information compatible for use by GDB.
   i.e. the most expressive format available.
   e.g. DWARF 2, stabs, or the native format if none is available.

# -Wall: It enables all warnings in the code.
```

## Starting The Debugger

There are two ways you can feed arguments to your program in the GDB debugger. Please follow the steps given in the below code snippet.

```
Method-I:
  $ gdb --args program arg1 arg2 ... argN  
  (gdb) r

Method-II:
  $ gdb program  
  (gdb) r arg1 arg2 ... argN

#Note: Run GDB with <--silent> option to hide the extra output it emits on the console.
```


Fot this demo, we launch a program called map in the debugger and pass argument 5.

```
$ gdb --args map 5
```

## Print Source Code in GDB Console
If your program hits a breakpoint, then you may want to look at the source code around it. In such a case, use the `l (or list)` command which prints ten lines of source code at a time.

You can also pass the list command a line number or a function name to tell GDB where to start.

```
list <filename>:<function>/<linenum>  # The filename may be omitted.
list <first>,<last>   # If last is omitted the context starting at start is printed instead of centered around it.
```

Display lines after a line number
```
(gdb) list 12
```
Display lines after a function
```
(gdb) list CheckValidEmail
```
Note: GDB debugger usually shows a few lines back of the point you requested.


 
## Breakpoints

### Break into a line or a Function.

To set a new breakpoint, You use the break or b command, and specify a location, which can be a function name,  line number, or source file:line number.

```bash
break <where>
```

Temporary break point
```bash
tbreak <where>
```

Where

- linenum
- function
- filename:LineNum/function
- `*instruction_addr`

For this demo, let's set a breakpoint at main(), and start the program:

```
(gdb) break main
Breakpoint 1 at 0x6e9: file map.c, line 18.
```

set breakpoint at special instruction address
```bash
break *0x000000008000215a
```

when you type `run` command , program will suspend at the first break point it encounter.

```
(gdb) run
Starting program: /home/vagrant/code/personal/hw-intro/map 5

Breakpoint 1, main (argc=2, argv=0x7fffffffe3a8) at map.c:18
18	    if(argc<2) n= 3;
(gdb) 
```


### Break after a condition.

```bash
break <where> if <condition> 
```

Break at the given location if the condition is met. Conditions may be almost any C expression that evaluate to true or false.

```bash
(gdb) break recur if i==1
Breakpoint 2 at 0x55555555475e: file recurse.c, line 5.
(gdb) continue
Continuing.

Breakpoint 2, recur (i=1) at recurse.c:5
5	    int j = i;
(gdb) print i
$1 = 1
```

Set/change the condition

```
condition <breakpoint> <condition> 
```

Set/change a condition on anexisting breakpoint.

```
(gdb) condition 2 i==0
```


### Skip/Ignore Breakpoints
While you are running through a loop in your code and wouldn’t want to pause for every break, then <ignore> command can help. Here is how you can skip a breakpoint the number of times you want.

First, check the index of the breakpoint which you want to ignore. Use the <info breakpoints> command.
```
(gdb) break test.cpp:18
Breakpoint 1 at 0x8005cd: file test.cpp, line 18.
 
(gdb) info breakpoints
Num     Type           Disp Enb Address            What
1       breakpoint     keep y   0x00000000008005cd in main() at test.cpp:18

```
Then, run the following command in the GDB debugger. Say, we want to ignore the break for 1000 times.
```
(gdb) ignore 1 1000
Will ignore next 1000 crossings of breakpoint 1.
(gdb) run
Starting program: /home/sample/src/test
 
Program received signal SIGSEGV, Segmentation fault.
0x00000000008005cd in main () at test.cpp:18
18            i = *ptr++;
(gdb) info breakpoints
Num     Type           Disp Enb Address            What
1       breakpoint     keep y   0x00000000008005cd in main() at test.cpp:18
    breakpoint already hit 10 times
    ignore next 990 hits
(gdb)
```
In the above example, we set the ignore limit to 1000, but the program crashed after the 10th iteration. So you should revise the ignore limit to 9 and step in to debug the condition which is leading to the crash.

That’s how the <gdb ignore> command helps in isolating issues.

### Remove Breakpoints & Quit from GDB
Deleting a Breakpoint
The option <d> is the GDB shortcut for deleting any breakpoint.
```
(gdb) d <breakpoint num>
```


## GDB Commands to Print Debug Info

### Command to execute after the program hits a break-point.

With the help of the <command> keyword, you can set multiple commands to run every time a breakpoint occurs. See the below code snippet for clarity.
```
(gdb) b CheckValidEmail
Breakpoint 1 at 0x8049d87: file ../../test/testgdb.c, line 107.
(gdb) command 1
# Note:
#1: 1 is the breakpoint number.
#2: Here you can specify set of commands to execute.
#3: To close the command block, use the "end" keyword.
>print port
>print IPAddr
>print User
>print Pwd
>end
(gdb)
```

### Examining the call stack

Show call stack.

```
(gdb) backtrace (or bt as shortcut) [full]
# OR
(gdb) info stack
```

The command `backtrace` (or `bt`) will show you the current function call stack, with the current function at the top, and the callers in order beneath it:

Use of the 'full' qualifier also prints the values of the local variables.

```
(gdb) backtrace
#0  recur (i=3) at recurse.c:5
#1  0x0000555555554832 in recur (i=4) at recurse.c:9
#2  0x0000555555554832 in recur (i=5) at recurse.c:9
#3  0x00005555555547dd in main (argc=2, argv=0x7fffffffe3a8) at map.c:29
```

### print the current stack of the executing program
You can call the <where> command which will return the trace along with the line number.
```
(gdb) where
#Tell you the point where the program died.
``` 
### print the line number in GDB while debugging
It’s the <frame> gdb command that will return the line number.
```
(gdb) frame
 #0  0x0807826e in main () at test.c:18
 18        if(is_exist(list, 10) != 0 ) {
(gdb)
```

## Examining Variables 

### Print Variables

Print content of variable/memory location/register.

```
print/format <what>
```

Format

* a: Pointer.
* c: Read as integer, print as character. d Integer, signed decimal.
* f: Floating point number.
* o: Integer, print as octal.
* s: Try to treat as C string.
* t: Integer, print as binary (t = "two“).
* u: Integer, unsigned decimal.
* x: Integer, print as hexadecimal.

What

* "expression" Almost any C expression, including function calls (must be prefixed with a cast to tell GDB the return value type).
* "file_name::variable_name" Content of the variable defined in the named file (static variables).
* "function::variable_name" Content of the variable defined in the named function (if on the stack).
* "{type}address" Content at address, interpreted as being of the C type type.
* "$register" Content of named register. Interesting registers are $esp (stack pointer), $ebp (frame pointer) and $eip (instruction pointer).

```
(gdb) print n
$1 = 5
(gdb) print/t n
$2 = 101
```

### Print a Macro
Printing a macro requires that you first compile your program with an extra option. Use the <-ggdb3> flag.
```
(gdb) p/x DBG_FLAG
$1 = 0x00

# Note: The x in the p/x would cause to print output in hex format.
```

### Print Array

```
print *array@len
```

Example: If you have defined an integer array as int arr[5] = {1, 2, 3, 4, 5};
```
(gdb) p arr
$1 = {1, 2, 3, 4, 5}
```

But the above method would not help if we have a large array of integers say 100 and the need is to print the integers between (96-100) range.
For this purpose, you can use following method which works in all cases but is a little complex in nature.
```
(gdb) p *&arr[96]@5
```

Print the first 3 elements of the Array

```
(gdb) print *arr@3
$4 = {1, 2, 3}
```

### printf
```
(gdb) printf "%08X\n", n
00000005
```

### Add Watchers
Adding watchpoints is same as telling the debugger to give a dynamic analysis of changes to the variables. And, it’s easy to add a watchpoint in your code.
```
(gdb) watch <expression>
```

Note that watch takes an expression as an argument, so you can put a variable name in there, or something more complex like `*(p+5)` or `a[15]`

watchpoints are special breakpoints that will trigger whenever an expression changes. Often you just want to know when a variable changes (is written to), and for that you can use the watch command:

```
(gdb) watch n
Hardware watchpoint 2: n
(gdb) next

Hardware watchpoint 2: n

Old value = 21845
New value = 3
main (argc=2, argv=0x7fffffffe3a8) at map.c:18
18      if(argc=2)  
(gdb) 
19        n = atoi(argv[1]);
(gdb) 

Hardware watchpoint 2: n

Old value = 3
New value = 5
main (argc=2, argv=0x7fffffffe3a8) at map.c:22
22      char *buf1 = malloc(100);
(gdb) 
```

### Display Variables

```
display/format <what>
```

Like `print`, but print the information after each stepping instruction.

For this demo

```
(gdb) display i
1: i = 5
(gdb) next
6     printf("%i\n", i);
1: i = 5
(gdb) next
5
8     if (i > 0) {
1: i = 5
```

## Examining Memory

```
x/nfu <address> 
```

* n: How many units to print (default 1).
* f: Format character (like "print“).
* u: Unit. Unit is one of:  
      * b: Byte  
      * h: Half-word (two bytes)  
      * w: Word (four bytes)  
      * g: Giant word (eight bytes))  

For example,

```
(gdb) x buf1
0x555555756260: "hello world"
(gdb) x/11x buf1
0x555555756260: 0x68  0x65  0x6c  0x6c  0x6f  0x20  0x77  0x6f
0x555555756268: 0x72  0x6c  0x64
```


To examine instructions in memory (besides the immediate next one to be executed, which GDB prints automatically), you use the `x/i` command. This command has the syntax `x/Ni ADDR`, where N is the number of consecutive instructions to disassemble and ADDR is the memory address at which to start disassembling.
```
x/10i
```


## Stepping

### Next statement

Step N statements, proceeding through subroutine calls.

```
next [N]
```

Unlike "step", if the current source line calls a subroutine,
this command does not enter the subroutine, but instead steps over
the call, in effect treating it as a single source line.

```
(gdb) next
19	    else n = atoi(argv[1]);
(gdb) 
```

Hitting Enter key will repeat the last command, this will save you typing `next` over and over again.

### Next instruction
Step a single assembly instruction, but proceed through subroutine calls.
```
nexti [N]
```

### Step into subroutine
```
step [N]
```

Step statement, if it's a subroutine call it will enter into the subroutine.

```
27	    recur(n);
(gdb) step 
recur (i=5) at recurse.c:5
5	    int j = i;
(gdb) 
```


### Step instruction
Step a single assembly instruction if it's a subroutine call it will enter into the function.
```
stepi [N]
```


### finish
Continue until the current function returns.
```
(gdb) finish (or fin as shortcut)
```

### continue
Continue normal execution until the end of programe or the next breakpoint.
```
continue
```

### Advance

```
advance <where>
```

To continue to a specific location, use the advance command, specifying a location like those shown in the "Breakpoints" section, above. Here's an example which advances from the current location until the function "recur()" is called:

```
(gdb) advance recur
recur (i=5) at recurse.c:5
5	    int j = i;
(gdb) 
```

advance is just shorthand for "continue to this temporary breakpoint."


## directory

```
directory <directory>
```

Add directory to the list of directories that is searched for sources.

## Load file
```
(gdb) file kernel/kernel
```

## Call function

```
call functionName(args...)
```

execute arbitrary function and print the result.

```
(gdb) call recur(3)
$1 = 0
```

## Informations

* `info breakpoints`: Print informations about the breakpoints and watchpoints.
* `info registers`: Print values of all registers.
* `info args`: Print the arguments to the function of the current stack frame.
* `info locals`: Print the local variables in the currently selected stack frame.
* `info display`: Print informations about the "displays“.
* `info sharedlibrary`: List loaded shared libraries.
* `info signals`: List all signals and how they are cur- rently handled.
* `info threads`: List all threads.
* `info frame`:


## Disassemble

Disassemble the current function or given location.

```
disassemble <where>
```

For this demo, I have enter into recur function, then I execute `disassemble`, gdb print out the assembly code of this "recur" function.

```
(gdb) disassemble
Dump of assembler code for function recur:
   0x00005555555547f8 <+0>:	push   %rbp
   0x00005555555547f9 <+1>:	mov    %rsp,%rbp
   0x00005555555547fc <+4>:	sub    $0x20,%rsp
   0x0000555555554800 <+8>:	mov    %edi,-0x14(%rbp)
=> 0x0000555555554803 <+11>:	mov    -0x14(%rbp),%eax
   0x0000555555554806 <+14>:	mov    %eax,-0x4(%rbp)
   0x0000555555554809 <+17>:	mov    -0x14(%rbp),%eax
   0x000055555555480c <+20>:	mov    %eax,%esi
   0x000055555555480e <+22>:	lea    0xaf(%rip),%rdi        # 0x5555555548c4
   0x0000555555554815 <+29>:	mov    $0x0,%eax
   0x000055555555481a <+34>:	callq  0x5555555545f0 <printf@plt>
   0x000055555555481f <+39>:	cmpl   $0x0,-0x14(%rbp)
   0x0000555555554823 <+43>:	jle    0x555555554834 <recur+60>
   0x0000555555554825 <+45>:	mov    -0x14(%rbp),%eax
   0x0000555555554828 <+48>:	sub    $0x1,%eax
   0x000055555555482b <+51>:	mov    %eax,%edi
   0x000055555555482d <+53>:	callq  0x5555555547f8 <recur>
   0x0000555555554832 <+58>:	jmp    0x555555554839 <recur+65>
   0x0000555555554834 <+60>:	mov    $0x0,%eax
   0x0000555555554839 <+65>:	leaveq 
   0x000055555555483a <+66>:	retq   
End of assembler dump.
```

## Layout

* `tui enable` show tui window.
* `layout src`	Standard layout—source on top, command window on the bottom
* `layout asm`	Just like the "src" layout, except it's an assembly window on top
* `layout split`	Three windows: source on top, assembly in the middle, and command at the bottom
* `layout reg`	Opens the register window on top of either source or assembly, whichever was opened last
* `tui reg general`	Show the general registers
* `tui reg float`	Show the floating point registers
* `tui reg system`	Show the "system" registers
* `tui reg next`	Show the next page of registers—this is important because there might be pages of registers that aren't in the "general", "float", or "system" sets

For this demo, when you type `layout asm` command ,you will see the interface as following
 ![layout asm](./img/demo/AsmLayout.png)

In asm layout , we can use `nexti` or `stepi`   command to step to the next asm instruction

When "layout" command split the window into diffrent pieces, we can use 'focus' command to specify which window to focus, for example use `focus asm` to select asm window, then you can use arrow key to scroll the window.


you can also use shortcut key to switch layout.
- 'Ctr-x a' or 'Ctr-x 1' Standard layout—source on top, command window on the bottom
- 'Ctr-x 2'  Three windows: source on top, assembly in the middle, and command at the bottom

Note: 'Ctr-x a' means first press and release 'Ctr-x' then click 'a'

## Help

`help command`	Get help on a certain command
For example

```
help breakpoint 
```

## GDB dashboard

[GDB dashboard](https://github.com/cyrus-and/gdb-dashboard) is a standalone ".gdbinit" file.
Just place ".gdbinit" in your home directory or project directory, for example with:

```
wget -P ~ https://git.io/.gdbinit
```

Then debug as usual, the dashboard will appear automatically every time the inferior program stops.

Optionally install Pygments to enable syntax highlighting:

```
pip install pygments
```

If it has no effect, execute the following command in the terminal

```
set auto-load local-gdbinit on
```


## LLDB

In MacOS use [LLDB](https://lldb.llvm.org/use/map.html) instead.

## Some other commands

 ![Commands](./img/demo/commands1.jpg)


> https://beej.us/guide/bggdb
  https://wiki.ubuntu.org.cn/用GDB调试程序
  http://www.unknownroad.com/rtfm/gdbtut/gdbtoc.html
  https://sourceware.org/gdb/current/onlinedocs/gdb    
  [GDB Tutorial: Advanced Debugging Tips For C/C++ Programmers](https://www.techbeamers.com/how-to-use-gdb-top-debugging-tips/)   
  [Give me 15 minutes & I'll change your view of GDB [VIDEO]](https://www.youtube.com/watch?v=PorfLSr3DDI)   




