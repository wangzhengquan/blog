## Tools for Manipulating Object Files
There are a number of tools available on Linux systems to help you understand and manipulate object files. In particular, the GNU binutils package is especially helpful and runs on every Linux platform.

- **ar.** Creates static libraries, and inserts, deletes, lists, and extracts members. 
-  **strings.** Lists all of the printable strings contained in an object file.
-  **strip.** Deletes symbol table information from an object file.
-  **nm.** Lists the symbols defined in the symbol table of an object file.
-  **size.** Lists the names and sizes of the sections in an object file.

-  **readelf.** Displays the complete structure of an object file, including all of the information encoded in the ELF header. Subsumes the functionality of size and nm. Common usage: `readelf -a prog`

-  **objdump.** The mother of all binary tools.Can display all of the informationinan object file. Its most useful function is disassembling the binary instructions in the '.text' section. Common usage: `objdump -dx prog`

- **ldd:** Lists the shared libraries that an executable needs at run time. Common usage: `ldd prog`

- `addr2line`: convert addresses into file names and line numbers.
```bash
$ addr2line -e kernel/kernel
 	0x0000000080002de2
  0x0000000080002f4a
  0x0000000080002bfc
```
You should see something like this:
```
  kernel/sysproc.c:74
  kernel/syscall.c:224
  kernel/trap.c:85
```
