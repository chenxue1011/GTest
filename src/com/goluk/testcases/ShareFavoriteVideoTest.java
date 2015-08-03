package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class ShareFavoriteVideoTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="ShareFavoriteVideoTest";
	public void testcase()throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(4000);
			Common.connectWifi(runcase,in, "已连接Goluk");
			sleep(2000);
			//点击分享
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/index_share_btn");
			//点击分享精彩视频
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/share_local_video_btn");
			//点击精彩视频
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/video_jcsp");
			
			Common.selectVideoFilter(runcase, in,60);
			
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
			Common.backToHome(runcase,in);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}	
}
