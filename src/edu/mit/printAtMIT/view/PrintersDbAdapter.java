package edu.mit.printAtMIT.view;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A wrapper for sqlitedatabase.
 * Mainly used to keep track of user's favorited printers.
 */
public class PrintersDbAdapter {
    public static final String KEY_PARSEID = "parseId";
    public static final String KEY_ROWID = "_id";
    
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mContext;
    
    /**
     * Database creation sql statement
     */
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "favorites";
    private static final String DATABASE_CREATE = 
            "CREATE TABLE " + DATABASE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "parseId TEXT NOT NULL);";


    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("PrintersDbAdapter", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }

    }
    
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public PrintersDbAdapter(Context ctx) {
        this.mContext = ctx;
    }
    
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public PrintersDbAdapter open() throws SQLException {
        mDbHelper = new DbHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        mDbHelper.close();
    }
    
    /**
     * @return
     *      Cursor over all printer ids
     */
    public List<String> getFavorites() {
        Log.i("PrintersDbAdapter", "getFavorites()");
        List<String> favorites = new ArrayList<String>();
        Cursor cursor =  mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_PARSEID}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String parseId = cursor.getString(1);
            favorites.add(parseId);
            cursor.moveToNext();
        }
        cursor.close();
        return favorites;
    }

    /**
     * Create a new row with parseId. If row is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param parseId
     *          parseId of the printer
     * @return rowId or -1 if failed
     */
    public long addToFavorites(String parseId) {
        Log.i("PrintersDbAdapter", "adding to favorites");
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PARSEID, parseId);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    
    /**
     * 
     * @param parseId
     * @return
     *      True of parseId is in database, False otherwise
     */
    public boolean isFavorite(String parseId) {
        Log.i("PrintersDbAdapter", "isFavorite()");
        Cursor cursor = 
                
                mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_PARSEID}, KEY_PARSEID + "=\"" + parseId + "\"", null,
                        null, null, null, null);
        Log.i("PrintersDbAdapter", "done querying");
        
        if (cursor.moveToFirst() == false) {
            Log.i("PrintersDbAdapter", "printer not in favorites");
            cursor.close();
            return false;
        }
        
        Log.i("PrintersDbAdapter", "printer in favorites");
        cursor.close();
        return true;
    }
    
    /**
     * 
     * @param parseId
     * @return
     *      True if deleted, False otherwise
     */
    public boolean removeFavorite(String parseId) {
        Log.i("PrintersDbAdapter", "removeFavorite()");
        return mDb.delete(DATABASE_TABLE, KEY_PARSEID + "=\"" + parseId + "\"", null) > 0;
    }
}
