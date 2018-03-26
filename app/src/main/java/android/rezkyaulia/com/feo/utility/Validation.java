package android.rezkyaulia.com.feo.utility;

import android.content.Context;
import android.rezkyaulia.com.feo.R;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * Created by Shiburagi on 21/06/2016.
 */
public class Validation {
    public boolean valid;
    public float y;
    public int messageId = R.string.please_fill_in_here;
    public String message;

    public Validation(boolean b) {
        valid = b;
    }
    public Validation() {
    }

    public static final String EMAIL = "E-mail";
    public static final String URL = "URL";
    public static final String CURRENCY = "Currency";
    public static final String INTEGER = "Integer";

    public Validation validate(Context context, String text, String validatorType, long minAnswer, long maxAnswer) {
        Validation validation = new Validation();
        if (text == null) {
            validation.valid = false;
        } else {
            text = text.toLowerCase();
            switch (validatorType) {
                case "E-mail":
                    validation.valid = email(text);
                    if (!validation.valid)
                        validation.messageId = R.string.invalidemail;
                    break;
                case "URL":
                    validation.valid = url(text);
                    if (!validation.valid)
                        validation.messageId = R.string.invalidurl;
                    break;
                case "Decimal":
                case "Currency":
                case "Integer":
                    validation.valid = false;
                    //

                    if (text.length() != 0)
                        try {

                        validation.valid = true;

                        } catch (Exception e) {
                            validation.messageId = R.string.invalidvalue;

                        }
                    break;
                default:
                    validation.valid = text.length() != 0;
            }
        }
        return validation;
    }


    public static void setEditTextInputType(EditText editText, String validatorType, long minAnswer, long maxAnswer) {
        Timber.d("Validator type : " + validatorType);

        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        switch (validatorType) {
            case "Decimal":
            case "Currency":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case "URL":
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                break;
            case "E-mail":
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case "Integer":
            case "MyKad":
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            default:
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }

    }

    public static void setEditTextInputType(EditText editText, String validatorType) {
        if (validatorType == null)
            return;

        if (validatorType != null)
            switch (validatorType) {
                case "Decimal":
                case "Currency":
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    break;
                case "URL":
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                    break;
                case "E-mail":
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
                case "MyKad":
                case "Integer":
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                default:
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            }

//        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

    }

    private static boolean url(String text) {
//        return name.matches("");
//        return name.matches("@(https?|ftp)://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[^\\s]*)?$@iS\n");
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(text);//replace with string to compare
        return matcher.find();
    }

    public static boolean email(String text) {
        return text.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }

    public static boolean myKad(String text) {
        return text.matches("^[0-9]{6}[\\-]?[0-9]{2}[\\-]?[0-9]{4}$");
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean textMatch(String text, String regex){
        return text.matches(regex);
    }
}

