package com.example.myapplication.service

import android.accessibilityservice.AccessibilityService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AccControllerService : AccessibilityService() {

    private val TAG = "AccessibilityService"
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event != null && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            val className = event.className?.toString()

            // Log the current package and class name
            Log.d("AccessibilityService", "Current window: $packageName - $className")

            // Check if the user is viewing the App Info page
//            if (isAppInfoPage(className)) {
//                // The user is on the App Info page
//                showOverlayWarning() // Show your overlay or warning
//            } else {
//                // User is in a different activity, you can handle it if needed
//                handleOtherActivity(packageName, className)
//            }
        }
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }
}