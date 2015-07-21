package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class VideoHotTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="VideoHotTest";
	public void testcase() throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(2000);
			//选择发现
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/index_square_btn");
			//选择热门
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/hot_title");
			//下拉刷新
			Common.scrollDown(runcase, in, "android.widget.ListView", 5, 8);
			sleep(5000);
			for(int i=1;i<6;i++){
				Common.infoLog(runcase, "Play the "+i+" times");
				Common.playSquareVide(runcase, in);
				Common.scrollUp(runcase, in, "android.widget.ListView", 1, 8);
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
			s=Common.checkFailReason(in, e.getMessage());
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}
}

