#include <string.h>
#include <jni.h>
#include "com_younglee_retrofittest_safe_Tag.h"

JNIEXPORT jstring JNICALL Java_com_younglee_retrofittest_safe_Tag_getTag(JNIEnv* env, jclass cls,
		jboolean debug) {
	jmethodID mid = (*env)->GetStaticMethodID(env, cls, "callback", "()V");
	if (mid == NULL) {
		return (*env)->NewStringUTF(env, "permission denied");
	}
	(*env)->CallStaticVoidMethod(env, cls, mid);

	jfieldID fid = (*env)->GetStaticFieldID(env, cls, "sTag",
			"Ljava/lang/String;");
	if (fid == NULL) {
		return (*env)->NewStringUTF(env, "permission denied");
	}

	jstring jstr = (*env)->GetStaticObjectField(env, cls, fid);
	const char *str = (*env)->GetStringUTFChars(env, jstr, NULL);
	if (str == NULL) {
		return (*env)->NewStringUTF(env, "permission denied");
	}
	if (!(strcmp(str, "com.younglee.retrofittest") == 0) ){
		return (*env)->NewStringUTF(env, "permission denied");
		//return (*env)->NewStringUTF(env, str);
	}
	(*env)->ReleaseStringUTFChars(env, jstr, str);

	return (*env)->NewStringUTF(env,
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAg3Ag75M4ZBcOjnjFKBAp+FuLTHI8h6kh0/rrh7qfjBJvUrm0wTM7qFT3XMfYZc00PdIH1e0Jjcmp2ayEtk2rlxHUHJTtL8loNMxsLRrqEVhc6q3Co8+YOytanckqBHwSqq0Vk6TfKoXwDZfrWSbrRUpLrepGoXdOl6gjoOQmYQIDAQAB");
}
