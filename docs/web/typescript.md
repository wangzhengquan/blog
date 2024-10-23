https://www.typescriptlang.org/docs/handbook/utility-types.html

https://www.typescriptlang.org/cheatsheets/

## react-typescript-cheatsheet
https://react-typescript-cheatsheet.netlify.app/docs/advanced/patterns_by_usecase/

https://github.com/typescript-cheatsheets/react


## project
https://daily.dev/blog/npm-tsc-and-typescript-projects#nodejs-and-npm


## React type
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

## React.RefAttributes<HTMLDivElement>

## React.ComponentPropsWithoutRef
```typescript
type PortalProps = React.ComponentPropsWithoutRef<typeof PortalPrimitive>;
```

## CSSStyleDeclaration

```typescript
let style: CSSStyleDeclaration = getComputedStyle(node);
```

