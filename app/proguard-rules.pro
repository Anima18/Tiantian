# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#------------------------------基础配置，通用配置，不要修改------------------------------

#====================代码混淆压缩比，在0~7之间
-optimizationpasses 5
#====================修改包名
-repackageclass ""
#====================忽略访问修饰符，配合上一句使用
-allowaccessmodification
#====================抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#====================不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
-verbose
#====================google推荐算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
#====================避免混淆Annotation、内部类、泛型、匿名类
-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod

#====================保留四大组件，自定义的Application等这些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.view.View

#====================support包
-dontwarn android.support.**
-keep class android.support.** {*;}
-keep interface android.support.**{*;}

#====================databinding包
-dontwarn android.databinding.**
-keep class android.databinding.** {*;}

#====================自定义view的set/get方法和构造方法
-keep public class * extends android.view.View{
  *** get*();
  void set*(***);
  public <init>(android.content.Context);
  public <init>(android.content.Context, android.util.AttributeSet);
  public <init>(android.content.Context, android.util.AttributeSet, int);
}

#====================避免资源混淆
-keep class **.R$* {*;}

#====================避免layout中onclick方法（android:onclick="onClick"）混淆
-keepclassmembers class * extends android.app.Activity{
  public void *(android.view.View);
}

#====================避免回调函数 onXXEvent 混淆
-keepclassmembers class * {
  void *(*Event);
}

#====================避免混淆枚举类
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

#====================Natvie 方法不混淆
-keepclasseswithmembernames class * {
  native <methods>;
}

#====================避免Parcelable混淆
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

#====================避免Serializable接口的子类中指定的某些成员变量和方法混淆
-keepclassmembers class * implements java.io.Serializable {
  static final long serialVersionUID;
  private static final java.io.ObjectStreamField[] serialPersistentFields;
  !static !transient <fields>;
  private void writeObject(java.io.ObjectOutputStream);
  private void readObject(java.io.ObjectInputStream);
  java.lang.Object writeReplace();
  java.lang.Object readResolve();
}

#====================关闭 Log日志
-assumenosideeffects class android.util.Log {
  public static *** d(...);
  public static *** v(...);
  public static *** i(...);
  public static *** e(...);
  public static *** w(...);
}

#====================保留Keep注解的类名和方法
-keep,allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}

#====================WebView混淆配置
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
  public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
  public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
  public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
  public void *(android.webkit.webView, jav.lang.String);
}

#------------------------------第三方库混淆（可以自行增加）------------------------------

#====================RxJava、RxAndroid混淆配置
-dontwarn rx.*
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
  long producerIndex;
  long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
  rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
  rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#====================Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

#====================OkHttp3混淆配置
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
