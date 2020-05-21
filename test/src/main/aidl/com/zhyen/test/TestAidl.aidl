// TestAidl.aidl
package com.zhyen.test;
import com.zhyen.test.Book;
// Declare any non-default types here with import statements

interface TestAidl {
       List<Book> getBookList();

       void addBookInOut(inout Book book);
}
