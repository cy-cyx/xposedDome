package com.xposed;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    String TAG = "Xposed_Hook";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.xposeddome")) return;
        Log.e(TAG, "find package");
        Class<?> clazz = XposedHelpers.findClass("com.xposeddome.MainActivity", lpparam.classLoader);


        XposedHelpers.findAndHookMethod(clazz, "log", String.class, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // 获得参数
                if (param.args.length > 0) {
                    String arg = (String) param.args[0];
                    Toast.makeText((Context) param.thisObject, arg, Toast.LENGTH_SHORT).show();

                    param.args[0] = "改过了";
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) {
            }
        });
    }
}
