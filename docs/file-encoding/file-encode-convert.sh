#!/bin/bash

# 把当前目录下所有java文件编码转换为UTF-8
find . -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
    # 假设源文件编码是 GBK，目标编码是 UTF-8
    # 请根据你实际的源文件编码进行修改，例如：-f GBK, -f ISO-8859-1, -f BIG5 等
    # 如果不确定源编码，并且文件包含中文，通常是 GBK 或 GB2312
    echo "$file"
    iconv -c -f GBK -t UTF-8 "$file" > "${file}.utf8"
    mv "${file}.utf8" "$file"
done
