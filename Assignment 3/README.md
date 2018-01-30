# Motion Compensation
## Assignment 3 Report
### Tools
 - Microsoft Visual Studio
 - Github for Desktop
 - OpenCV Library
#
### Assignment Requirements
 - Task 1: Find motion vector for every 16x16 macroblock on the two input images using the Sequential Search and the 2D Logarithm Search
 - Task 2: Output predict frame
#
### Functions of program
 - Read PGM image from source
 - Find motion vector
 - Predict image (Sequential Search & 2D Logarithm Search)
 - Write PGM(P2) image to destination
 - Show image
#
### Result & Conclusion
#### Sequential Search

![](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%203/Sequantial_Search.PNG)

#### 2D Logarithm Search
![](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%203/2D_Logarithm_Search.PNG)

#### Details of two search compare

|  | MacroBlock | SearchSize | Speed | SNR |
|---|---|---|---|---|
| Sequential Search | 16x16 | 31x31 | 24s | None |
| 2D Logarithm Search |  |  |  |  |
|  |  |  |  | |
|  |  |  |  | |
|  |  |  |  | |
|  |  |  |  | |

- [Reference PGM](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%203/x64/Debug/i1.pgm "i1.pgm")
- [Target PGM](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%203/x64/Debug/i2.pgm "i2.pgm")
- [Predict(Sequential) PGM](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%203/x64/Debug/i2p(SS).pgm "i2p(SS).pgm")
- [Predict(2D Logarithm) PGM](https://github.com/khyjb1995/MMS2017FALL/blob/master/Assignment%203/x64/Debug/i2p(2D).pgm "i2p(2D).pgm")