package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class DownloadLoopVideoTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="DownloadLoopVideoTest";
	public void testcase()throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/user_start_have");
			sleep(4000);
			//skip the userguidId
			Common.skipUserGuide(runcase,in, "cn.com.mobnote.golukmobile:id/user_start_look");
			//Connect to wifi
			Common.connectWifi(runcase,in, "cn.com.mobnote.golukmobile:id/wifi_conn_txt");
			sleep(2000);
			//加载视频预览，15秒超时
			int i=1;
			UiObject waitingnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mLoading");
			while(i<16){
				if(waitingnote.exists()){
					sleep(1000);
					Common.infoLog(runcase,"Loading Preview"+i+"sec");
				}else{
					Common.infoLog(runcase,"Loading Finish");
					sleep(1000);
					break;
				}
				i++;
			}
			if(i==15){
				throw new Exception("Loading more than 15s");
			}
			UiObject fm=Common.findViewById(runcase,in, "cn.com.mobnote.golukmobile:id/mFileLayout");
			fm.clickAndWaitForNewWindow();
			sleep(2000);
			
			Common.infoLog(runcase,"Star Download from Loop Video");
			UiObject loopvideolistwaitnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mProgressBar");
			int waitforloopvideolist=1;
			while(waitforloopvideolist<16){
				if(loopvideolistwaitnote.exists()){
					Common.infoLog(runcase,"Loading loop video list"+waitforloopvideolist+"s");
					sleep(1000);
				}else{
					break;
				}
				waitforloopvideolist++;
			}
			sleep(2000);
			Common.entryVideoList(runcase,in,"cn.com.mobnote.golukmobile:id/video_xhyx");
			UiScrollable us=Common.findScrollViewById(runcase,in, "cn.com.mobnote.golukmobile:id/mLoopVideoList");
			sleep(2000);
			for(int nPlayCount=1;nPlayCount<3;nPlayCount++){
				Common.infoLog(runcase,"The "+nPlayCount+" Page");
				Common.downloadVideo(runcase,in,"cn.com.mobnote.golukmobile:id/mEditBtn");
				sleep(5000);
				us.scrollForward();
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
			String s=null;
			UiObject AppCrash=Common.findViewByText2(in, "Unfortunately");
			if(AppCrash.exists()){
				s="App Crash happened";
			}else{
				s=e.getMessage();
			}
			Common.backToHome(runcase,in);
			UiObject AppCrashBtn=Common.findViewByText2(in, "OK");
			if(AppCrashBtn.exists()){
				try {
					sleep(1000);
					AppCrashBtn.clickAndWaitForNewWindow();
				} catch (UiObjectNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			UiObject ANR=Common.findViewByText2(in, "极路客 isn't responding.");
			if(ANR.exists()){
				s="ANR happened";
			}else{
				s=e.getMessage();
			}
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}	
}
