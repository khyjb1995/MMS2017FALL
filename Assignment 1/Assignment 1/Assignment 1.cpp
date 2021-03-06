// Assignment 1.cpp: 定義主控台應用程式的進入點。
//

#include "stdafx.h"
#include <iostream>
#include <istream>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <cmath>

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


int getMatrixValue(int intMatrixSize, int i, int j)
{
	int intMatrix2[2][2] = { { 0, 2 },{ 3, 1 } };
	int intMatrix3[3][3] = { { 0, 7, 3 },{ 6, 5, 2 },{ 4, 1, 8 } };
	int intMatrix4[4][4] = { { 0, 8, 2, 10 },{ 12, 4, 14, 6 },{ 3, 11, 1, 9 },{ 15, 7, 13, 5 } };
	int intMatrix8[8][8] = { { 0, 48, 12, 60, 3, 51, 15, 63 },{ 32, 16, 44, 28, 35, 19, 47, 31 },{ 8, 56, 4, 52, 11, 59, 7, 55 },{ 40, 24, 36, 20, 43, 27, 39, 23 },{ 2, 50, 14, 62, 1, 49, 13, 61 },{ 34, 18, 46, 30, 33, 17, 45, 29 },{ 10, 58, 6, 54, 9, 57, 5, 53 },{ 42, 26, 38, 22, 41, 25, 37, 21 } };
	
	switch (intMatrixSize)
	{
	case 2:
		return intMatrix2[i][j];
		break;
	case 3:
		return intMatrix3[i][j];
		break;
	case 4:
		return intMatrix4[i][j];
		break;
	case 8:
		return intMatrix8[i][j];
		break;
	default:
		break;
	}
}

Mat simpleDithering(Mat matGray, int intMatrixSize)
{
	vector<vector<int>> intMatrix;
	intMatrix.resize(intMatrixSize, vector<int>(intMatrixSize, '0'));

	for (int i = 0; i < intMatrixSize; i++)
	{
		for (int j = 0; j < intMatrixSize; j++)
		{
			intMatrix[i][j] = getMatrixValue(intMatrixSize,i,j);
		}
	}

	Mat matDith(matGray.rows * intMatrixSize,matGray.cols * intMatrixSize,CV_8UC1);
	
	for (int x = 0; x < matGray.rows; x++)
	{
		for (int y = 0; y < matGray.cols; y++)
		{
			uchar gray = matGray.at<Vec3b>(x, y).val[0];	
			for (int i = 0; i < intMatrixSize; i++)
			{
				for (int j = 0; j < intMatrixSize; j++)
				{
					
					uchar intensity;
					if (gray >= (255 / (intMatrixSize*intMatrixSize + 1) * intMatrix[i][j]))
					{
						intensity = 255;
					}
					else
					{
						intensity = 0;
					}
					matDith.at<uchar>(intMatrixSize * x + i, intMatrixSize * y + j) = intensity;
				}
			}
		}
	}
	
	return matDith;
}

Mat implementDithering(Mat matGray, int intMatrixSize)
{
	vector<vector<int>> intMatrix;
	intMatrix.resize(intMatrixSize, vector<int>(intMatrixSize, '0'));

	for (int i = 0; i < intMatrixSize; i++)
	{
		for (int j = 0; j < intMatrixSize; j++)
		{
			intMatrix[i][j] = getMatrixValue(intMatrixSize, i, j);
		}
	}

	Mat matDith(matGray.rows, matGray.cols, CV_8UC1);

	for (int x = 0; x < matGray.rows; x++)
	{
		for (int y = 0; y < matGray.cols; y++)
		{
			uchar gray = matGray.at<Vec3b>(x, y).val[0];
			uchar intensity;
			if ((gray / intMatrixSize * intMatrixSize) > intMatrix[x % intMatrixSize][y % intMatrixSize])
			{
				intensity = 255;
			}
			else
			{
				intensity = 0;
			}
			matDith.at<uchar>(x, y) = intensity;
		}
	}

	return matDith;
}

int main()
{
	Mat matColorImage;
	Mat matGrayImage;
	Mat matDirthering;
	int intMatrixSize;
	string stringImageUrl;

	stringImageUrl = "cute.jpg";
	//cin >> stringImageUrl;
	
	matColorImage = imread(stringImageUrl);
	imshow("Color Image", matColorImage);
	

	matGrayImage = grayConvert(matColorImage);
	imshow("Gray Image", matGrayImage);
	imwrite("Gray Image.jpg", matGrayImage);
	waitKey(0);
	
	matDirthering = simpleDithering(matGrayImage, 2);
	imshow("Simple Dirt Image 2x2", matDirthering);
	imwrite("Simple Dirt Image 2x2.jpg", matDirthering);
	waitKey(0);

	matDirthering = simpleDithering(matGrayImage, 3);
	imshow("Simple Dirt Image 3x3", matDirthering);
	imwrite("Simple Dirt Image 3x3.jpg", matDirthering);
	waitKey(0);

	matDirthering = simpleDithering(matGrayImage, 4);
	imshow("Simple Dirt Image 4x4", matDirthering);
	imwrite("Simple Dirt Image 4x4.jpg", matDirthering);
	waitKey(0);

	matDirthering = simpleDithering(matGrayImage, 8);
	imshow("Simple Dirt Image 8x8", matDirthering);
	imwrite("Simple Dirt Image 8x8.jpg", matDirthering);
	waitKey(0);

	matDirthering = implementDithering(matGrayImage, 2);
	imshow("Implement Dirt Image 2x2", matDirthering);
	imwrite("Implement Dirt Image 2x2.jpg", matDirthering);
	waitKey(0);

	matDirthering = implementDithering(matGrayImage, 3);
	imshow("Implement Dirt Image 3x3", matDirthering);
	imwrite("Implement Dirt Image 3x3.jpg", matDirthering);
	waitKey(0);

	matDirthering = implementDithering(matGrayImage, 4);
	imshow("Implement Dirt Image 4x4", matDirthering);
	imwrite("Implement Dirt Image 4x4.jpg", matDirthering);
	waitKey(0);

	matDirthering = implementDithering(matGrayImage, 8);
	imshow("Implement Dirt Image 8x8", matDirthering);
	imwrite("Implement Dirt Image 8x8.jpg", matDirthering);
	waitKey(0);

    return 0;
}

