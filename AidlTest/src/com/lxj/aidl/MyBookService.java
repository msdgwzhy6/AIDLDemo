package com.lxj.aidl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class MyBookService extends Service {
	private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<Book>();
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mBooks.add(new Book(1,"java虚拟机"));
		mBooks.add(new Book(2,"android疯狂讲义"));
	}

	private Binder mBinder = new IBookManager.Stub() {
		//虽然IBinder只能传输List,但是服务端之所以可以用CopyOnWriteArrayList是因为它会自动在传输时转化为list
		@Override
		public List<Book> getBookList() throws RemoteException {
			// TODO Auto-generated method stub
			return mBooks;
		}
		
		@Override
		public void addBook(Book book) throws RemoteException {
			// TODO Auto-generated method stub
			mBooks.add(book);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

}
