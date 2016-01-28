package wtopolski.android.samplelist.db;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

/**
 * Created by 10c on 2015-11-12.
 */
public class DBContract {
    @NonNull
    public static String DB_NAME = "elements";

    public static class ElementTable implements BaseColumns {
        @NonNull
        public static String TABLE_NAME = "elements";
        @NonNull
        public static String TITLE_COLUMN = "title";
        @NonNull
        public static String DESC_COLUMN = "desc";
        @NonNull
        public static String PRIORITY_COLUMN = "priority";
    }
}
