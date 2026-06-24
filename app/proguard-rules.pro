# Comprehensive ProGuard rules for Codeboard
# These rules ensure that all necessary classes are preserved to prevent installation and runtime issues.

# Keep application classes and their public members to ensure stability after minification
-keep class com.gazlaws.codeboard.** { *; }

# Keep Input Method Service related classes (Crucial for Keyboard Apps)
-keep class * extends android.inputmethodservice.InputMethodService
-keep class * extends android.inputmethodservice.InputMethodService$InputMethodImpl

# AndroidX and Material components (common rules to prevent issues with reflection and inflation)
-keep class androidx.appcompat.** { *; }
-keep class com.google.android.material.** { *; }
-keep class androidx.preference.** { *; }
-keep class androidx.core.** { *; }
-keep class androidx.annotation.** { *; }

# AppIntro specific rules
-keep class com.github.appintro.** { *; }

# Color Picker rules
-keep class com.github.evilbunny2008.** { *; }

# General Android support and lifecycle classes
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# Keep class members for Javascript interface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Preserve R classes for resource lookups
-keep class **.R$* { *; }

# Handle View related rules for XML inflation and property animation
-keepclassmembers class * extends android.view.View {
   public <init>(android.content.Context);
   public <init>(android.content.Context, android.util.AttributeSet);
   public <init>(android.content.Context, android.util.AttributeSet, int);
   public void set*(...);
}

# Kotlin specific rules for hybrid support
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-dontwarn org.jetbrains.kotlin.**

# Suppress warnings from common libraries that may be safely ignored during minification
-dontwarn androidx.**
-dontwarn com.google.android.material.**
