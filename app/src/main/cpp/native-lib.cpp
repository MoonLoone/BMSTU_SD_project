#include <jni.h>
#include <string>
#include <android/log.h>
#include <spdlog/spdlog.h>
#include <spdlog/sinks/android_sink.h>

#define LOG_INFO(...) __android_log_print(ANDROID_LOG_INFO, "bmstu_project_ndk", __VA_ARGS__)

#define SLOG_INFO(...) android_logger->info( __VA_ARGS__ )
auto android_logger = spdlog::android_logger_mt("android", "bmstu_project_ndk");

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_bmstu_1project_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    LOG_INFO("Hello from c++ %d", 2021);
    SLOG_INFO("Hello from spdlog {0}", 2021);
    return env->NewStringUTF(hello.c_str());
}