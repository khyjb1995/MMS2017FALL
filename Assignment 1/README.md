# Halftone Dithering of Gray-Level Images
## Assignment 1 Report
### Tools
 - Microsoft Visual Studio
 - Github for Desktop
 - OpenCV Library
#
### Assignment Requirements
 - Task 1: Write a program to read images
 - Task 2: Convert color images to gray-level images
 - Task 3: Implement the dithering algorithm
#
### Functions of program
 - Read image from source
 - Write image to destination
 - Show image
 - Convert image from Color-Level to Gray-Level
 - Matrixes build (2x2, 3x3, 4x4 ,8x8)
 - Simple Dithering Gray-Level image
 - Implement Dithering Gray-Level image
#
### Result & Conclusion

#### Read Color-Level Image and Convert image from Color-Level to Gray-Level
When convert Color-Level image to Gray-Level image, I found some reduced on Gray-Level image size. 

![](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%201/Image01.jpg)

#### Two algorithm Dithering
Compare Gray-Level Image to the two algorithm of dithering image, there are different of each size and resolution. 

At simple dithering, will cause image resolution increase and size increase. 

At implement dithering, keep the image resolution and size not changed, but it will lost more detail after dithering. 

#### Simple Dithering Gray-Level image
From result, [Simple Dithering 4x4] image was the most similar to the original image. 

![](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%201/Image02.jpg)
![](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%201/Image03.jpg)

#### Implement Dithering Gray-Level image
From result, [Implement Dithering 8x8] image was the most clearly to the original image.  

![](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%201/Image04.jpg)
![](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%201/Image05.jpg)

Details of each image compare

|  | Type | Resolution | Size on image |
|---|---|---|---|
| Color-Level |  | 399x391 | 59KB |
| Gray-Level |  | 399x391 | 55KB |
| Simple Dithering | 2x2 | 798x782 | 436KB |
| Simple Dithering | 3x3 | 1197x1173 | 1266KB |
| Simple Dithering | 4x4 | 1596x1564 | 2435KB |
| Simple Dithering | 8x8 | 3192x3128 | 8291KB |
| Implement Dithering | 2x2 | 399x391 | 31KB |
| Implement Dithering | 3x3 | 399x391 | 59KB |
| Implement Dithering | 4x4 | 399x391 | 75KB |
| Implement Dithering | 8x8 | 399x391 | 78KB | 
 
#
### References
- [https://en.wikipedia.org/wiki/Ordered_dithering](https://en.wikipedia.org/wiki/Ordered_dithering "Wikipedia Ordered Dithering")
- [http://blog.csdn.net/litingcheng1126/article/details/70768175](http://blog.csdn.net/litingcheng1126/article/details/70768175 "CSDN Install OpenCV over VS2017")
- [http://monkeycoding.com/?p=540](http://monkeycoding.com/?p=540 "MonkeyCoding Image Function")
- [http://ninghang.blogspot.tw/2012/11/list-of-mat-type-in-opencv.html](http://ninghang.blogspot.tw/2012/11/list-of-mat-type-in-opencv.html "NingHang Blog MAT Type")
- [https://docs.opencv.org/3.2.0/d5/d98/tutorial_mat_operations.html](https://docs.opencv.org/3.2.0/d5/d98/tutorial_mat_operations.html "OpenCV MAT Operations")
- [https://docs.opencv.org/3.2.0/d6/d6d/tutorial_mat_the_basic_image_container.html](https://docs.opencv.org/3.2.0/d6/d6d/tutorial_mat_the_basic_image_container.html "OpenCV MAT Container")
- [http://blog.csdn.net/poem_qianmo/article/details/21176257](http://blog.csdn.net/poem_qianmo/article/details/21176257 "CSDN Channel Split")