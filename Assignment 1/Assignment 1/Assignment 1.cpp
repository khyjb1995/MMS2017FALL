// Assignment 1.cpp: 定義主控台應用程式的進入點。
//

#include "stdafx.h"
#include <iostream>
#include <opencv2/core/core.hpp>       // OpenCV 核心库：定义了图像数据结构
#include <opencv2/highgui/highgui.hpp> // highgui: 包含了所有图形接口函数

using namespace std;
using namespace cv; //引入 OpenCV 的命名空间

int main()
{
	// 定义一个图像变量
	Mat matColorImage;

	// 从内存读入一张图像
	matColorImage = imread("C:\\Users\\khy_j\\Dropbox\\National Dong Hwang University\\106-1 Multimedia Systems\\MMS2017FALL\\Assignment 1\\x64\\Debug\\penguin.jpg");
	//"C:\IMG_8049.jpg"
	
	// 将该图像显示在新建的窗口内
	imshow("Color Image", matColorImage);

	// 永远等待按钮
	waitKey(0);

    return 0;
}

