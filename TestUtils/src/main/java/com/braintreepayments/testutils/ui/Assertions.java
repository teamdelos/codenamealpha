package com.braintreepayments.testutils.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.EditText;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB_MR1;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class Assertions {

    /**
     * Asserts that the right drawable of an {@link android.widget.EditText} matches a given
     * drawable resource.
     *
     * @param context
     * @param resourceId The id of the resource to check.
     * @return {@link android.support.test.espresso.ViewAssertion} for chaining
     */
    public static ViewAssertion theIconHintIs(Context context, int resourceId) {
        Drawable right = context.getResources().getDrawable(resourceId);
        return assertHintsAre(null, null, right, null);
    }

    /**
     * Asserts that the drawables of an {@link android.widget.EditText} match the given
     * drawables.
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return {@link android.support.test.espresso.ViewAssertion} for chaining
     */
    public static ViewAssertion assertHintsAre(final Drawable left, final Drawable top,
            final Drawable right, final Drawable bottom) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                assert (view) != null;
                Drawable[] drawables = ((EditText) view).getCompoundDrawables();
                assertBitmapsEqual(drawables[0], left);
                assertBitmapsEqual(drawables[1], top);
                assertBitmapsEqual(drawables[2], right);
                assertBitmapsEqual(drawables[3], bottom);
            }
        };
    }

    @TargetApi(HONEYCOMB_MR1)
    public static void assertBitmapsEqual(Drawable d1, Drawable d2) {
        if (d1 == null || d2 == null) {
            assertEquals(d1, d2);
        } else {
            Bitmap b1 = ((BitmapDrawable) d1).getBitmap();
            Bitmap b2 = ((BitmapDrawable) d2).getBitmap();
            if (SDK_INT >= HONEYCOMB_MR1) {
                assertTrue(b1.sameAs(b2));
            } else {
                assertEquals(b1.getHeight(), b2.getHeight());
                assertEquals(b1.getWidth(), b2.getWidth());
                for (int x = 0; x < b1.getWidth(); x++) {
                    for (int y = 0; y < b1.getHeight(); y++) {
                        assertEquals(b1.getPixel(x, y), b2.getPixel(x, y));
                    }
                }
            }
        }
    }

}
