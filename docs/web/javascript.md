## Guide
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Using_classes
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Inheritance_and_the_prototype_chain
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/new#description
## coordinate and size
- getBoundingClientRect
- offsetHeight: https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/offsetHeight
- clientHeight: https://developer.mozilla.org/en-US/docs/Web/API/Element/clientHeight
- window.innerHeight and window.outerHeight
- IntersectionObserver
```javascript
useEffect(() => {
    const div = ref.current;
    const observer = new IntersectionObserver(entries => {
      const entry = entries[0];
      if (entry.isIntersecting) {
        document.body.style.backgroundColor = 'black';
        document.body.style.color = 'white';
      } else {
        document.body.style.backgroundColor = 'white';
        document.body.style.color = 'black';
      }
    }, {
       threshold: 1.0
    });
    observer.observe(div);
    return () => {
      observer.disconnect();
    }
  }, []);
```



## scroll
- scrollHeight:
- element.scrollTop / window.scrollY: 
```javascript
element.scrollHeight == element.clientHeight + element.scrollTop
```
scrollTop is a non-rounded number, while scrollHeight and clientHeight are rounded — so the only way to determine if the scroll area is scrolled to the bottom is by seeing if the scroll amount is close enough to some threshold (in this example 1):
```javascript
(element.scrollHeight - element.clientHeight - element.scrollTop) <= 1;

```
- scrollBy(): https://developer.mozilla.org/en-US/docs/Web/API/Window/scrollBy
The `scrollBy()` method scrolls the document in the window by the given amount.
```javascript
element.scrollBy({
  top: 100,
  left: 100,
  behavior: "smooth",
});
```

- scrollTo():  https://developer.mozilla.org/en-US/docs/Web/API/Window/scrollTo
`scrollTo()` scrolls to a particular set of coordinates in the document.
```javascript
element.scrollTo({
  top: 100,
  left: 100,
  behavior: "smooth",
});

```

- scrollIntoView: https://developer.mozilla.org/en-US/docs/Web/API/Element/scrollIntoView
The Element interface's scrollIntoView() method scrolls the element's ancestor containers such that the element on which scrollIntoView() is called is visible to the user.
```javascript
const imgNode = listNode.querySelectorAll('li > img')[index];
imgNode.scrollIntoView({
  behavior: 'smooth',
  block: 'nearest',
  inline: 'center'
});
```

## resize
- resizeTo
- resizeBy
- ResizeObserver
```javascript
const parentBox = document.querySelector('[data-element="parent-box"]');
const heightLabel = document.querySelector('[data-element="height-label"]');
const widthLabel = document.querySelector('[data-element="width-label"]');

// Update the size labels on resize
const observer = new ResizeObserver((observedItems) => {
  const { borderBoxSize } = observedItems[0];

  widthLabel.innerText = `${Math.round(borderBoxSize[0].inlineSize)}px`;
  heightLabel.innerText = `${Math.round(borderBoxSize[0].blockSize)}px`;
});

observer.observe(parentBox);
```

## MutationObserver
```javascript
const callback = function(mutationsList, observer) {
  for (const mutation of mutationsList) {
    if (mutation.type === 'childList') {
      console.log('A child node has been added or removed.');
    } else if (mutation.type === 'characterData') {
      console.log('The text content of a node has changed.');
    } else if (mutation.type === 'attributes') {
      console.log('The attributes of the node have changed.');
    }
  }
};
const observer = new MutationObserver(callback);

// Start observing the target element for changes
observer.observe(this.container, { 
  childList: true,  // Monitor child elements
  subtree: true,    // Monitor entire subtree
  characterData: true, // Monitor text content
  attributes: true,  // Monitor attribute changes
});
```

## modify
- insertBefore
- appendChild
- removeChild

## content
innerHTML
outerHTML
textContent

## formData
```
const formData = new FormData(e.target);
const orderDetails = {
  ...Object.fromEntries(formData),
  count,
};
```

## time
```
performance.now()
Date.now()
```

## animation
requestAnimationFrame
KeyframeEffect : https://developer.mozilla.org/en-US/docs/Web/API/KeyframeEffect
Element: animate() : https://developer.mozilla.org/en-US/docs/Web/API/Element/animate

## Object.is 
比较两个对象是否一样

## Regular expressions
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Regular_expressions

## Accessible Rich Internet Applications(ARIA)
https://developer.mozilla.org/en-US/docs/Web/Accessibility/ARIA

## events
https://developer.mozilla.org/en-US/docs/Web/Events/Creating_and_triggering_events
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Event_loop

## Destructuring_assignment
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment

## Expression_statement
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/Expression_statement
## Iteration_protocols
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Iteration_protocols
## Object_initializer
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Object_initializer

## 特数语法
```javascript
// invoke onComplete if it exit  
onComplete?.(); 
```

## books
ou Don't Know JS : https://github.com/getify/You-Dont-Know-JS/tree/2nd-ed


## reference
[v8](https://v8.dev/blog)