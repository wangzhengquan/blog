## Paths
1. Move To
The 'x y' following uppercase 'M' is absolute values, lowercase 'm' is offset values.
```
M x y
(or)
m dx dy
```
2. Line
draws a line from the current position to a new position
```
L x y
(or)
l dx dy
```
H draws a horizontal line, V draws a vertical line
```
H x
(or)
h dx

V y
(or)
v dy
```

3. Close Path
This command draws a straight line from the current position back to the first point of the path. There is no difference between the uppercase and lowercase command.
```
Z
(or)
z
```

4. Cubic Béziers curve
To create a cubic Bézier, three sets of coordinates need to be specified. The last set of coordinates here (x, y) specify where the line should end. The other two are control points. (x1, y1) is the control point for the start of the curve, and (x2, y2) is the control point for the end. The control points essentially describe the slope of the line starting at each point, then start point and the first control point describe the first slop, the second control point and the end point describe the end slop. The Bézier function then creates a smooth curve that transfers from the slope established at the beginning of the line, to the slope at the other end.
```
C x1 y1, x2 y2, x y
(or)
c dx1 dy1, dx2 dy2, dx dy
```
> **Note:**  The co-ordinate deltas for q are both relative to the previous point (that is, dx2 and dy2 are not relative to dx1 and dy1).

Several Bézier curves can be strung together to create extended, smooth shapes. Often, the control point on one side of a point will be a reflection of the control point used on the other side to keep the slope constant. In this case, a shortcut version of the cubic Bézier can be used, designated by the command S (or s).  It only take two set of coordinates,  the The last set of coordinates here (x, y) specify where the line should end, the first set of coordinates here(x2 y2) specify the control point for the end, the control point for the start was infered from the previouse end slop and this end slop. If the S command doesn't follow another S or C command, then the current position of the cursor is used as the first control point. 

```
S x2 y2, x y
(or)
s dx2 dy2, dx dy
```

5. Quadratic Béziers curve
The other type of Bézier curve, the quadratic curve called with Q, is actually a simpler curve than the cubic one. It requires one control point which determines the slope of the curve at both the start point and the end point. It takes two parameters: the control point and the end point of the curve.
```
Q x1 y1, x y
(or)
q dx1 dy1, dx dy
```
As with the cubic Bézier curve, there is a shortcut for stringing together multiple quadratic Béziers, called with T. This shortcut takes one parameter: the end point of the curve. The control point was infered from the previous slop and this end point. This only works if the previous command was a Q or a T command. If not, then the control point is assumed to be the same as the previous point, and only lines will be drawn.

```
T x y
(or)
t dx dy
```

6. Arcs
At its start, the arc element takes in two parameters for the x-radius and y-radius. The final two parameters designate the x and y coordinates to end the stroke. Together, these four values define the basic structure of the arc.
```
A rx ry x-axis-rotation large-arc-flag sweep-flag x y
a rx ry x-axis-rotation large-arc-flag sweep-flag dx dy
```
## drawing applications

Inkscape: https://inkscape.org



## reference
svgrepo: https://www.svgrepo.com/vectors
https://www.w3.org/TR/SVG/paths.html#PathData

mdn: https://developer.mozilla.org/en-US/docs/Web/SVG/Tutorial/Basic_Shapes
w3c: https://www.w3.org/TR/SVG/Overview.html
advent calendar-themed SVG Tutorial: 	https://svg-tutorial.com/

https://www.w3.org/TR/SVG/coords.html#TransformMatrixDefined

svgprimer:	https://www.w3.org/Graphics/SVG/IG/resources/svgprimer.html

