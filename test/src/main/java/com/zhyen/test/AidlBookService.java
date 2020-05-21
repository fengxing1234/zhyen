package com.zhyen.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AidlBookService extends Service {

    private static final String TAG = "AidlService";
    private List<Book> books;

    @Override
    public void onCreate() {
        super.onCreate();
        books = new ArrayList<>();
        Book book1 = new Book("活着");
        Book book2 = new Book("或者");
        Book book3 = new Book("叶应是叶");
        Book book4 = new Book("https://github.com/leavesC");
        Book book5 = new Book("http://www.jianshu.com/u/9df45b87cfdf");
        Book book6 = new Book("http://blog.csdn.net/new_one_object");
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
    }

    private TestAidl.Stub stub = new TestAidl.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "getBookList: ");
            return books;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            Log.d(TAG, "addBookInOut: " + book);
            books.add(book);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
