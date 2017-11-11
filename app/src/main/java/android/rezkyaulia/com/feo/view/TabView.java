package android.rezkyaulia.com.feo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.design.widget.TabLayout.MODE_FIXED;

/**
 * Created by Shiburagi on 28/03/2017.
 */

public class TabView extends LinearLayout implements View.OnLongClickListener {
    private static final int DEFAULT_GAP_TEXT_ICON = 8;
    private TabLayout.Tab mTab;
    private TextView mTextView;
    private ImageView mIconView;

    private View mCustomView;
    private TextView mCustomTextView;
    private ImageView mCustomIconView;

    private int mDefaultMaxLines = 2;
    private int maxWidth;
    private int mTabMaxWidth;
    private float mTabTextSize;
    private float mTabTextMultiLineSize;
    private int mMode;
    private ColorStateList mTabTextColors;
    private int mTabTextAppearance;

    public TabView(Context context, TabLayout tabLayout) {
        super(context);
//        if (tabLayout.mTabBackgroundResId != 0) {
//            ViewCompat.setBackground(
//                    this, AppCompatResources.getDrawable(context, mTabBackgroundResId));
//        }
//        ViewCompat.setPaddingRelative(this, mTabPaddingStart, mTabPaddingTop,
//                mTabPaddingEnd, mTabPaddingBottom);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        setClickable(true);
        ViewCompat.setPointerIcon(this,
                PointerIconCompat.getSystemIcon(getContext(), PointerIconCompat.TYPE_HAND));
    }



    @Override
    public boolean performClick() {
        final boolean handled = super.performClick();

        if (mTab != null) {
            if (!handled) {
                playSoundEffect(SoundEffectConstants.CLICK);
            }
            mTab.select();
            return true;
        } else {
            return handled;
        }
    }

    @Override
    public void setSelected(final boolean selected) {
        final boolean changed = isSelected() != selected;

        super.setSelected(selected);

        if (changed && selected && Build.VERSION.SDK_INT < 16) {
            // Pre-JB we need to manually send the TYPE_VIEW_SELECTED event
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        }

        // Always dispatch this to the child views, regardless of whether the value has
        // changed
        if (mTextView != null) {
            mTextView.setSelected(selected);
        }
        if (mIconView != null) {
            mIconView.setSelected(selected);
        }
        if (mCustomView != null) {
            mCustomView.setSelected(selected);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        // This view masquerades as an action bar tab.
        event.setClassName(ActionBar.Tab.class.getName());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        // This view masquerades as an action bar tab.
        info.setClassName(ActionBar.Tab.class.getName());
    }

    @Override
    public void onMeasure(final int origWidthMeasureSpec, final int origHeightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(origWidthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(origWidthMeasureSpec);
        final int maxWidth = this.maxWidth;

        final int widthMeasureSpec;
        final int heightMeasureSpec = origHeightMeasureSpec;

        if (maxWidth > 0 && (specWidthMode == MeasureSpec.UNSPECIFIED
                || specWidthSize > maxWidth)) {
            // If we have a max width and a given spec which is either unspecified or
            // larger than the max width, update the width spec using the same mode
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mTabMaxWidth, MeasureSpec.AT_MOST);
        } else {
            // Else, use the original width spec
            widthMeasureSpec = origWidthMeasureSpec;
        }

        // Now lets measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // We need to switch the text size based on whether the text is spanning 2 lines or not
        if (mTextView != null) {
            final Resources res = getResources();
            float textSize = mTabTextSize;
            int maxLines = mDefaultMaxLines;

            if (mIconView != null && mIconView.getVisibility() == VISIBLE) {
                // If the icon view is being displayed, we limit the text to 1 line
                maxLines = 1;
            } else if (mTextView != null && mTextView.getLineCount() > 1) {
                // Otherwise when we have text which wraps we reduce the text size
                textSize = mTabTextMultiLineSize;
            }

            final float curTextSize = mTextView.getTextSize();
            final int curLineCount = mTextView.getLineCount();
            final int curMaxLines = TextViewCompat.getMaxLines(mTextView);

            if (textSize != curTextSize || (curMaxLines >= 0 && maxLines != curMaxLines)) {
                // We've got a new text size and/or max lines...
                boolean updateTextView = true;

                if (mMode == MODE_FIXED && textSize > curTextSize && curLineCount == 1) {
                    // If we're in fixed mode, going up in text size and currently have 1 line
                    // then it's very easy to get into an infinite recursion.
                    // To combat that we check to see if the change in text size
                    // will cause a line countNotSeen change. If so, abort the size change and stick
                    // to the smaller size.
                    final Layout layout = mTextView.getLayout();
                    if (layout == null || approximateLineWidth(layout, 0, textSize)
                            > getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) {
                        updateTextView = false;
                    }
                }

                if (updateTextView) {
                    mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    mTextView.setMaxLines(maxLines);
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }
    }

    public void setTab(@Nullable final TabLayout.Tab tab) {
        if (tab != mTab) {
            mTab = tab;
            update();
        }
    }

    void reset() {
        setTab(null);
        setSelected(false);
    }

    final void update() {
        final TabLayout.Tab tab = mTab;
        final View custom = tab != null ? tab.getCustomView() : null;
        if (custom != null) {
            final ViewParent customParent = custom.getParent();
            if (customParent != this) {
                if (customParent != null) {
                    ((ViewGroup) customParent).removeView(custom);
                }
                addView(custom);
            }
            mCustomView = custom;
            if (mTextView != null) {
                mTextView.setVisibility(GONE);
            }
            if (mIconView != null) {
                mIconView.setVisibility(GONE);
                mIconView.setImageDrawable(null);
            }

            mCustomTextView = (TextView) custom.findViewById(android.R.id.text1);
            if (mCustomTextView != null) {
                mDefaultMaxLines = TextViewCompat.getMaxLines(mCustomTextView);
            }
            mCustomIconView = (ImageView) custom.findViewById(android.R.id.icon);
        } else {
            // We do not have a custom view. Remove one if it already exists
            if (mCustomView != null) {
                removeView(mCustomView);
                mCustomView = null;
            }
            mCustomTextView = null;
            mCustomIconView = null;
        }

        if (mCustomView == null) {
            // If there isn't a custom view, we'll us our own in-built layouts
            if (mIconView == null) {
                ImageView iconView = (ImageView) LayoutInflater.from(getContext())
                        .inflate(android.support.design.R.layout.design_layout_tab_icon, this, false);
                addView(iconView, 0);
                mIconView = iconView;
            }
            if (mTextView == null) {
                TextView textView = (TextView) LayoutInflater.from(getContext())
                        .inflate(android.support.design.R.layout.design_layout_tab_text, this, false);
                addView(textView);
                mTextView = textView;
                mDefaultMaxLines = TextViewCompat.getMaxLines(mTextView);
            }
            TextViewCompat.setTextAppearance(mTextView, mTabTextAppearance);
            if (mTabTextColors != null) {
                mTextView.setTextColor(mTabTextColors);
            }
            updateTextAndIcon(mTextView, mIconView);
        } else {
            // Else, we'll see if there is a TextView or ImageView present and update them
            if (mCustomTextView != null || mCustomIconView != null) {
                updateTextAndIcon(mCustomTextView, mCustomIconView);
            }
        }

        // Finally update our selected state
        setSelected(tab != null && tab.isSelected());
    }

    private void updateTextAndIcon(@Nullable final TextView textView,
                                   @Nullable final ImageView iconView) {
        final Drawable icon = mTab != null ? mTab.getIcon() : null;
        final CharSequence text = mTab != null ? mTab.getText() : null;
        final CharSequence contentDesc = mTab != null ? mTab.getContentDescription() : null;

        if (iconView != null) {
            if (icon != null) {
                iconView.setImageDrawable(icon);
                iconView.setVisibility(VISIBLE);
                setVisibility(VISIBLE);
            } else {
                iconView.setVisibility(GONE);
                iconView.setImageDrawable(null);
            }
            iconView.setContentDescription(contentDesc);
        }

        final boolean hasText = !TextUtils.isEmpty(text);
        if (textView != null) {
            if (hasText) {
                textView.setText(text);
                textView.setVisibility(VISIBLE);
                setVisibility(VISIBLE);
            } else {
                textView.setVisibility(GONE);
                textView.setText(null);
            }
            textView.setContentDescription(contentDesc);
        }

        if (iconView != null) {
            MarginLayoutParams lp = ((MarginLayoutParams) iconView.getLayoutParams());
            int bottomMargin = 0;
            if (hasText && iconView.getVisibility() == VISIBLE) {
                // If we're showing both text and icon, add some margin bottom to the icon
                bottomMargin = dpToPx(DEFAULT_GAP_TEXT_ICON);
            }
            if (bottomMargin != lp.bottomMargin) {
                lp.bottomMargin = bottomMargin;
                iconView.requestLayout();
            }
        }

        if (!hasText && !TextUtils.isEmpty(contentDesc)) {
            setOnLongClickListener(this);
        } else {
            setOnLongClickListener(null);
            setLongClickable(false);
        }
    }

    @Override
    public boolean onLongClick(final View v) {
        final int[] screenPos = new int[2];
        final Rect displayFrame = new Rect();
        getLocationOnScreen(screenPos);
        getWindowVisibleDisplayFrame(displayFrame);

        final Context context = getContext();
        final int width = getWidth();
        final int height = getHeight();
        final int midy = screenPos[1] + height / 2;
        int referenceX = screenPos[0] + width / 2;
        if (ViewCompat.getLayoutDirection(v) == ViewCompat.LAYOUT_DIRECTION_LTR) {
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            referenceX = screenWidth - referenceX; // mirror
        }

        Toast cheatSheet = Toast.makeText(context, mTab.getContentDescription(),
                Toast.LENGTH_SHORT);
        if (midy < displayFrame.height()) {
            // Show below the tab view
            cheatSheet.setGravity(Gravity.TOP | GravityCompat.END, referenceX,
                    screenPos[1] + height - displayFrame.top);
        } else {
            // Show along the bottom center
            cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
        }
        cheatSheet.show();
        return true;
    }

    public TabLayout.Tab getTab() {
        return mTab;
    }

    /**
     * Approximates a given lines width with the new provided text size.
     */
    private float approximateLineWidth(Layout layout, int line, float textSize) {
        return layout.getLineWidth(line) * (textSize / layout.getPaint().getTextSize());
    }

    int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }
}
