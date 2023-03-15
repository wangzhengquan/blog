

## Multiple Selection with the Keyboard


### Adding a Line

To add the line above or below to the selection, use:

* Windows/Linux: Ctrl+Alt+⬆ and Ctrl+Alt+⬇
* Mac: ⌃+⇧+⬆ and ⌃+⇧+⬇

### Splitting the Selection into Lines

Select a block of lines, and then split it into many selections, one per line, using:

* Windows/Linux: Ctrl+Shift+L
* Mac: ⇧+⌘+L

### Quick Add Next
To add the next occurrence of the current word to the selection, use Quick Add Next, which is bound to:

* Windows/Linux: Ctrl+D
* Mac: ⌘+D

Individual occurrences can be skipped via Quick Skip Next, which is bound to:

* Windows/Linux: Ctrl+K, Ctrl+D
* Mac: ⌘+K, ⌘+D

### Find All

To add all occurrences of the current word to the selection, use Find All:

* Windows/Linux: Alt+F3
* Mac: ⌃+⌘+G

### Undo Selection

If you go too far, use Undo Selection to step backwards:

* Windows/Linux: Ctrl+U
* Mac: ⌘+U

### Single Selection

To go from multiple selections to a single selection, press Esc.


### Column Selection


Column selection can be used to select a rectangular area of a file. Column selection doesn’t operate via a separate mode, instead it makes use of multiple selections.

You can use additive selections to select multiple blocks of text, or subtractive selections to remove a block.
 

### Using the Mouse

Different mouse buttons are used on each platform:

MAC

* Left Mouse Button + ⌥
* OR: Middle Mouse Button
* Add to selection: ⌘
* Subtract from selection: ⇧+⌘

WINDOWS

* Right Mouse Button + Shift
* OR: Middle Mouse Button
* Add to selection: Ctrl
* Subtract from selection: Alt

LINUX

* Right Mouse Button + Shift
* Add to selection: Ctrl
* Subtract from selection: Alt

### Using the Keyboard

MAC

* Ctrl+Shift+⬆
* Ctrl+Shift+⬇

WINDOWS

* Ctrl+Alt+⬆
* Ctrl+Alt+⬇

LINUX

* Alt+Shift+⬆
* Alt+Shift+⬇


## Index

### Find file 

Type `Ctrl+P` on Windows and Linux, `Cmd+P` on macOS. and type the name of the file you're looking for. If there are multiple hits, you can select the appropriate file using cursor keys. It also supports powerful operators, that let you jump to specific parts inside a file.

Examples:

* `file.js` opens that file
* `:100` jumps to line 100 in current file
* `file.js:100` jumps to line 100 in file.js
* `file.js@loadFile` : jumps to a loadFile() in file.js


### Search DEFINITION inside the current file
- Windows/Linux: `Ctrl+R`  
- Mac: `⌘+R` 


### GOTO DEFINITION 
* Invoking Goto Symbol in Project to fuzzy-search through symbols
    - Windows/Linux: `Ctrl+Shift+R`  
    - Mac: `⌘+Shift+R` 
* Hovering over a word to show the Goto Definition Popup
* `Ctrl-->Click the word-->Goto Definition`
* Executing Goto Definition for the word under the caret `F12`
* Executing Goto Reference for the word under the caret `Shift+F12`

All of the Goto commands can also be invoked via the "Goto menu".
 

### Jump to line `Ctrl-g`



> https://www.sublimetext.com/docs/