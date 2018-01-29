// Assignment 1.cpp: 定義主控台應用程式的進入點。
//

#include "stdafx.h"
#include <iostream>
#include <istream>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <cmath>
#include <ctime>

using namespace std;
using namespace cv;



Mat searchSequential(Mat matRef, Mat matTar, int N, int P)
{

	Mat matResult;

	return matResult;
}

Mat search2DLogarithm(Mat matRef, Mat matTar, int N, int P)
{

	Mat matResult;

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
	pxm_params.push_back(1);

	stringImageUrlRef = "i1.pgm";
	stringImageUrlTar = "i2.pgm";

	intMacroBlockSize = 16;
	intSearchSize = 31;

	//N = MacroBlockSize
	//P = (SearchSize - 1) / 2

	//cin >> stringImageUrl;
	
	matRefPGM = imread(stringImageUrlRef);
	matTarPGM = imread(stringImageUrlTar);
	imshow("Reference", matRefPGM);
	imshow("Target", matTarPGM);
		
	imwrite("i2p.pgm", matRefPGM,pxm_params);
	
	
	waitKey(0);

    return 0;
}

