## 1. 初始化你的项目（如果还没有的话）：
```bash
npm init -y
```

## 2. 安装 Chai：
```bash
npm install chai mocha # Mocha 通常与 Chai 一起使用
```

## 3. 完成后的 `package.json` 文件

```json
{
  "name": "my-test-project",
  "version": "1.0.0",
  "description": "",
  "main": "index.js",
  "type": "module",   
  "scripts": {
    "test": "mocha 'test/**/*-test.js'",
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "devDependencies": {
    "chai": "^4.3.4",
    "mocha": "^9.1.3"
  }
}
```
- `"type": "module"`：项目被配置为使用 ES Modules，这意味着你可以使用 **ES Modules** (ECMAScript Modules) 语法，例如使用`import` 而不是 `require` 来导入 Chai。

## 4.  **创建你的测试文件（例如 `test/my-test.js`）：**

```javascript
// test.js
import { should } from 'chai'; // 直接导入 should
should(); // 调用 should 函数来扩展 Object.prototype

describe('Array', function () {
  describe('#indexOf()', function () {
    it('should return -1 when the value is not present', function () {
      [1, 2, 3].indexOf(5).should.equal(-1);
      [1, 2, 3].indexOf(0).should.equal(-1);
    });
  });
});
```

或者如果你想使用 `expect` 风格：
```javascript
// test.js
import { expect } from 'chai';

describe('Array', function () {
  describe('#indexOf()', function () {
    it('should return -1 when the value is not present', function () {
      expect([1, 2, 3].indexOf(5)).to.equal(-1);
      expect([1, 2, 3].indexOf(0)).to.equal(-1);
    });
  });
});
```

## 5. 运行你的测试：
```bash
npm test
```


## 注意事项：

*   **Chai `should()` 的调用方式：** 当你使用 `import { should } from 'chai';` 时，你需要显式地调用 `should()` 函数来激活它的 BDD 接口，这样 `Object.prototype` 才能被扩展，从而允许你使用 `someValue.should.equal(...)` 这样的语法。
