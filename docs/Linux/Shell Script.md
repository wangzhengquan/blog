
## local variable

```bash
local d="hello" 
``` 

Remark: both side of '=' can't have white space.

## function 

```bash
# declaration function
function buildln() {
  echo dir = ${1}
  
}
# call function
buildln ~/wk/nand2tetris/projects/01
```

## Loop Statement

```bash
for file in `ls ~/wk/nand2tetris/projects/01`
do
  echo ${file}
done
```


## If Statement

```bash
if [[ -d $file ]]; then
    echo "$file is a directory"
elif [[ -f $file ]]; then
    echo "$file is a file"
else
    echo "$file is not valid"
    exit 1
fi
```

## Case Statement

```bash
case ${1} in
  "release")
   echo "release case"
  ;;
  
  "debug")
   echo "debug case"
  ;;  

  "help")
  echo "usage case"
  ;;

  "")
  echo "No argument case"
  ;;
  
  *)
  echo "Default case."
  ;;

esac
```



