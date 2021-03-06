// Assignment 1.cpp: 定義主控台應用程式的進入點。
//

#include "stdafx.h"
#include <iostream>
#include <istream>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <cmath>
#include <ctime>
#include <cfloat>

using namespace std;
using namespace cv;

double MAD(Mat matRef, Mat matTar, int x, int y, int i, int j, int intMacroBlockSize)
{
	double refPixel, tarPixel;
	double Mad = 0;
	for (int k = 0; k < intMacroBlockSize; k++)
	{
		for (int l = 0; l < intMacroBlockSize; l++) {
			//cout << "VecX: " << x + i + k << endl << "VecY: " << y + j + l << endl;
			
			refPixel = (double)matRef.at<uchar>(x + i + k, y + j + l);
			tarPixel = (double)matTar.at<uchar>(x + k, y + l);

			Mad = Mad + abs(tarPixel - refPixel);
		}
	}
	Mad = Mad / pow(intMacroBlockSize, 2);;

	return Mad;
}

Mat searchSequential(Mat matRef, Mat matTar, int N, int P)
{
	clock_t startTime, endTime;
	Mat matResult(matRef.rows, matRef.cols, IMREAD_GRAYSCALE);
	vector<int> motionVectorX, motionVectorY;
	int vecX, vecY;
	double current, min;

	startTime = clock();

	motionVectorX.clear();
	motionVectorY.clear();

	for (int x = 0; x < matRef.rows; x += N)
	{
		for (int y = 0; y < matRef.cols; y += N)
		{
			min = DBL_MAX;
			for (int i = -P; i <= P; i++)
			{
				for (int j = -P; j <= P; j++)
				{
					if (x < 0 || x >= matRef.rows || y < 0 || y >= matRef.cols || (x + i) < 0 || (x + i) >= matRef.rows || (y + j) < 0 || (y + j) >= matRef.cols)
					{
						continue;
					}
					else
					{
						if ((matRef.rows - x) < N || (matRef.cols - y) < N || (matRef.rows - (x + i)) < N || (matRef.cols - (y + j)) < N)
						{
							continue;
						}
						current = MAD(matRef, matTar, x, y, i, j, N);
						if (current < min)
						{
							min = current;
							vecX = i;
							vecY = j;
						}
					}
				}
			}
			motionVectorX.push_back(vecX);
			motionVectorY.push_back(vecY);
		}
	}

	endTime = clock();
	cout << "Sequential Search Speed: (" << (endTime - startTime)/CLOCKS_PER_SEC << " Seconds)" << endl;

	
	int count = 0;
	for (int x = 0; x < matRef.rows; x += N)
	{
		for (int y = 0; y < matRef.cols; y += N)
		{
			int ref_x = x + motionVectorX[count];
			int ref_y = y + motionVectorY[count];
			count++;

			for (int i = 0; i < N; i++)
			{
				for (int j = 0; j < N; j++)
				{
					matResult.at<uchar>(x + i, y + j) = (int)matRef.at<uchar>(ref_x + i, ref_y + j);
				}
			}
		}
	}
	return matResult;
}

Mat search2DLogarithm(Mat matRef, Mat matTar, int N, int P)
{
	clock_t startTime, endTime;
	Mat matResult(matRef.rows, matRef.cols, IMREAD_GRAYSCALE);
	vector<int> motionVectorX, motionVectorY, currentVecX, currentVecY;
	int vecX, vecY, currentX, currentY, last, offset, u, v;
	double current, min;

	startTime = clock();
	motionVectorX.clear();
	motionVectorY.clear();

	

	endTime = clock();
	cout << "2D Logarithm Search Speed: " << (endTime - startTime) / CLOCKS_PER_SEC << " Seconds)" << endl;

	int count = 0;
	for (int x = 0; x < matRef.rows; x += N)
	{
		for (int y = 0; y < matRef.cols; y += N)
		{
			int ref_x = x + motionVectorX[count];
			int ref_y = y + motionVectorY[count];
			count++;

			for (int i = 0; i < N; i++)
			{
				for (int j = 0; j < N; j++)
				{
					matResult.at<uchar>(x + i, y + j) = (int)matRef.at<uchar>(ref_x + i, ref_y + j);
				}
			}
		}
	}

	return matResult;
}

int main()
{
	Mat matRefPGM;
	Mat matTarPGM;
	Mat matPredPGM;
	int intMatrixSize;
	int intMacroBlockSize;
	int intSearchSize;
	string stringImageUrlTar;
	string stringImageUrlRef;

	vector<int> pxm_params;
	pxm_params.push_back(CV_IMWRITE_PXM_BINARY);
	pxm_params.push_back(0);

	stringImageUrlRef = "i1.pgm";
	stringImageUrlTar = "i2.pgm";

	intMacroBlockSize = 16;
	intSearchSize = 31;

	//N = MacroBlockSize
	//P = (SearchSize - 1) / 2

	//cin >> stringImageUrl;
	
	matRefPGM = imread(stringImageUrlRef, IMREAD_GRAYSCALE);
	matTarPGM = imread(stringImageUrlTar, IMREAD_GRAYSCALE);
	//cout << matRefPGM.type();
	imshow("Reference", matRefPGM);
	imshow("Target", matTarPGM);
	matPredPGM = searchSequential(matRefPGM, matTarPGM, intMacroBlockSize, (intSearchSize - 1) / 2);
	imshow("Predict", matPredPGM);
	imwrite("i2p(SS).pgm", matPredPGM,pxm_params);
	
	
	waitKey(0);

    return 0;
}

