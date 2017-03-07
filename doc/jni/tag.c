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
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCigwlB4DdiofJiedeeCNjPcFAmlq4wH7r9qt9dGEcvQJXRBrc0ce3M2PM7dc/Da3ZY7nRUrSCqbAjpnNsXqR8ehUOpCDr/cCczuBwnLQ1Iaxqvj59IY7IoXaQedH4/81lnVRFkDutphfC+CsA9oimxPenzPB7VG5D9yAv18BLXHwIDAQAB");
}
