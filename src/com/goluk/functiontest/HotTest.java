package com.goluk.functiontest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class HotTest extends UiAutomatorTestCase {
	public final static UiDevice device=UiDevice.getInstance();
	public final static String runcase="HotTest";
	public void testcase() throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			//通过是否有IPC按钮判断是否进入
			Common.openActivity(runcase,device,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(4000);
			Common.connectWifi(runcase,device, "已连接Goluk");
			sleep(2000);
			//点击发现
			Common.clickViewById(runcase, device, "cn.com.mobnote.golukmobile:id/index_square_btn");
			//点击热门
			Common.clickViewById(runcase, device, "cn.com.mobnote.golukmobile:id/hot_title");
			for(int i=0;i<4;i++){
				device.swipe(557, 721, 557, 1653, 10);
				sleep(2000);
				}
//			if(de.exists()){
//				throw new Exception("No any hot view, plase check it");
//			}
			
			Common.backToHome(runcase,device);
			Common.passcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}catch(Exception e){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date curDate = new Date(System.currentTimeMillis());
			String str = formatter.format(curDate);
			Common.infoLog(runcase,"The screen save in /sdcard/GolukTest/"+runcase+"/"+str+".png");
			Common.takeScreen(device, runcase,str);
			sleep(2000);
			//Common.takeBugReport(runcase, str);
			String s=null;
			s=Common.checkFailReason(device, e.getMessage());
			Common.backToHome(runcase,device);
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}
}
