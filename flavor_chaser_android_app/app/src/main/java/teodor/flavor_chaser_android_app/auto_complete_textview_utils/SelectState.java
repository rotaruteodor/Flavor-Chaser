//package teodor.flavor_chaser_android_app.AutoCompleteTextViewUtils;
//
//import android.app.Activity;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.SimpleCursorAdapter;
//import android.widget.TextView;
//
//import teodor.flavor_chaser_android_app.R;
//
//public class SelectState extends Activity {
//
//    final static int[] to = new int[] { android.R.id.text1 };
//    final static String[] from = new String[] { "state" };
//
//    class ItemAutoTextAdapter extends SimpleCursorAdapter
//            implements AdapterView.OnItemClickListener {
//
//        private AutoCompleteDbAdapter mDbHelper;
//
//        public ItemAutoTextAdapter(AutoCompleteDbAdapter dbHelper) {
//            // Call the SimpleCursorAdapter constructor with a null Cursor.
//            super(SelectState.this, android.R.layout.simple_dropdown_item_1line, null, from, to);
//            mDbHelper = dbHelper;
//        }
//
//        @Override
//        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
//            if (getFilterQueryProvider() != null) {
//                return getFilterQueryProvider().runQuery(constraint);
//            }
//
//            Cursor cursor = mDbHelper.getMatchingStates(
//                    (constraint != null ? constraint.toString() : null));
//
//            return cursor;
//        }
//
//        @Override
//        public String convertToString(Cursor cursor) {
//            final int columnIndex = cursor.getColumnIndexOrThrow("state");
//            final String str = cursor.getString(columnIndex);
//            return str;
//        }
//
//        @Override
//        public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
//            // Get the cursor, positioned to the corresponding row in the result set
//            Cursor cursor = (Cursor) listView.getItemAtPosition(position);
//
//            // Get the state's capital from this row in the database.
//            String capital = cursor.getString(cursor.getColumnIndexOrThrow("capital"));
//
//            // Update the parent class's TextView
//            mStateCapitalView.setText(capital);
//        }
//    }
//
//    private TextView mStateCapitalView;
//    private AutoCompleteTextView mStateNameView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AutoCompleteDbAdapter dbHelper = new AutoCompleteDbAdapter(this);
//        setContentView(R.layout.selectstate);
//        Button confirmButton = (Button) findViewById(R.id.confirm);
//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                setResult(RESULT_OK);
//                finish();
//            }
//        });
//
//        mStateCapitalView = (TextView) findViewById(R.id.state_capital);
//        mStateNameView = (AutoCompleteTextView) findViewById(R.id.state_name);
//
//        // Create an ItemAutoTextAdapter for the State Name field,
//        // and set it as the OnItemClickListener for that field.
//        ItemAutoTextAdapter adapter = this.new ItemAutoTextAdapter(dbHelper);
//        mStateNameView.setAdapter(adapter);
//        mStateNameView.setOnItemClickListener(adapter);
//    }
//}
