# أبو طلال — التحليل الرقمي (Android)
مشروع Android لتشغيل محرك Termux المحلي وعرض http://127.0.0.1:8080/ داخل WebView.

## APK
افتح Actions ثم Build Abu Talal APK، وبعد نجاح التشغيل نزّل artifact باسم AbuTalal-debug-apk.

## إعداد Termux (مرة واحدة)
احفظ سكربت start_android.sh في ~/raqami_v2/start_android.sh ثم:
```bash
chmod 700 ~/raqami_v2/start_android.sh
mkdir -p ~/.termux
echo 'allow-external-apps = true' >> ~/.termux/termux.properties
termux-reload-settings
```
يلزم تثبيت Termux من مصدر متوافق ومنح تطبيق أبو طلال إذن RUN_COMMAND. قد تختلف صلاحيات Android حسب نسخة Termux والجهاز.
هذا APK لا يحتوي على Python أو بيانات السوق. يجب أن يكون ~/raqami_v2 موجودًا ويعمل.
تحذير: targetSdk 28 اختيار توافق مبدئي وليس إثبات دعم جميع إصدارات Android. اختبره على جهازك.
