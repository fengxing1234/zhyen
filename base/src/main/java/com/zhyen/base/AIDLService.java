package com.zhyen.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    private final String TAG = "AIDLService";
    private List<Student> students;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        students = new ArrayList<>();
        initData();
    }

    private void initData() {
        Student student1 = new Student("张三", 21);
        Student student2 = new Student("李四", 21);
        Student student3 = new Student("王老五", 21);
        Student student4 = new Student("孙悟空", 21);
        Student student5 = new Student("唐三藏", 21);
        Student student6 = new Student("牛魔王", 21);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
    }

    private IMyService.Stub stub = new IMyService.Stub() {
        @Override
        public List<Student> getStudent() throws RemoteException {
            Log.d(TAG, "getStudent: ");
            return students;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            Log.d(TAG, "addStudent: " + student);
            if (student != null) {
                students.add(student);
                Log.d(TAG, "addStudent: " + student.toString());
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return stub;
    }

}
