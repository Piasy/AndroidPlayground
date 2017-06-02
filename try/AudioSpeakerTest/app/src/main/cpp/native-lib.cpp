#include <jni.h>
#include <string>
#include <android/log.h>

#define TAG "JNI_TEST"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_github_piasy_audiospeakertest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject instance) {
    __android_log_print(ANDROID_LOG_DEBUG, TAG, "%p", env);
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_github_piasy_audiospeakertest_MainActivity_test(JNIEnv *env, jobject instance,
                                                         jstring s_) {
    const char *s = env->GetStringUTFChars(s_, 0);

    __android_log_print(ANDROID_LOG_DEBUG, TAG, "%p %s", env, s);

    env->ReleaseStringUTFChars(s_, s);
}