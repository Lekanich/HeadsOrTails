package lekanich;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

public class HeadsOrTailsBundle extends AbstractBundle {
    /**
     * The {@link java.util.ResourceBundle} path.
     */
    @NonNls
    private static final String BUNDLE_NAME = "messages.HeadsOrTailsBundle";
    private static final String[] KEYS_FUNNY_SUBTITLES = {
            "heads.or.tails.intro.m0.empty",
            "heads.or.tails.intro.m1",
            "heads.or.tails.intro.m2",
            "heads.or.tails.intro.m3",
            "heads.or.tails.intro.m4",
            "heads.or.tails.intro.m5",
            "heads.or.tails.intro.m6",
            "heads.or.tails.intro.m7",
            "heads.or.tails.intro.m8",
            "heads.or.tails.intro.m9",
            "heads.or.tails.intro.m10",
            "heads.or.tails.intro.m11"
    };

    /**
     * The {@link java.util.ResourceBundle} instance.
     */
    private static final HeadsOrTailsBundle BUNDLE = new HeadsOrTailsBundle();

    private HeadsOrTailsBundle() {
        super(BUNDLE_NAME);
    }

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) final String key, final Object... params) {
        return BUNDLE.getMessage(key, params);
    }

    public static int getFunnyIntrosSize() {
        return KEYS_FUNNY_SUBTITLES.length;
    }

    public static String getFunnyIntrosByIndex(final int index) {
        return BUNDLE.getMessage(KEYS_FUNNY_SUBTITLES[index]);
    }
}
