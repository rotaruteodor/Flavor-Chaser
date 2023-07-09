//package teodor.flavor_chaser_android_app.AutoCompleteTextViewUtils;
//
//import android.app.Activity;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
///**
// * Simple database access helper class.
// *
// * @author Dan Breslau
// */
//public class AutoCompleteDbAdapter {
//    /**
//     * List of states and capitals.
//     */
//    private static final String[][] States = {
//            { "Alabama", "Montgomery" },
//            { "Alaska", "Juneau" },
//            { "Arizona", "Phoenix" },
//            { "Arkansas", "Little Rock" },
//            { "California", "Sacramento" },
//            { "Colorado", "Denver" },
//            { "Confusion", "\"C\"" },
//            { "Connecticut", "Hartford" },
//            { "Delaware", "Dover" },
//            { "Florida", "Tallahassee" },
//            { "Georgia", "Atlanta" },
//            { "Hawaii", "Honolulu" },
//            { "Idaho", "Boise" },
//            { "Illinois", "Springfield" },
//            { "Indiana", "Indianapolis" },
//            { "Iowa", "Des Moines" },
//            { "Kansas", "Topeka" },
//            { "Kentucky", "Frankfort" },
//            { "Louisiana", "Baton Rouge" },
//            { "Maine", "Augusta" },
//            { "Maryland", "Annapolis" },
//            { "Massachusetts", "Boston" },
//            { "Michigan", "Lansing" },
//            { "Minnesota", "St. Paul" },
//            { "Mississippi", "Jackson" },
//            { "Missouri", "Jefferson City" },
//            { "Montana", "Helena" },
//            { "Nebraska", "Lincoln" },
//            { "Nevada", "Carson City" },
//            { "New Hampshire", "Concord" },
//            { "New Jersey", "Trenton" },
//            { "New Mexico", "Santa Fe" },
//            { "New York", "Albany" },
//            { "North Carolina", "Raleigh" },
//            { "North Dakota", "Bismarck" },
//            { "Ohio", "Columbus" },
//            { "Oklahoma", "Oklahoma City" },
//            { "Oregon", "Salem" },
//            { "Pennsylvania", "Harrisburg" },
//            { "Rhode Island", "Providence" },
//            { "South Carolina", "Columbia" },
//            { "South Dakota", "Pierre" },
//            { "Tennessee", "Nashville" },
//            { "Texas", "Austin" },
//            { "Utah", "Salt Lake City" },
//            { "Vermont", "Montpelier" },
//            { "Virginia", "Richmond" },
//            { "Washington", "Olympia" },
//            { "West Virginia", "Charleston" },
//            { "Wisconsin", "Madison" },
//            { "Wyoming", "Cheyenne" }
//    };
//
//    private static final String DATABASE_NAME = "capitals";
//    private static final String TABLE_NAME = "state";
//    private static final int DATABASE_VERSION = 1;
//
//    private class DatabaseHelper extends SQLiteOpenHelper {
//
//        DatabaseHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            final String DATABASE_CREATE_STATES =
//                    "create table " + TABLE_NAME
//                            + "(_id integer primary key autoincrement"
//                            + ", state text not null"
//                            + ", capital text not null)";
//
//            db.execSQL(DATABASE_CREATE_STATES);
//            populateWithData(db);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//            onCreate(db);
//        }
//    }
//
//    private DatabaseHelper mDbHelper;
//    private SQLiteDatabase mDb;
//    private final Activity mActivity;
//
//    /**
//     * Constructor - takes the context to allow the database to be
//     * opened/created
//     *
//     * @param activity
//     *            the Activity that is using the database
//     */
//    public AutoCompleteDbAdapter(Activity activity) {
//        this.mActivity = activity;
//        mDbHelper = this.new DatabaseHelper(activity);
//        mDb = mDbHelper.getWritableDatabase();
//    }
//
//    /**
//     * Closes the database.
//     */
//    public void close() {
//        mDbHelper.close();
//    }
//
//    /**
//     * Return a Cursor that returns all states (and their state capitals) where
//     * the state name begins with the given constraint string.
//     *
//     * @param constraint
//     *            Specifies the first letters of the states to be listed. If
//     *            null, all rows are returned.
//     * @return Cursor managed and positioned to the first state, if found
//     * @throws SQLException
//     *             if query fails
//     */
//    public Cursor getMatchingStates(String constraint) throws SQLException {
//
//        String queryString =
//                "SELECT _id, state, capital FROM " + TABLE_NAME;
//
//        if (constraint != null) {
//            // Query for any rows where the state name begins with the
//            // string specified in constraint.
//            //
//            // NOTE:
//            // If wildcards are to be used in a rawQuery, they must appear
//            // in the query parameters, and not in the query string proper.
//            // See http://code.google.com/p/android/issues/detail?id=3153
//            constraint = constraint.trim() + "%";
//            queryString += " WHERE state LIKE ?";
//        }
//        String params[] = { constraint };
//
//        if (constraint == null) {
//            // If no parameters are used in the query,
//            // the params arg must be null.
//            params = null;
//        }
//        try {
//            Cursor cursor = mDb.rawQuery(queryString, params);
//            if (cursor != null) {
//                this.mActivity.startManagingCursor(cursor);
//                cursor.moveToFirst();
//                return cursor;
//            }
//        }
//        catch (SQLException e) {
//            Log.e("AutoCompleteDbAdapter", e.toString());
//            throw e;
//        }
//
//        return null;
//    }
//
//    /**
//     * Populates the database with data on states and state capitals.
//     *
//     * @param db
//     *            The database to be populated; must have the appropriate table
//     *            ("state") and columns ("state" and "values") already set up.
//     */
//    private void populateWithData(SQLiteDatabase db) {
//        try {
//            db.beginTransaction();
//            ContentValues values = new ContentValues();
//            // Populate the database with the state/capital city
//            // pairs found in the States array.
//            for (String[] s : States) {
//                values.put("state", s[0]);
//                values.put("capital", s[1]);
//
//                db.insert(TABLE_NAME, "state", values);
//            }
//            db.setTransactionSuccessful();
//        }
//        finally {
//            db.endTransaction();
//        }
//    }
//
//}
