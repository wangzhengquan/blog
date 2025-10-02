 
# 通过 `package.json` 的 `bin` 字段 , 用于命令行工具

如果你正在开发一个命令行工具，并且希望通过 npm 安装后可以在任何地方直接执行，那么 `package.json` 的 `bin` 字段是最佳选择。

1.  **创建一个 `package.json` 文件：**
    在你的项目根目录中运行 `npm init -y`。

2.  **在 `package.json` 中添加 `bin` 字段：**
    编辑 `package.json` 文件，添加 `bin` 字段，它是一个对象，键是你的命令名称，值是你的 Node.js 脚本的路径。

    ```json
    {
      "name": "my-cli-tool",
      "version": "1.0.0",
      "description": "A simple CLI tool",
      "main": "index.js",
      "scripts": {
        "test": "echo \"Error: no test specified\" && exit 1"
      },
      "bin": {
        "mycli": "./bin/mycli.js"
      },
      "keywords": [],
      "author": "",
      "license": "ISC"
    }
    ```

    在这个例子中，我们定义了一个名为 `mycli` 的命令，它将执行 `./bin/mycli.js`。

3.  **创建你的 Node.js 脚本：**
    确保 `bin/mycli.js` 文件存在，并且**在文件顶部添加 Shebang 行**：

    ```javascript
    #!/usr/bin/env node
    // bin/mycli.js

    console.log("Hello from my CLI tool!");

    const command = process.argv[2];
    if (command === "greet") {
        console.log("Greetings!");
    } else if (command === "help") {
        console.log("Usage: mycli [greet|help]");
    } else {
        console.log("Unknown command. Try 'mycli help'");
    }
    ```
4. **赋予文件执行权限：**

```bash
chmod +x bin/mycli.js
```

5.  **安装你的包 (本地或全局)：**

    *   **本地开发链接：** 在你的项目根目录中运行 `npm link`。这会在你的全局 `node_modules` 中创建一个符号链接，指向你当前项目的 `bin` 脚本。
        ```bash
        npm link
        ```
        之后，你就可以在终端中直接运行 `mycli` 命令了。

    *   **全局安装：** 如果你将包发布到 npm，用户可以通过 `npm install -g my-cli-tool` 全局安装它。

6. **执行：**

```bash
mycli
mycli greet
mycli help
```
