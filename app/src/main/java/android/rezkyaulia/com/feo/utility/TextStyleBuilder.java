package android.rezkyaulia.com.feo.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.rezkyaulia.com.feo.R;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Pair;
import android.view.View;

import java.util.Locale;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shiburagi on 23/02/2017.
 */
public class TextStyleBuilder {

    // Step 1: private static variable of INSTANCE variable
    private static volatile TextStyleBuilder INSTANCE;

    // Step 2: private constructor
    private TextStyleBuilder() {

    }

    // Step 3: Provide public static getInstance() method returning INSTANCE after checking
    public static TextStyleBuilder getInstance() {

        // double-checking lock
        if(null == INSTANCE){

            // synchronized block
            synchronized (TextStyleBuilder.class) {
                if(null == INSTANCE){
                    INSTANCE = new TextStyleBuilder();
                }
            }
        }
        return INSTANCE;
    }



    public Spannable parse(final Context context, String name) {

        if (name == null)
            return null;
        name = name.replaceAll("\\>[\\s]+", ">").replaceAll("[\\s]+\\<", "<").replaceAll("<br>", "\n")
                .replaceAll("(&nbsp;)", " ");

        SpannableStringBuilder spannable = new SpannableStringBuilder(name);
        final String finalName = name;

        final TreeMap<Integer, Pair<Integer, Integer>> pairs = new TreeMap<>();
        parse(spannable, name, "href", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {

                String text = finalName.substring(start, end);

                final String popup = parse(spannable, text, start, pairs);
                trim(pairs, start, end, openTag, closeTag);
                spannable.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(context)
                                .setMessage(popup)
                                .setNegativeButton(R.string.close, null)
                                .create()
                                .show();
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });
        parse(spannable, name, "center", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);
                spannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });

        parse(spannable, name, "right", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

                spannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });

        parse(spannable, name, "i", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

                spannable.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });

        parse(spannable, name, "b", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

                spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });

        parse(spannable, name, "\\#[\\w]+", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

                String text = finalName.substring(start);
                int endTag = text.indexOf('>');
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor(
                        text.substring(1, endTag)
                )), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        });
        parse(spannable, name, "b\\#[\\w]+", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

                String text = finalName.substring(start);
                int endTag = text.indexOf('>');
                spannable.setSpan(new BackgroundColorSpan(Color.parseColor(
                        text.substring(2, endTag)
                )), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });

        parse(spannable, name, "large", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

                spannable.setSpan(new RelativeSizeSpan(1.3f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });

        parse(spannable, name, "small", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

                spannable.setSpan(new RelativeSizeSpan(.7f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });


        parse(spannable, name, "[\\w\\W]+", new OnFoundListener() {
            @Override
            public void onFound(Spannable spannable, int start, int end, int openTag, int closeTag) {
                trim(pairs, start, end, openTag, closeTag);

            }
        });

        while (!pairs.isEmpty()) {
            Pair<Integer, Integer> pair = pairs.pollLastEntry().getValue();
            spannable.delete(pair.first, pair.second);
        }
        return spannable;
    }

    private void trim(TreeMap<Integer, Pair<Integer, Integer>> pairs, int start, int end, int openTag, int closeTag) {

        pairs.put(start, new Pair<Integer, Integer>(start, start + openTag));
        pairs.put(end - closeTag, new Pair<Integer, Integer>(end - closeTag, end));
    }

    private String parse(Spannable spannable, String name, int i, TreeMap<Integer, Pair<Integer, Integer>> pairs) {

        Pattern p = Pattern.compile("\\<p\\>[\\w\\W]*\\<\\/p\\>");
        Matcher m = p.matcher(name);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
//            spannable.setSpan(new RelativeSizeSpan(.0f), start + i, end + i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            pairs.put(start + i, new Pair<Integer, Integer>(start + i, end + i));
            if (end - start > 7)
                return name.substring(start + 3, end - 4).replaceAll("\\<br\\>", "\n");

        }
        return "";
    }

    private Spannable parse(Spannable spannable, String text, String tag, OnFoundListener listener) {
        Pattern p = Pattern.compile(
                String.format(
                        Locale.getDefault(),
                        "(<%s>|<\\/%s>)", tag, tag)
        );
        Matcher m = p.matcher(text);
        find(spannable, m, 0, "<" + tag + ">", "</" + tag + ">", listener);
        return spannable;
    }

    private Matcher find(Spannable spannable, Matcher m, int index, String openTag, String closeTag, OnFoundListener listener) {
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String text = m.group();

            if (m.group().matches(openTag)) {
                find(spannable, m, start, openTag, closeTag, listener);
            } else {
//                spannable.setSpan(new RelativeSizeSpan(.0f), index, index + (m.end() - m.start() - 1),
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannable.setSpan(new RelativeSizeSpan(.0f), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                listener.onFound(spannable, index, m.end(), m.end() - m.start() - 1, m.end() - m.start());

                return m;
            }


        }

        return m;
    }
}
