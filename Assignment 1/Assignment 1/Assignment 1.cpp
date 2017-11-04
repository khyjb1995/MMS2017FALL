// Assignment 1.cpp: 定義主控台應用程式的進入點。
//

#include "stdafx.h"
#include <iostream>
#include <istream>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>

using namespace std;
using namespace cv;

Mat grayConvert(Mat matColor)
{
	Mat matGray = matColor;

	for (int x = 0; x < matColor.rows; x++)
	{
		for (int y = 0; y < matColor.cols; y++)
		{
			Vec3b intensity = matColor.at<Vec3b>(x, y);
			uchar blue = intensity.val[0];
			uchar green = intensity.val[1];
			uchar red = intensity.val[2];
			uchar gray = (blue + green + red) / 3;
			intensity.val[0] = gray;
			intensity.val[1] = gray;
			intensity.val[2] = gray;
			matGray.at<Vec3b>(x, y) = intensity;
		}
	}
	return matGray;
}



Mat simpleDithering(Mat matGray)
{
	Mat matDith = matGray;
	for (int x = 0; x < matGray.cols; x += 3)
	{
		for (int y = 0; y < matGray.rows; y += 3)
		{
			for (int i = 0;;)
			{
				for (int j = 0;;)
				{

				}
			}
		}
	}

	return matDith;
}

int main()
{
	Mat matColorImage;
	//Mat matGrayImage;
	string stringImageUrl;

	stringImageUrl = "C:\\Users\\khy_j\\Dropbox\\National Dong Hwang University\\106-1 Multimedia Systems\\MMS2017FALL\\Assignment 1\\x64\\Debug\\cute.jpg";
	//cin >> stringImageUrl;

	matColorImage = imread(stringImageUrl);
	imshow("Color Image", matColorImage);
	
	Mat matGrayImage = Mat(matColorImage.rows * 2, matColorImage.cols * 2, CV_8UC1);

	matGrayImage = grayConvert(matColorImage);
	imshow("Gray Image", matGrayImage);


	waitKey(0);

    return 0;
}

