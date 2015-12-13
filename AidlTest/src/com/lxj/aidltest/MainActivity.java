package com.lxj.aidltest;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;

import com.lxj.aidl.Book;
import com.lxj.aidl.IBookManager;
import com.lxj.aidl.MyBookService;

public class MainActivity extends Activity {
	private IBookManager mBookManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent i = new Intent(this,MyBookService.class);
        bindService(i, connection, Context.BIND_AUTO_CREATE);

	}
	//Binder死亡时可以重新绑定代理
	private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
		
		@Override
		public void binderDied() {
			// TODO Auto-generated method stub
			if(mBookManager == null){
				return;
			}else{
				mBookManager.asBinder().unlinkToDeath(deathRecipient, 0);
				mBookManager = null;
			}
		}
	};
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mBookManager = IBookManager.Stub.asInterface(service);//获得由Binder转换成的所需的AIDL接口对象
			 try {
				 mBookManager.asBinder().linkToDeath(deathRecipient, 0);
				List<Book> list = mBookManager.getBookList();

				 Log.i("book list", "query book list:" + list.toString());
				 Book newBook = new Book(3, "Android宝典");
				 mBookManager.addBook(newBook);
				 List<Book> newList = mBookManager.getBookList();
				 Log.i("book list", "query book list:" + newList.toString());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		 unbindService(connection);
		super.onDestroy();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
