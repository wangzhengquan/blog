
## local variable

```bash
local d="hello" 
``` 

Remark: both side of '=' can't have white space.

## function 

```bash
# declaration function
function recurse() {
  local dir=${1}
 # ls ${1}
  #cd ${1}
  for file in `ls ${dir}`
  do
    if [[ -d ${dir}/$file ]]; then
        recurse ${dir}/$file
    elif [[ -f ${dir}/$file ]]; then
        if [[ $file == *.xml ]]
        then
           echo ${dir}/${file} 
           #echo rename ${dir}/${file}
        fi
    else
        echo "$file is not valid"
        exit 1
    fi
  done
}
# call function
recurse ~/wk/nand2tetris/projects
```

## Loop Statement

```bash
for file in `ls ~/wk/nand2tetris/projects/01`
do
  echo ${file}
done
```

```bash
for file in $(find . -name "*.c")
do
  filename=${file%.c}
  echo $filename.cpp
done

```

```bash
for (( i=0; i<10; i++ ))
do
  echo $i 
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



