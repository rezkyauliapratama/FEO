package android.rezkyaulia.com.feo.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.rezkyaulia.com.feo.R;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Contract;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static java.lang.Math.round;

/**
 * Created by Shiburagi on 09/07/2016.
 */
public class Utils {
    public long getWarningAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        Timber.e("WARNING ALARM : "+time().getUserFriendlyDateTimeString(calendar.getTimeInMillis()));
        return calendar.getTimeInMillis();
    }

    // Static member class member that holds only one instance of the
    // SingletonExample class
    private static class SingletonHolder {
        public static Utils singletonInstance =
                new Utils();
    }

    // SingletonExample prevents any other class from instantiating
    private Utils() {
    }

    // Providing Global point of access
    @Contract(pure = true)
    public static Utils getInstance() {
        return SingletonHolder.singletonInstance;
    }

    public TimeUtility time() {
        return new TimeUtility();
    }

    public double toMB(long l) {
        return (((double) l) / (double) 1024.0f) / 1024.0f;
    }

    public AppInfo request(String packageName, Context context)
            throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo = manager.getPackageInfo(packageName, 0);
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;

        return extractInfo(manager, packageInfo, applicationInfo);
    }

    private AppInfo extractInfo(PackageManager pm, PackageInfo pi, ApplicationInfo ai) {
        long delta_rx = TrafficStats.getUidRxBytes(ai.uid);
        long delta_tx = TrafficStats.getUidTxBytes(ai.uid);

        final AppInfo appInfo = new AppInfo();
        appInfo.packageName = ai.packageName;
        appInfo.appInstall = time().getDateTimeString(pi.firstInstallTime);
        appInfo.appUpdate = time().getDateTimeString(pi.lastUpdateTime);
        appInfo.updatedDate = time().getDateTimeString();
        appInfo.version = pi.versionName;
        appInfo.uploadSize = toMB(delta_tx);
        appInfo.downloadSize = toMB(delta_rx);

        return appInfo;
    }

    public class AppInfo {
        public int id;
        public String extra;
        @SerializedName("PackageName")
        public String packageName;
        @SerializedName("AppUpdatedDate")
        public String appUpdate;
        @SerializedName("AppInstalledDate")
        public String appInstall;
        @SerializedName("LastUpdatedDate")
        public String updatedDate;
        public String createdDate;
        @SerializedName("InstallFlag")
        public int install;
        @SerializedName("Version")
        public String version;
        public int status;
        @SerializedName("DataSize")
        public double dataSize;
        @SerializedName("CacheSize")
        public double cacheSize;
        public double uploadSize;
        public double downloadSize;
    }


    public float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return round(px);
    }

    public void setStatusBarColor(Activity context) {
        Window window = context.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryTransparent));
        }
    }


    public File getFolder(String questionFileFolder) {
        File folder = Environment.getExternalStorageDirectory();
        String[] folderNames = questionFileFolder.split("/");
        for (String folderName : folderNames) {
            folder = new File(folder, folderName);
            if (!folder.exists())
                folder.mkdirs();
        }

        return folder;
    }


    public List<String> convertStringIntoList(String text) {
        List<String> words = new ArrayList<>();
        try {

            String[] temp = text.replaceAll("\\s+(?=\\p{Punct})", "").trim().split("\\s+");
            for (String str : temp) {
                words.add(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

    public String readTextFile(Context context, Uri uri) {
        InputStream inputStream = null;
        String words = "";
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));

            String line;
            Timber.e("open text file - content" + "\n");
            /*while ((line = reader.readLine()) != null) {
                String[] temp = line.split("\\s+");
                for (String str : temp){
                    words.add(str);
                }
            }*/

            while ((line = reader.readLine()) != null) {
                words = words + "\n" + line;
            }
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;

    }

    public long getMilisWPM(int wpm) {
        long minuteMilis = TimeUnit.MINUTES.toMillis(1);
        long wpmMilis = round(minuteMilis / wpm);

        return wpmMilis;
    }

    public String getWords(int mIndex, int pointWord, int mNol, int mGs, List<String> mWords) {
        String tempWord = "";

        if (pointWord == mIndex && mWords != null) {
            Timber.e("pointWord == mIndex");
            for (int i = 0; i < mNol; i++) {
                Timber.e("For mNol");
                for (int j = 0; j < mGs; j++) {
                    Timber.e("for mGS");
                    if (pointWord >= mWords.size()) {
                        pointWord = 0;
                        Timber.e("Point Word : " + pointWord + " | break GS");
                        break;
                    }

                    tempWord = tempWord + " ".concat(mWords.get(pointWord));
                    Timber.e("tempWord GS:" + tempWord);

                    pointWord++;


                }
                tempWord = tempWord + "\n";
                Timber.e("tempWord NOL:" + tempWord);

                Timber.e("pointwords : " + pointWord);
                if (pointWord >= mWords.size()) {
                    pointWord = 0;
                    Timber.e("Point Word : " + pointWord + " | break NOL");
                    break;
                }


            }
        }
        return tempWord;

    }

    public String getUniqueID(Context context) {
        return getUniqueID(context, "");
//        return uuid.fromString(String.valueOf(deviceId.hashCode())).toString();
    }


    public String getUniqueID(Context context, String i) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString() + System.currentTimeMillis() + i;// + '-' + getDate().replace(' ', '-');
//        try {
//            return new String(encrypt(generateKey(), deviceId.getBytes()),"UTF-8");
//        } catch (Exception e) {
//
//            return deviceId;
//        }


        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
//        return uuid.fromString(String.valueOf(deviceId.hashCode())).toString();
    }



}
