## hook
https://legacy.reactjs.org/docs/hooks-intro.html


##  fully controlled and fully uncontrolled component.
https://legacy.reactjs.org/blog/2018/06/07/you-probably-dont-need-derived-state.html

### useRef
Refs let a component hold some information that isn’t used for rendering, like a DOM node or a timeout ID. Unlike with state, updating a ref does not re-render your component

Only run once at initial render,


### useEffect
Run after browser repaint.

useEffect : 初始化的时候执行一次，每当dependency变化的时候执行一次，dependency=[]只在初始化的时候执行一次，no dependency 每次render的时候都会执行。

### useLayoutEffect
Run after render and before browser repaint. You can measure layout here.

React guarantees that the code inside useLayoutEffect and any state updates scheduled inside it will be processed before the browser repaints the screen. This lets you render the tooltip, measure it, and re-render the tooltip again without the user noticing the first extra render. In other words, useLayoutEffect blocks the browser from painting.

The purpose of useLayoutEffect is to let your component use layout information for rendering:

1. Render the initial content.
2. Measure the layout before the browser repaints the screen.
3. Render the final content using the layout information you’ve read.


### useInsertionEffect
fires before React makes changes to the DOM. Libraries can insert dynamic CSS here.

## react-error-boundary
https://github.com/bvaughn/react-error-boundary


## animation
https://legacy.reactjs.org/docs/animation.html

## react router
https://reactrouter.com/en/main
https://v5.reactrouter.com/web/guides/server-rendering
