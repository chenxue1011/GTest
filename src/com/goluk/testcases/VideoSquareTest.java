package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class VideoSquareTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="VideoSquareTest";
	public final static String[] item={"cn.com.mobnote.golukmobile:id/category_btn_one",
		"cn.com.mobnote.golukmobile:id/category_btn_three","cn.com.mobnote.golukmobile:id/category_btn_five",
		"cn.com.mobnote.golukmobile:id/category_btn_four"};
	public void testcase() throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(2000);
			//选择发现
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/index_square_btn");
			//选择广场
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/square_title");
			//选择栏目
			for(int i=0;i<item.length;i++){
				Common.clickViewById(runcase, in, item[i]);
				sleep(2000);
				int waittime=1;
				while(waittime<31){
					UiObject title=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/title_layout");
					if(title.exists()){
						Common.scrollDown(runcase, in, "android.widget.ListView", 5);
						for(int y=1;y<5;y++){
							Common.infoLog(runcase, "Play the "+item[i]+" "+y+" times Video");
							Common.playSquareVide(runcase, in);
							sleep(1000);
							Common.scrollUp(runcase, in, "android.widget.ListView", 2);
							}
						waittime=31;
					}else{
						Common.infoLog(runcase, "Loading "+waittime+"s");
						sleep(1000);
						waittime=waittime+1;
					}
					in.pressBack();
				}

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

