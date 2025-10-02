## shape
```css
div {
  width: 100px;
  height: 100px;
  background: conic-gradient(
    hsl(360 100% 50%),
    hsl(315 100% 50%),
    hsl(270 100% 50%),
    hsl(225 100% 50%),
    hsl(180 100% 50%),
    hsl(135 100% 50%),
    hsl(90 100% 50%),
    hsl(45 100% 50%),
    hsl(0 100% 50%)
  );
  clip-path: circle(closest-side);
}
```

##
```css
.space-y-4 > :not([hidden]) ~ :not([hidden]) {
   --tw-space-y-reverse: 0;
   margin-top: calc(1rem * calc(1 - var(--tw-space-y-reverse)));
   margin-bottom: calc(1rem * var(--tw-space-y-reverse));
} 
```

- `>`: 这是直接子代选择器。它意味着我们只选择 .space-y-4 元素的直接子元素。
- `~`: 这是 后续兄弟组合器 (General Sibling Combinator)。A ~ B 会选择所有在元素 A 之后，并且与 A 拥有相同父元素的 B 元素。

## center
```css

button {
    display: grid;
    place-items: center;
}
```

## box-sizing
```css
box-sizing: border-box | content-box;
```


## margin-block
```css
margin-block-start: 20px; 
margin-block-end: 20px; 
```

## aspect-ratio

```css
aspect-ratio: 1 / 1;
```

## object-fit
The `object-fit` CSS property sets how the content of a replaced element, such as an `<img>` or `<video>`, should be resized to fit its container.

## object-position
The object-position CSS property specifies the alignment of the selected replaced element's contents within the element's box. Areas of the box which aren't covered by the replaced element's object will show the element's background.


## CSS box model

https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_box_model/Introduction_to_the_CSS_box_model

### shape-outside
The shape-outside CSS property defines a shape—which may be non-rectangular—around which adjacent inline content should wrap. By default, inline content wraps around its margin box; shape-outside provides a way to customize this wrapping, making it possible to wrap text around complex objects rather than simple boxes.

## resize
set element resizeble
```
resize: both;
```

### clip
background-clip
overflow-clip-margin

## text

1. text direction
```css
writing-mode: vertical-rl;
```
2. text capital
```css
text-transform: uppercase;
```

3. text wrap
```css
white-space: nowrap;
overflow: hidden;
text-overflow: ellipsis;
```

## line-height
```css
line-height: 1.2;
```

## grid
https://developer.chrome.com/docs/devtools/css/grid

## table
```css
border-collapse
border-spacing
```

## query
1. @supports
```css
/* https://webkit.org/blog/10042/wide-gamut-color-in-css-with-display-p3/ */
:root {
  --bright-green: rgb(0, 255, 0);
}
/* Display-P3 color, when supported. */
@supports (color: color(display-p3 1 1 1)) {
  :root {
    --bright-green: color(display-p3 0 1 0);
  }
}
```

## justify  align

justify 是在横轴方向调整， align是在纵轴方向调整
content 是对整个空间的分配，items是单元格内的对齐

## animation
offset: https://developer.mozilla.org/en-US/docs/Web/CSS/offset

## function
https://developer.mozilla.org/en-US/docs/Web/CSS/sin



## Reference
CSS_flow_layout: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_flow_layout

The CSS Zen Garden: https://www.csszengarden.com/

An Interactive Guide to Flexbox: https://www.joshwcomeau.com/css/interactive-guide-to-flexbox/
CSS center: https://www.joshwcomeau.com/css/center-a-div/
Layout Algorithms:  https://www.joshwcomeau.com/css/understanding-layout-algorithms/
