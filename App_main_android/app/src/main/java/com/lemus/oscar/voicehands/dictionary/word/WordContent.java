package com.lemus.oscar.voicehands.dictionary.word;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lemus.oscar.voicehands.VHDataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class WordContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<WordItem> ITEMS = new ArrayList<WordItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, WordItem> ITEM_MAP = new HashMap<String, WordItem>();

    private int category;

    /*private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }*/

    public WordContent (int category) {
        this.category = category;

        initItems();
    }

    private void initItems() {

        SQLiteDatabase bd = SQLiteDatabase.openDatabase(VHDataBaseHelper.dbPath, null, 1);
        Cursor fila = bd.rawQuery("SELECT * FROM words WHERE id_category = " + category, null);
        SQLiteDatabase bd2 = SQLiteDatabase.openDatabase(VHDataBaseHelper.dbPath, null, 1);
        Cursor fila2; // = bd2.rawQuery("SELECT * FROM images", null);
        fila.moveToFirst();
        //fila2.moveToFirst();

        do {
            fila2 = bd2.rawQuery("SELECT image FROM images WHERE code = '" + fila.getString(5) + "'", null);
            fila2.moveToFirst();
            addItem(new WordItem(String.valueOf(fila.getInt(0)),
                    fila.getString(2),
                    fila.getString(3),
                    fila2.getBlob(0)));
        }while (fila.moveToNext());

        bd.close();
        bd2.close();
    }

    private static void addItem(WordItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /*private static WordItem createDummyItem(int position) {
        return new WordItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }*/

    /**
     * A dummy item representing a piece of content.
     */
    public static class WordItem {
        public final String id;
        public final String content;
        public final String details;
        public final byte[] image;

        public WordItem(String id, String content, String details, byte[] image) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.image = image;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
