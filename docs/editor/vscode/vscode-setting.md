## 设置 Tab 为 2 个空格
### 方法 1：通过设置界面（GUI）
1. 打开 VS Code，点击左下角的 ⚙️ 设置图标（或使用快捷键 `Ctrl / Cmd + ,`）
2. 在搜索栏输入 tabsize，找到以下两个选项：
- Editor: Tab Size → 设置为 2
- Editor: Insert Spaces → 勾选（确保按 Tab 时插入空格而非制表符）

### 方法 2：直接修改 settings.json 文件
1. 打开 VS Code 设置文件：
按下 `Ctrl / Cmd + Shift + P` ，输入 Open Settings (JSON) 并选择。

2. 在 settings.json 中添加或修改以下内容：
```json
{
    "editor.tabSize": 2,
    "editor.insertSpaces": true,
    "editor.detectIndentation": false,
    "[python]": {
      "editor.tabSize": 4,
      "editor.insertSpaces": true
    }
}
```