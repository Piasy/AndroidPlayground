#!/bin/bash
#adb pull /sdcard/converted_dump_640_480.png
#shasum org_dump_640_480.png
#shasum converted_dump_640_480.png
#adb pull /sdcard/converted_dump_640_480.rgb
adb pull /sdcard/converted_dump_640_480.yuv2rgb.jni
adb pull /sdcard/converted_dump_640_480.yuv2rgb.bit
adb pull /sdcard/converted_dump_640_480.yuv2rgb.float
#adb pull /sdcard/converted_dump_640_480.rgb2yuv.jni
#adb pull /sdcard/converted_dump_640_480.rgb2yuv.bit
#adb pull /sdcard/converted_dump_640_480.rgb2yuv.float