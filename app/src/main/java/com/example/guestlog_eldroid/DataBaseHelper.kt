package com.example.guestlog_eldroid

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.UUID

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "guestlog.db"
        const val DATABASE_VERSION = 1

        const val TABLE_GUESTS = "guests"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_TIME_IN = "time_in"
        const val COLUMN_DATE = "date"
        const val COLUMN_PHONE_NUMBER = "phone_number"
        const val COLUMN_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
        CREATE TABLE $TABLE_GUESTS (
            $COLUMN_ID TEXT PRIMARY KEY,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_EMAIL TEXT NOT NULL,
            $COLUMN_TIME_IN TEXT,
            $COLUMN_DATE TEXT,
            $COLUMN_PHONE_NUMBER TEXT,
            $COLUMN_AGE INTEGER
        );
        CREATE INDEX idx_guests_id ON $TABLE_GUESTS ($COLUMN_ID);
    """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GUESTS")
        onCreate(db)
    }

    // Add guest to the database
    fun addGuest(name: String, email: String?, timeIn: String?, date: String?, phoneNumber: String?, age: String?): Long {
        val db = writableDatabase
        val newId = UUID.randomUUID().toString()
        return try {
            val values = ContentValues().apply {
                put(COLUMN_ID, newId)
                put(COLUMN_NAME, name)
                put(COLUMN_EMAIL, email)
                put(COLUMN_TIME_IN, timeIn)
                put(COLUMN_DATE, date)
                put(COLUMN_PHONE_NUMBER, phoneNumber)
                put(COLUMN_AGE, age?.toIntOrNull())
            }
            val rowId = db.insert(TABLE_GUESTS, null, values)
            Log.d("DatabaseHelper", "Guest added with row ID: $rowId")
            rowId
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error adding guest: ${e.message}")
            -1
        } finally {
            db.close()
        }
    }

    // Update guest in the database
    fun updateGuest(guest: Guest): Int {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put(COLUMN_NAME, guest.name)
                put(COLUMN_EMAIL, guest.email)
                put(COLUMN_TIME_IN, guest.timeIn)
                put(COLUMN_DATE, guest.date)
                put(COLUMN_PHONE_NUMBER, guest.phoneNumber)
                put(COLUMN_AGE, guest.age?.toIntOrNull())
            }
            db.update(TABLE_GUESTS, values, "$COLUMN_ID = ?", arrayOf(guest.id))
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error updating guest: ${e.message}")
            -1
        } finally {
            db.close()
        }
    }

    // Fetch all guests from the database
    fun getAllGuests(): List<Guest> {
        val guests = mutableListOf<Guest>()
        val db = readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query(TABLE_GUESTS, null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val guest = Guest(
                        id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        timeIn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_IN)),
                        date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER)),
                        age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)).toString()
                    )
                    guests.add(guest)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error fetching guests: ${e.message}")
        } finally {
            cursor?.close()
            db.close()
        }
        return guests
    }fun clearGuests() {
        val db = writableDatabase
        try {
            db.execSQL("DELETE FROM $TABLE_GUESTS")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error clearing guests: ${e.message}")
        } finally {
            db.close()
        }
    }


    // Delete guest from the database
    fun deleteGuest(id: String): Int {
        val db = writableDatabase
        return try {
            db.delete(TABLE_GUESTS, "$COLUMN_ID = ?", arrayOf(id))
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error deleting guest: ${e.message}")
            -1
        } finally {
            db.close()
        }
    }
}
