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




## react events
https://developer.mozilla.org/en-US/docs/Web/API/UI_Events
here's the list of the most common React event types along with their related arguments and their types in TypeScript:

Clipboard Events:
```typescript
onCopy: (event: React.ClipboardEvent<HTMLElement>) => void
onCut: (event: React.ClipboardEvent<HTMLElement>) => void
onPaste: (event: React.ClipboardEvent<HTMLElement>) => void
```
Composition Events:
```typescript
onCompositionEnd: (event: React.CompositionEvent<HTMLElement>) => void
onCompositionStart: (event: React.CompositionEvent<HTMLElement>) => void
onCompositionUpdate: (event: React.CompositionEvent<HTMLElement>) => void
```
Keyboard Events:
```typescript
onKeyDown: (event: React.KeyboardEvent<HTMLElement>) => void
onKeyPress: (event: React.KeyboardEvent<HTMLElement>) => void
onKeyUp: (event: React.KeyboardEvent<HTMLElement>) => void
```
Focus Events:
```typescript
onFocus: (event: React.FocusEvent<HTMLElement>) => void
onBlur: (event: React.FocusEvent<HTMLElement>) => void
````
Form Events:
```typescript
onChange: (event: React.ChangeEvent<HTMLElement>) => void
onInput: (event: React.FormEvent<HTMLElement>) => void
onInvalid: (event: React.FormEvent<HTMLElement>) => void
onReset: (event: React.FormEvent<HTMLElement>) => void
onSubmit: (event: React.FormEvent<HTMLFormElement>) => void
```
Generic Events:
```typescript
onError: (event: React.SyntheticEvent<HTMLElement, Event>) => void
onLoad: (event: React.SyntheticEvent<HTMLElement, Event>) => void
````
Mouse Events:
```typescript
onClick: (event: React.MouseEvent<HTMLElement>) => void
onContextMenu: (event: React.MouseEvent<HTMLElement>) => void
onDoubleClick: (event: React.MouseEvent<HTMLElement>) => void
onDrag: (event: React.DragEvent<HTMLElement>) => void
onDragEnd: (event: React.DragEvent<HTMLElement>) => void
onDragEnter: (event: React.DragEvent<HTMLElement>) => void
onDragExit: (event: React.DragEvent<HTMLElement>) => void
onDragLeave: (event: React.DragEvent<HTMLElement>) => void
onDragOver: (event: React.DragEvent<HTMLElement>) => void
onDragStart: (event: React.DragEvent<HTMLElement>) => void
onDrop: (event: React.DragEvent<HTMLElement>) => void
onMouseDown: (event: React.MouseEvent<HTMLElement>) => void
onMouseEnter: (event: React.MouseEvent<HTMLElement>) => void
onMouseLeave: (event: React.MouseEvent<HTMLElement>) => void
onMouseMove: (event: React.MouseEvent<HTMLElement>) => void
onMouseOut: (event: React.MouseEvent<HTMLElement>) => void
onMouseOver: (event: React.MouseEvent<HTMLElement>) => void
onMouseUp: (event: React.MouseEvent<HTMLElement>) => void
```
Pointer Events:
```typescript
onPointerDown: (event: React.PointerEvent<HTMLElement>) => void
onPointerMove: (event: React.PointerEvent<HTMLElement>) => void
onPointerUp: (event: React.PointerEvent<HTMLElement>) => void
onPointerCancel: (event: React.PointerEvent<HTMLElement>) => void
onGotPointerCapture: (event: React.PointerEvent<HTMLElement>) => void
onLostPointerCapture: (event: React.PointerEvent<HTMLElement>) => void
onPointerEnter: (event: React.PointerEvent<HTMLElement>) => void
onPointerLeave: (event: React.PointerEvent<HTMLElement>) => void
onPointerOver: (event: React.PointerEvent<HTMLElement>) => void
onPointerOut: (event: React.PointerEvent<HTMLElement>) => void
```
Selection Events:
```typescript
onSelect: (event: React.SyntheticEvent<HTMLElement>) => void
```
Touch Events:
```typescript
onTouchCancel: (event: React.TouchEvent<HTMLElement>) => void
onTouchEnd: (event: React.TouchEvent<HTMLElement>) => void
onTouchMove: (event: React.TouchEvent<HTMLElement>) => void
onTouchStart: (event: React.TouchEvent<HTMLElement>) => void
```
UI Events:
```typescript
onScroll: (event: React.UIEvent<HTMLElement>) => void
```
Wheel Events:
```typescript
onWheel: (event: React.WheelEvent<HTMLElement>) => void
```
Media Events:
```typescript
onAbort: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onCanPlay: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onCanPlayThrough: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onDurationChange: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onEmptied: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onEncrypted: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onEnded: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onError: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onLoadedData: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onLoadedMetadata: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onLoadStart: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onPause: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onPlay: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onPlaying: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onProgress: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onRateChange: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onSeeked: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onSeeking: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onStalled: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onSuspend: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onTimeUpdate: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onVolumeChange: (event: React.SyntheticEvent<HTMLMediaElement>) => void
onWaiting: (event: React.SyntheticEvent<HTMLMediaElement>) => void
```
Please note that the type of the event argument in each event handler function may vary depending on the specific event type and the HTML element it's associated with. In the examples above, we've used the most common types provided by the React TypeScript definitions.




