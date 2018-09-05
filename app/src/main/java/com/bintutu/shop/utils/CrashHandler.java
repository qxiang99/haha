package com.bintutu.shop.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Process;
import java.io.File;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义异常处理器
 */
public class CrashHandler implements UncaughtExceptionHandler {

	private Context mContext;
	private UncaughtExceptionHandler defaultUncaught;
	private java.io.File logFile = new File(AppConstant.CRASH, System.currentTimeMillis()+".txt");
	private java.io.File File = new File(AppConstant.CRASH);
	//private File logFile = new File(AppConstant.CRASH, "crashLog.trace");

	public CrashHandler(Context context) {
		super();
		mContext = context;
		upLoadErrorFileToServer();
	    defaultUncaught = Thread.getDefaultUncaughtExceptionHandler();
	    Thread.setDefaultUncaughtExceptionHandler(this); // 设置为当前线程默认的异常处理器
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		
		// 打印当前的异常信息
		ex.printStackTrace();
		
		// 如果我们没处理异常，并且系统默认的异常处理器不为空，则交给系统来处理
		if(!handlelException(ex) && defaultUncaught != null){
			defaultUncaught.uncaughtException(thread, ex);
		}else{
			
			// 已经记录完log, 提交服务器
			//upLoadErrorFileToServer(logFile);
			
			/*Intent in = new Intent(mContext, MainActivity.class);
			in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 如果设置了此标志，这个activity将成为一个新task的历史堆栈中的第一个activity
			mContext.startActivity(in);*/
			
			// 杀死我们的进程
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Process.killProcess(Process.myPid()); // 杀死线程
				}
			}, 2 * 1000);
		}
	}

	/**
	 * 上传log到服务器
	 * */
	private void upLoadErrorFileToServer() {

	}

	/**
	 * 记录异常信息
	 * */
	private boolean handlelException(Throwable ex) {
		if(ex == null){
			return false;
		}
		PrintWriter pw = null;
		try {
			if(!File.exists()){
				File.mkdirs();
			}
			if(!logFile.exists()){
				logFile.createNewFile();
			}
			pw = new PrintWriter(logFile);
			// 收集手机及错误信息
			collectInfoToSDCard(pw, ex);
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 收集记录错误信息
	 * @throws NameNotFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * */
	private void collectInfoToSDCard(PrintWriter pw, Throwable ex) throws NameNotFoundException, IllegalAccessException, IllegalArgumentException {
		PackageManager pm = mContext.getPackageManager();
		PackageInfo mPackageInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
		
		pw.println("time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 记录错误发生的时间
		pw.println("versionCode: " + mPackageInfo.versionCode); // 版本号
		pw.println("versionName: " + mPackageInfo.versionName); // 版本名称
		
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			pw.print(field.getName() + " : ");
			pw.println(field.get(null).toString());
		}
		ex.printStackTrace(pw);
	}
}