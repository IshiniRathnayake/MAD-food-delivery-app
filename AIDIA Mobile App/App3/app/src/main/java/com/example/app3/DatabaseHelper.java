package com.example.app3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and table names
    private static final String DATABASE_NAME = "app_db";
    private static final int DATABASE_VERSION = 2;

    // Users table
    private static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_BIRTHDAY = "birthday";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DISTRICT = "district";
    public static final String COLUMN_ZIPCODE = "zipcode";
    public static final String COLUMN_PHONE = "phone";

    // Menu items table
    private static final String TABLE_MENU_ITEMS = "MenuItems";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_AVAILABLE_ITEMS = "available_items";
    public static final String COLUMN_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_BIRTHDAY + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_DISTRICT + " TEXT, " +
                COLUMN_ZIPCODE + " TEXT, " +
                COLUMN_PHONE + " TEXT)";
        db.execSQL(createUsersTable);

        // Create menu items table
        String createMenuItemsTable = "CREATE TABLE " + TABLE_MENU_ITEMS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_CATEGORY + " TEXT NOT NULL, " +
                COLUMN_AVAILABLE_ITEMS + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " REAL NOT NULL)";
        db.execSQL(createMenuItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Upgrade users table by adding new columns
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_BIRTHDAY + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_DISTRICT + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ZIPCODE + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_PHONE + " TEXT");
        }
    }

    // User methods
    public boolean insertUser(String username, String email, String password, String birthday, String address, String district, String zipcode, String phone) {
        if (checkUser(email)) {
            return false; // User with this email already exists
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_BIRTHDAY, birthday);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_DISTRICT, district);
        values.put(COLUMN_ZIPCODE, zipcode);
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean updateUser(String email, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_EMAIL + " = ?", new String[]{email});
        return rowsAffected > 0;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD,
                COLUMN_BIRTHDAY,
                COLUMN_ADDRESS,
                COLUMN_DISTRICT,
                COLUMN_ZIPCODE,
                COLUMN_PHONE
        };
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        return db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_EMAIL,
                COLUMN_PHONE
        };

        return db.query(TABLE_USERS, columns, null, null, null, null, null);
    }

    // Menu item methods
    public void addMenuItem(String name, String category, int availableItems, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_AVAILABLE_ITEMS, availableItems);
        values.put(COLUMN_PRICE, price);
        db.insert(TABLE_MENU_ITEMS, null, values);
        db.close();
    }

    public void updateMenuItem(int id, String name, String category, int availableItems, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_AVAILABLE_ITEMS, availableItems);
        values.put(COLUMN_PRICE, price);
        db.update(TABLE_MENU_ITEMS, values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getMenuItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MENU_ITEMS, null);
    }
}
