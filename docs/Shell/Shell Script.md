## symbol


- `$@` : stores all the arguments in a list of string
- `$*` : stores all the arguments as a single string
- `$#` : stores the number of arguments


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

## wait

```bash
#########################################################
# first word-count

# generate the correct output
../mrsequential ../../mrapps/wc.so ../pg*txt || exit 1
sort mr-out-0 > mr-correct-wc.txt
rm -f mr-out*

echo '***' Starting wc test.

../mrcoordinator ../pg*txt &
pid=$!

# give the coordinator time to create the sockets.
sleep 1

# start multiple workers.
(../mrworker ../../mrapps/wc.so) &
(../mrworker ../../mrapps/wc.so) &
(../mrworker ../../mrapps/wc.so) &

# wait for the coordinator to exit.
wait $pid

# since workers are required to exit when a job is completely finished,
# and not before, that means the job has finished.
sort mr-out* | grep . > mr-wc-all
if cmp mr-wc-all mr-correct-wc.txt
then
  echo '---' wc test: PASS
else
  echo '---' wc output is not the same as mr-correct-wc.txt
  echo '---' wc test: FAIL
  failed_any=1
fi

# wait for remaining workers and coordinator to exit.
wait
```



