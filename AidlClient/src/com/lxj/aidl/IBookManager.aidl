package com.lxj.aidl;

import com.lxj.aidl.Book;

interface IBookManager {
     List<Book> getBookList();
     void addBook(in Book book);

}