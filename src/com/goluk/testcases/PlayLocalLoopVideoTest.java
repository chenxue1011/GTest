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

public class PlayLocalLoopVideoTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="PlayLocalLoopVideoTest";
	public void testcase() throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/user_start_have");
			sleep(4000);
			//skip the userguidId
			Common.skipUserGuide(runcase,in, "cn.com.mobnote.golukmobile:id/user_start_look");
			//选择 更多
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/more_btn");
			//选择本地视频
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/local_video_item");
			
			UiObject loopvideolistwaitnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mProgressBar");
			int waitforloopvideolist=1;
			while(waitforloopvideolist<15){
				if(loopvideolistwaitnote.exists()){
					Common.infoLog(runcase,"Waiting for loop video list"+(waitforloopvideolist+2)+"s");
					sleep(2000);
				}else{
					break;
				}
				waitforloopvideolist++;
			}
			sleep(2000);
			
			Common.infoLog(runcase,"Start to Play Local Loop Video");
			Common.clickViewById(runcase,in, "cn.com.mobnote.golukmobile:id/video_xhyx");
			sleep(5000);
			UiScrollable us=Common.findScrollViewById(runcase,in, "cn.com.mobnote.golukmobile:id/mLoopVideoList");
			sleep(2000);
			for(int nPlayCount=1;nPlayCount<3;nPlayCount++){
				Common.infoLog(runcase,"The "+nPlayCount+" Page");
				Common.playVideo(runcase,in,"cn.com.mobnote.golukmobile:id/mLoading",15);
				us.scrollForward();
			}
			Common.backToHome(runcase,in);
			Common.passcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		} catch (Exception e) {
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
