#include <jni.h>

#include <opencv2/opencv.hpp>

//using namespace cv;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_realopencv_MainActivity_ConvertRGBtoGray(JNIEnv *env, jobject thiz,
                                                          jlong mat_addr_input,
                                                          jlong mat_addr_result) {
    // TODO: implement ConvertRGBtoGray()
    cv::Mat &matInput = *(cv::Mat *)mat_addr_input;
    cv::Mat &matResult = *(cv::Mat *)mat_addr_result;

    cvtColor(matInput, matResult, cv::COLOR_RGBA2BGRA);

}