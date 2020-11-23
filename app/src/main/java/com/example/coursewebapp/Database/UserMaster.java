package com.example.coursewebapp.Database;

import android.provider.BaseColumns;

public final class UserMaster {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserMaster() {
    }

    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "UsersTable";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_PASSWORD = "Password";
        public static final String COLUMN_NAME_TYPE = "Type";
    }

    public static class Message implements BaseColumns {
        public static final String TABLE_NAME = "MessageTable";
        public static final String COLUMN_NAME_TEACHER = "User";
        public static final String COLUMN_NAME_SUBJECT = "Subject";
        public static final String COLUMN_NAME_TYPE = "Message";
    }
}

