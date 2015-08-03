package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class RequestLiveTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="RequestLiveTest";
	public void testcase()throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(4000);
			Common.connectWifi(runcase,in, "已连接Goluk");
			sleep(2000);
			//点击分享
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/index_share_btn");
			//点击分享网络直播
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/share_mylive_btn");
			//点击 进入直播间
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/live_bottomlayout");
			int wait=1;
			UiObject waitNote=Common.findViewById2(in, "android:id/message");
			while(wait<60){
				UiObject tryAgain=Common.findViewById2(in, "android:id/button1");
				if(tryAgain.exists()){
					tryAgain.clickAndWaitForNewWindow();
				}
				if(waitNote.exists()){
					sleep(1000);
					Common.infoLog(runcase, "Waiting the time "+wait+"s");
					wait++;
				}else{
					Common.infoLog(runcase, "Start to Live 5s");
					sleep(5000);
					//点击退出直播按钮
					Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/live_exit_btn");
					sleep(5000);
					Common.clickViewById(runcase, in, "android:id/button1");
					break;
				}
			}
			if(wait==60){
				throw new Exception("Live loading time out 60s");
			}
			
			Common.backToHome(runcase,in);
			Common.passcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}catch(Exception e){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date curDate = new Date(System.currentTimeMillis());
			String str = formatter.format(curDate);
			Common.infoLog(runcase,"The screen save in /sdcard/GolukTest/"+runcase+"/"+str+".png");
			Common.takeScreen(in, runcase,str);
			sleep(5000);
			Common.takeBugReport(runcase, str);
			String s=null;
			s=Common.checkFailReason(in, e.getMessage());
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}	
}
