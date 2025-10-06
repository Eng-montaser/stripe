package com.facebook.react.modules.core;

import android.os.Handler;
import android.os.Looper;

import comm.facebookk.reactt.bridgee.ReadableMap;
import comm.facebookk.reactt.bridgee.WritableMap;

import io.flutter.plugin.common.MethodChannel;

public class DeviceEventManagerModule {
    public static class RCTDeviceEventEmitter {
        private Handler uiThreadHandler = new Handler(Looper.getMainLooper());

        private final MethodChannel channel;

        public RCTDeviceEventEmitter(MethodChannel channel) {
            this.channel = channel;
        }

        public void emit(String eventName, ReadableMap params) {
            uiThreadHandler.post(() -> channel.invokeMethod(eventName, params));
        }
    }
}
