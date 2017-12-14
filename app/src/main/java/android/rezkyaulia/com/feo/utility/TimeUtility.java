package android.rezkyaulia.com.feo.utility;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.rezkyaulia.com.feo.R;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rezky Aulia Pratama on 7/18/2017.
 */

public class TimeUtility {

    public TimeUtility() {
    }

    public String getDateString() {
        return getDateString(Calendar.getInstance());
    }

    public String getDateString(Calendar calendar) {
        return getDateString(calendar.getTime());
    }

    public String getDateTimeStringWithoutT() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return getDateTimeStringWithoutT(calendar.getTime());
    }
    public String getDateTimeStringWithoutT(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }

    public String getDateString(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.format(calendar);
    }

    public String getDateTimeString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return getDateTimeString(calendar);
    }

    public String getDateTimeString(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return getDateTimeString(calendar);
    }

    public String getDateTimeString(TimePicker timePicker) {

        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hour, minute);

        return getDateTimeString(calendar);
    }

    public String getDateTimeString(DatePicker datePicker, TimePicker timePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return getDateTimeString(calendar);
    }

    public String getDateTimeString(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return getDateTimeString(calendar);
    }


    public String getDateTimeString(Calendar calendar) {

        return getDateTimeString(calendar.getTime());
    }

    public String getDateTimeString(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }

    public String getUserDateString(String dateString) {

        Date date = parseDate(dateString);

        return getUserDateString(date);

    }

    public String getUserDateString() {

        Date date = Calendar.getInstance().getTime();

        return getUserDateString(date);

    }

    public String getUserDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        return format.format(date);
    }

    public String getUserDateWithTimeString(String dateString) {

        Date date = parseDate(dateString);

        return getUserDateWithTimeString(date);

    }

    public String getLastUpdate(String packageName, Context context) {
        try {
            return Utils.getInstance().request(packageName, context).appUpdate;
        } catch (PackageManager.NameNotFoundException e) {
            return getDateTimeString();
        }
    }

    public String getUserDateWithTimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyy  hh:mm:ss", Locale.getDefault());

        return format.format(date);
    }

    public String getDateForFilename() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return getDateForFilename(calendar);
    }

    public String getDateForFilename(Calendar calendar) {

        return getDateForFilename(calendar.getTime());
    }

    public String getDateForFilename(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssS", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }

    public String getUserTime(long datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(datetime);
        return getUserTime(calendar);
    }

    private String getUserTime(Calendar calendar) {
        return getUserTime(calendar.getTime());
    }

    private String getUserTime(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return simpleDateFormat.format(time);
    }


    public String getUserFriendlyDateTimeString() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return getUserFriendlyDateTimeString(calendar);
    }

    public String getUserFriendlyDateTimeString(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return getUserFriendlyDateTimeString(calendar);
    }

    public String getUserFriendlyDateTimeString(DatePicker datePicker, TimePicker timePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return getUserFriendlyDateTimeString(calendar);
    }

    public String getUserFriendlyDateTimeString(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return getUserFriendlyDateTimeString(calendar);
    }


    public String getUserFriendlyDateTimeString(Calendar calendar) {

        return getUserFriendlyDateTimeString(calendar.getTime());
    }

    public String getUserFriendlyDateTimeString(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }

    public String getUserFriendlyDuration(Context context, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return getUserFriendlyDuration(context, calendar);
    }

    public String getUserFriendlyDuration(Context context, Calendar calendar) {

        Calendar currentCalendar = Calendar.getInstance();

        String displayTime = "";
        long diff = currentCalendar.getTimeInMillis() - calendar.getTimeInMillis();
        if (TimeUnit.MILLISECONDS.toDays(diff) == 0) {
            if (TimeUnit.MILLISECONDS.toHours(diff) == 0) {
                if (TimeUnit.MILLISECONDS.toMinutes(diff) == 0) {
                    if (TimeUnit.MILLISECONDS.toSeconds(diff) <= 1)
                        displayTime = context.getResources().getString(R.string.asecondago);
                    else
                        displayTime = String.format(Locale.getDefault(),
                                context.getResources().getString(R.string.secondsago),
                                TimeUnit.MILLISECONDS.toSeconds(diff));
                } else if (TimeUnit.MILLISECONDS.toMinutes(diff) == 1) {
                    displayTime = context.getResources().getString(R.string.aminuteago);
                } else
                    displayTime = String.format(Locale.getDefault(),
                            context.getResources().getString(R.string.minutesago),
                            TimeUnit.MILLISECONDS.toMinutes(diff));
            } else if (TimeUnit.MILLISECONDS.toHours(diff) == 1) {
                displayTime = context.getResources().getString(R.string.anhourago);
            } else
                displayTime = String.format(Locale.getDefault(),
                        context.getResources().getString(R.string.hoursago),
                        TimeUnit.MILLISECONDS.toHours(diff));
        } else if (TimeUnit.MILLISECONDS.toDays(diff) == 1) {
            displayTime = context.getResources().getString(R.string.yesterday);
        } else
            displayTime = String.format(Locale.getDefault(),
                    context.getResources().getString(R.string.daysago),
                    TimeUnit.MILLISECONDS.toDays(diff));

        return displayTime;
    }


    public String getUserFriendlyDateString(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return getUserFriendlyDateString(calendar);
    }

    public String getUserFriendlyDateString(Calendar calendar) {

        return getUserFriendlyDateString(calendar.getTime());
    }

    public String getUserFriendlyDateString(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }


    public String getUserFriendlyTimeString(TimePicker timePicker) {

        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hour, minute);

        return getUserFriendlyTimeString(calendar);
    }

    public String getUserFriendlyTimeString(Calendar calendar) {

        return getUserFriendlyTimeString(calendar.getTime());
    }

    public String getUserFriendlyTimeStringA(Calendar calendar) {

        return getUserFriendlyTimeStringA(calendar.getTime());
    }

    public String getUserFriendlyTimeString(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }

    public String getUserFriendlyTimeStringA(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }


    public String getTimeString(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return getTimeString(calendar);
    }


    public String getTimeString(Calendar calendar) {

        return getTimeString(calendar.getTime());
    }

    public String getTimeString(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }

    public String getCopyMessageDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d, HH:mm", Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    public Date parseDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public String getUserFriendlyDateTimeWithBreakString(Date calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy\nHH:mm", Locale.getDefault());

        return simpleDateFormat.format(calendar);
    }

    public Date parseDateWithT(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
