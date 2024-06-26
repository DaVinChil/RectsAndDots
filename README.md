# Research of three algorithms for finding out how many rectangles does the point belong to.

In this research, we aim to investigate and compare the performance of three 
distinct algorithms for solving the problem of determining the number of 
rectangles containing a given point. The algorithms under consideration are 
Algorithm A based on brute force enumeration, Algorithm B employing prepared matrix, and Algorithm 
C utilizing a sweep line technique.

By evaluating these algorithms on different sets of nonrandom generated rectangles and query points,
we seek to analyze their computational efficiency and scalability. Understanding 
the strengths and limitations of each algorithm will provide valuable insights into their 
practical utility and help guide the selection of an appropriate approach for real-world applications.

## Algorithms

---------
### Brute Force

Brute force algorithm basically runs through all given rectangles for each point.

- *Preparation Time Complexity = O(1)*
- *Querying Time Complexity = O(n)*
- *Overall Time Complexity = O(nm)*

*n - amount of rectangles,*
*m - amount of points*

---------

### Matrix 

Second algorithm constructing a matrix where each cell represents a number of rectangle contains in area between
(x1, y1) and (x2, y2), where (x, y) is the closest corners of rectangles.

- *Preparation Time Complexity = O(n^3)*
- *Querying Time Complexity = O(logn)*
- *Overall Time Complexity = O(n^3 + mlogn)*

*n - amount of rectangles,*
*m - amount of points*

---------

### Sweep Line 

Third algorithm involves sorting the rectangles based on their x-coordinates and processing them one
by one using a sweep line moving horizontally across the plane.

- *Preparation Time Complexity = O(nlogn)*
- *Querying Time Complexity = O(logn)*
- *Overall Time Complexity = O(nlogn + mlogn)*

*n - amount of rectangles,*
*m - amount of points*

## Testing

----------

### First Approach
For testing, generate *N* number of rectangles with corners in *[i * 10, i * 10]* and *[(N - i) * 10, (N - i) * 10]*,
and points from *2* to *N^2* pieces.

![first](src/main/resources/until_n_2.png)

As we can see, when the number of points less than *N^2* sweep line algorithm is much preferable.
When brute force is faster than matrix approach, yet its increasing tempo is quite high that will make it 
worse even than matrix.

------------

### Second Approach

In the second approach generating same *N* rectangles, but in this case, with points coming up to *N^3* pieces.

![second](src/main/resources/until_n_3.png)

Brute force obviously appears to be highly time-consuming. But in the case of matrix and tree-based algorithms, things 
are getting tricky. Meanwhile, on the paper they should be equal, sweep line algorithm requires more time for execution.
Most probably this is happening due to traversing the tree which is a more complex algorithm than binary search.

### How to run
```cmd
/.mvnw compile -q exec:java
```