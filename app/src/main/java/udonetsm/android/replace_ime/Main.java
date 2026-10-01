package udonetsm.android.replace_ime;
import android.content.DialogInterface;
import java.util.List;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpp) {
        if (!"android".equals(lpp.packageName)) return;
        XposedBridge.log("replace_ime: loaded in android");
        try {
            Class<?> c = XposedHelpers.findClass(
                    "com.android.server.inputmethod.InputMethodMenuControllerExtImpl",
                    lpp.classLoader);
            XposedBridge.log("replace_ime: class found");
            XposedBridge.hookAllMethods(c, "showInputMethodMenu", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam p) {
                    XposedBridge.log("replace_ime: hook fired");
                    try {
                        List<?> list = (List<?>) p.args[4];
                        int checked = (Integer) p.args[5];
                        DialogInterface.OnClickListener ok =
                                (DialogInterface.OnClickListener) p.args[9];
                        DialogInterface.OnCancelListener cancel =
                                (DialogInterface.OnCancelListener) p.args[10];
                        XposedBridge.log("replace_ime: size=" + (list == null ? -1 : list.size()) + " checked=" + checked);
                        if (list != null && list.size() > 1) {
                            ok.onClick(null, (checked + 1) % list.size());
                        } else {
                            cancel.onCancel(null);
                        }
                        p.setResult(true);
                    } catch (Throwable t) {
                        XposedBridge.log(t);
                    }
                }
            });
        } catch (Throwable t) {
            XposedBridge.log(t);
        }
    }
}