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
# --------------------------------------------基本指令区--------------------------------------------#
#-optimizationpasses 5                                                   # 指定代码的压缩级别(在0~7之间，默认为5)
#-dontusemixedcaseclassnames                                             # 是否使用大小写混合(windows大小写不敏感，建议加入)
#-dontskipnonpubliclibraryclasses                                        # 是否混淆非公共的库的类
#-dontskipnonpubliclibraryclassmembers                                   # 是否混淆非公共的库的类的成员
#-dontpreverify                                                          # 混淆时是否做预校验(Android不需要预校验，去掉可以加快混淆速度)
#-verbose                                                                # 混淆时是否记录日志(混淆后会生成映射文件)
#
#-obfuscationdictionary ./proguard-1il.txt
#-classobfuscationdictionary ./proguard-o0O.txt
#-packageobfuscationdictionary ./proguard-socialism.txt
#
## 混淆时所采用的算法(谷歌推荐算法)
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable
#
## 将文件来源重命名为“SourceFile”字符串
#-renamesourcefileattribute abc
#
## 保持注解不被混淆
#-keepattributes *Annotation*
#-keep public class * extends java.lang.annotation.Annotation {*;}
#
## 保持泛型不被混淆
#-keepattributes Signature
## 保持反射不被混淆
#-keepattributes EnclosingMethod
## 保持异常不被混淆
#-keepattributes Exceptions
## 保持内部类不被混淆
#-keepattributes InnerClasses
## 抛出异常时保留代码行号
#-keepattributes SourceFile,LineNumberTable
#
## --------------------------------------------默认保留区--------------------------------------------#
## 保持基本组件不被混淆
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.os.IInterface
#-keep public class * extends android.os.ResultReceiver
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.appwidget.AppWidgetProvider
#-keep public class * extends android.webkit.*{*;}
#-keep public class * extends android.widget.*{*;}
#-keep public class * extends android.app.*{*;}
#
## 保留 NotProguard 注解
#-keep class com.proguard.annotation.NotProguard
#-keep @com.proguard.annotation.NotProguard class * {*;}
#-keepclasseswithmembers class * {
#    @com.proguard.annotation.NotProguard <methods>;
#}
#-keepclasseswithmembers class * {
#    @com.proguard.annotation.NotProguard <fields>;
#}
#-keepclasseswithmembers class * {
#    @com.proguard.annotation.NotProguard <init>(...);
#}
#
## 保持 Google 原生服务需要的类不被混淆
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService
#
## Support 包规则
#-dontwarn android.support.**
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.support.v7.**
#-keep public class * extends android.support.annotation.**
#
## 保持 native 方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## 保留自定义控件(继承自View)不被混淆
#-keep public class * extends android.view.View {
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
## 保留指定格式的构造方法不被混淆
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
## 保留在Activity中的方法参数是view的方法(避免布局文件里面onClick被影响)
#-keepclassmembers class * extends android.app.Activity {
#    public void *(android.view.View);
#}
#
## 保持枚举 enum 类不被混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
## 保持R(资源)下的所有类及其方法不能被混淆
#-keep class **.R$* {*;}
#
## 保持BuldConfig下的所有属性不能被混淆
#-keep class **.BuildConfig {*;}
#
## 保持GlideModelConfig不被混淆
#-keep class * extends com.bumptech.glide.module.AppGlideModule
#-keep class * extends com.bumptech.glide.module.LibraryGlideModule
#
## 保持 Parcelable 序列化的类不被混淆(注：aidl文件不能去混淆)
#-keep class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}
#
## 需要序列化和反序列化的类不能被混淆(注：Java反射用到的类也不能被混淆)
#-keepnames class * implements java.io.Serializable
#
## 保持 Serializable 序列化的类成员不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    !private <fields>;
#    !private <methods>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
## 保持使用 @Keep 注解的类不被混淆
#-dontwarn android.support.annotation.Keep
#-keep @android.support.annotation.Keep class *  {*;}
#
## 保持 BaseAdapter 类不被混淆
#-keep public class * extends android.widget.BaseAdapter {*;}
## 保持 CusorAdapter 类不被混淆
#-keep public class * extends android.widget.CusorAdapter{*;}
#
## --------------------------------------------自定义保留区--------------------------------------------#
#
##-keep public class com.weithink.fengkong.bean.PathInfo{*;}
#-keep class com.weithink.fengkong.WeithinkFengkong {
#    public static WeithinkFengkong getInstance();
#    public void syncData(***);
#}
#-keep class com.weithink.fengkong.bean.** {
#    void set*(***);
#    void set*(int, ***);
#
#    boolean is*();
#    boolean is*(int);
#
#    *** get*();
#    *** get*(int);
#}