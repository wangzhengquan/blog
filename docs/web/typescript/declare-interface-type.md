在 TypeScript 中，`declare`、`interface` 和 `type` 都用于定义类型，但它们在用途和行为上有一些关键的区别。

### `interface`

`interface` 用于定义对象的结构。它主要用于：

*   **定义对象的形状：** 指定对象应该具有哪些属性以及它们的类型。
*   **实现契约：** 类可以实现接口，以确保它们符合接口定义的结构。
*   **声明函数类型：** 定义函数签名。
*   **可扩展性：** 接口可以合并（Declaration Merging），这在编写库时非常有用，因为它允许用户在现有接口中添加属性。
*   **继承：** 接口可以互相继承。

**示例：**

```typescript
interface Point {
  x: number;
  y: number;
}

interface User {
  id: number;
  name: string;
  email?: string; // 可选属性
}

interface Shape {
  area(): number;
}

class Circle implements Shape {
  constructor(public radius: number) {}
  area() {
    return Math.PI * this.radius * this.radius;
  }
}

// 接口合并示例
interface Widget {
  name: string;
}

interface Widget {
  version: string;
}

const myWidget: Widget = { name: "Foo", version: "1.0" };
```

### `type` (类型别名)

`type` 关键字用于创建类型别名。它非常灵活，可以用于：

*   **定义原始类型别名：** 为字符串、数字等基本类型创建别名。
*   **定义联合类型：** 允许变量可以是多种类型之一。
*   **定义交叉类型：** 将多个类型组合成一个新类型。
*   **定义元组类型：** 具有固定数量和已知类型的元素的数组。
*   **定义函数类型：** 与接口类似，但语法略有不同。
*   **定义泛型类型：** 创建可以与不同类型一起使用的可重用类型。
*   **定义字面量类型：** 限制变量只能是特定的字符串或数字值。

**示例：**

```typescript
type ID = string | number; // 联合类型

type Point3D = {
  x: number;
  y: number;
  z: number;
}; // 对象类型

type Person = {
  name: string;
  age: number;
};

type Employee = Person & {
  employeeId: string;
}; // 交叉类型

type Callback = (message: string) => void; // 函数类型

type Status = "success" | "error" | "pending"; // 字面量联合类型

type StringList = Array<string>; // 泛型类型别名
```

### `declare`

`declare` 关键字用于向 TypeScript 编译器声明已在其他地方（例如在 JavaScript 文件中或通过全局变量）定义但尚未在 TypeScript 类型系统中定义的变量、函数、类或模块。它主要用于：

*   **类型声明文件（.d.ts）：** 它是 `.d.ts` 文件中最常用的关键字，用于描述现有 JavaScript 库的类型信息。
*   **声明全局变量或函数：** 当您在 TypeScript 代码中使用全局变量或函数，但它们没有在当前 TypeScript 项目中定义时。
*   **声明模块：** 当您想为没有类型声明的 JavaScript 模块提供类型信息时。
*   **声明命名空间：** 用于组织全局类型。

`declare` 本身不生成任何 JavaScript 代码。它只是告诉 TypeScript 编译器：“嘿，这个东西存在，这是它的类型。”

**示例：**

```typescript
// declare variable
declare const PI: number;

// declare function
declare function greet(name: string): void;

// declare class
declare class MyUtilityClass {
  constructor();
  doSomething(): void;
}

// declare module
declare module "my-js-library" {
  export function doStuff(): any;
  export const version: string;
}

// declare global (for ambient global declarations)
declare global {
  interface Window {
    myGlobalVar: string;
  }
}
```

### 总结区别

| 特性           | `interface`                                      | `type` (类型别名)                                       | `declare`                                      |
| :------------- | :----------------------------------------------- | :------------------------------------------------------ | :--------------------------------------------- |
| **用途**       | 定义对象结构，实现契约                          | 创建类型别名，更灵活地定义各种类型                    | 声明现有 JS 代码中的类型，不生成代码         |
| **类型范围**   | 主要用于对象、类、函数类型                      | 可以表示任何类型：原始类型、联合、交叉、元组、对象等 | 声明任何现有 JS 实体的类型                    |
| **合并**       | **可以合并**（声明合并）                         | **不能合并**                                            | 不适用，用于声明已存在的实体                 |
| **实现**       | 类可以 `implements` 接口                        | 类不能 `implements` 类型别名（除非类型别名是对象字面量） | 不适用                                         |
| **扩展**       | 可以 `extends` 其他接口，也可以被其他接口 `extends` | 可以通过交叉类型 `&` 或字面量扩展对象类型           | 不适用                                         |
| **递归**       | 允许递归引用（例如，`interface Node { value: number; next: Node; }`） | 允许递归引用                                            | 不适用                                         |
| **生成 JS**    | 不生成任何 JS 代码                               | 不生成任何 JS 代码                                      | 不生成任何 JS 代码                             |

### 何时使用哪个？

*   **优先使用 `interface` 来定义对象的形状。** 它们通常更具表现力，并且支持声明合并，这对于库和插件的扩展性非常有用。
*   **当需要定义联合类型、交叉类型、原始类型别名、元组类型或字面量类型时，使用 `type`。** 它提供了更大的灵活性。
*   **当您需要为现有（非 TypeScript）JavaScript 代码提供类型信息时，使用 `declare`。** 这通常发生在 `.d.ts` 类型声明文件中。

在许多情况下，`interface` 和对象字面量形式的 `type` 可以互换使用。但在 TypeScript 社区中，对于对象的形状定义，`interface` 仍然是首选，因为它具有声明合并的特性。

希望这个解释对您有所帮助！