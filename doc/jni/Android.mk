LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_MODULE    := tag
LOCAL_SRC_FILES := tag.c
include $(BUILD_SHARED_LIBRARY)
 