# Tools for Manipulating Object Files
There are a number of tools available on Linux systems to help you understand and manipulate object files. In particular, the GNU binutils package is especially helpful and runs on every Linux platform.

## ar
Creates static libraries, and inserts, deletes, lists, and extracts members. 

* c Create the archive.
* t List the files in the archive.
* r Replace or add the specified files to the archive.
* d Delete the specified archive files.
* x  Extract the specified archive members into the files named by the command line arguments.  If no members are specified, all the members of the archive are extracted into the current directory. 
* s Running `ar s` on an archive is equivalent to running `ranlib` on it.  
  
```bash
arc rcs libvec.a addvec.o multvec.o
```


## strings
Lists all of the printable strings contained in an object file.
```bash
$ strings main.o
```
## strip
Deletes symbol table information from an object file.
```bash
$ strip main.o
```

## nm 
Lists the symbols defined in the symbol table of an object file.
```bash
$ nm main.o
```
## size
Lists the names and sizes of the sections in an object file.
```bash
$ size multvec.o
```

## readelf
 Displays the complete structure of an object file, including all of the information encoded in the ELF header. Subsumes the functionality of size and nm. 

Check ELF header by typing:
```bash
readelf -h kernel
```

Check program headers by typing:
```bash
readelf -l kernel
``` 

Check section headers by typing:
```bash
readelf -S kernel
```


## objdump
The mother of all binary tools.Can display all of the informationinan object file. Its most useful function is disassembling the binary instructions in the '.text' section. 

Examine the full list of the names, sizes, and link addresses of all the sections in the kernel executable by typing:
```bash
objdump -h obj/kern/kernel
```

Inspect the program headers by typing:
```bash
objdump -x obj/kern/kernel
```

You can see the entry point by typing:
```bash
objdump -f obj/kern/kernel
```

You can read symbol table by typing:
```bash
objdump -G obj/kern/kernel
```

Disassemble object file
```bash
objdump -d obj/kern/kernel
```
 

## ldd
 Lists the shared libraries that an executable needs at run time. 
```bash
ldd prog
```

## addr2line
convert addresses into file names and line numbers.
```bash
$ addr2line -e kernel/kernel 0000000080002de2 
```
 
