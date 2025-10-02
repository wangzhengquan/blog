## React.Component
React.ReactNode vs JSX.Element vs React.ReactElement: https://www.totaltypescript.com/jsx-element-vs-react-reactnode

JSXElementConstructor == React.ElementType ⊃ React.Component
React.ReactNode ⊃ JSX.Element == React.ReactElement

## [ElementRef](https://www.totaltypescript.com/strongly-type-useref-with-elementref)
```typescript
type OtherComponentRef = ElementRef<typeof OtherComponent>;
const Component = () => {
  const ref = useRef<OtherComponentRef>(null);
  return <OtherComponent ref={ref}>Hello</OtherComponent>;
};

```

## style 

- CSSStyleDeclaration: 当你在 TypeScript 中直接操作 DOM 元素的 style 属性时，它的类型是 CSSStyleDeclaration。例如你通过 `element.style` 来访问它。
```typescript
let style: CSSStyleDeclaration = node.style;

```
或者通过 getComputedStyle 来访问它
```typescript
let style: CSSStyleDeclaration = getComputedStyle(node);
```
- React.CSSProperties : 在 React 中，style prop 的类型是 React.CSSProperties。
```typescript
function Ball({ className = '', style }: { className?: string; style?: React.CSSProperties } {
  return (
    <div />
  );
}
```


## `React.RefAttributes<HTMLDivElement>`

##  React.ComponentProps

```typescript
function TooltipContent({
  className,
  children,
  ...props
}: React.ComponentProps<'div'>) {
  return (
    <div
      className={cn(
        "bg-red",
        className
      )}
      {...props}>
      {children}
    </div>
  )
}
```

```typescript
type PortalProps = React.ComponentPropsWithoutRef<typeof PortalPrimitive>;
```

## React.AllHTMLAttributes

```typescript
export default function Main({
  className = '',
  children,
  ...props
}: React.AllHTMLAttributes<HTMLDivElement>) {
  return (
    <main className={cn('min-h-full p-2 md:p-4', className)} {...props}>
      {children}
    </main>
  );
}
```
