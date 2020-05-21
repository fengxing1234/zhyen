package com.zhyen.android.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhyen.android.R;
import com.zhyen.base.IMyService;
import com.zhyen.base.Student;
import com.zhyen.test.Book;
import com.zhyen.test.TestAidl;

import java.util.List;

public class TestAIDLActivity extends AppCompatActivity {


    private static final String TAG = "TestAIDLActivity";
    private boolean connected;
    private boolean bookConnected;
    private Button btnStudentList;
    private Button btnAddStudent;
    private IMyService myService;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);
            myService = IMyService.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + name);
            connected = false;
        }
    };

    private ServiceConnection connectionBook = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);
            bookConnected = true;
            testAidl = TestAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bookConnected = false;
            Log.d(TAG, "onServiceDisconnected: " + name);
        }
    };
    private TestAidl testAidl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_aidl_activity);
        btnStudentList = findViewById(R.id.btn_student_list);
        btnAddStudent = findViewById(R.id.btn_add_student);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!connected) return;
                Student student = new Student("新人一个", 32);
                try {
                    myService.addStudent(student);
                    Log.d(TAG, "onClick: 向服务端添加一个学生 :" + student.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        btnStudentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!connected) return;
                try {
                    List<Student> students = myService.getStudent();
                    for (Student student : students) {
                        Log.d(TAG, "学生: " + student.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!bookConnected) return;
                    testAidl.addBookInOut(new Book("呵呵哒"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_book_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bookConnected) return;
                try {
                    List<Book> bookList = testAidl.getBookList();
                    for (Book book : bookList) {
                        Log.d(TAG, "onClick: " + book.getName());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        bindService();
        bindServiceBook();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.zhyen.android");
        intent.setAction("com.zhyen.base.action");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void bindServiceBook() {
        Intent intent = new Intent();
        intent.setPackage("com.zhyen.test");
        intent.setAction("com.zhyen.test.action");
        bindService(intent, connectionBook, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(connection);
        }
        unbindService(connectionBook);
    }
}
