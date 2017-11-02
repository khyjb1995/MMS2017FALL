// Assignment 1.cpp: 定義主控台應用程式的進入點。
//

#include "stdafx.h"
#include <iostream>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>

using namespace std;
using namespace cv;

int main()
{
	Mat matColorImage;

	matColorImage = imread("C:\\Users\\khy_j\\Dropbox\\National Dong Hwang University\\106-1 Multimedia Systems\\MMS2017FALL\\Assignment 1\\x64\\Debug\\penguin.jpg");
	imshow("Color Image", matColorImage);

	Mat matGrayImage = matColorImage;

	for (int x = 0; x < matColorImage.rows; x++)
	{
		for (int y = 0; y < matColorImage.cols; y++)
		{
			Vec3b intensity = matColorImage.at<Vec3b>(x, y);
			uchar blue = intensity.val[0];
			uchar green = intensity.val[1];
			uchar red = intensity.val[2];
			uchar gray = (blue + green + red) / 3;
			intensity.val[0] = gray;
			intensity.val[1] = gray;
			intensity.val[2] = gray;
			matGrayImage.at<Vec3b>(x, y) = intensity;
		}
	}

	imshow("Gray Image", matGrayImage);

	waitKey(0);

    return 0;
}

