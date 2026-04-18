# Add project specific ProGuard rules here.
-keepattributes Signature
-keepattributes *Annotation*

# Retrofit
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class com.ruralhealthai.app.data.model.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keepattributes EnclosingMethod
