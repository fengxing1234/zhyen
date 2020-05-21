// IMyService.aidl
package com.zhyen.base;
// Declare any non-default types here with import statements
import com.zhyen.base.Student;

interface IMyService {
     List<Student> getStudent();
     void addStudent(in Student student);
}
