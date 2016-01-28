package wtopolski.android.samplelist.db;

import android.provider.BaseColumns;

/**
 * Created by 10c on 2015-11-12.
 */
public class DBContract {
    public static String DB_NAME = "elements";

    public static class ElementTable implements BaseColumns {
        public static String TABLE_NAME = "elements";
        public static String TITLE_COLUMN = "title";
        public static String DESC_COLUMN = "desc";
        public static String PRIORITY_COLUMN = "priority";
    }
}
