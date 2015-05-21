package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class PlayFavoriteVideoTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="PlayFavoriteVideoTest";
	public void testcase() throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(4000);
			Common.connectWifi(runcase,in, "已连接Goluk");
			sleep(2000);
			//点击IPC连接按钮
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			
			int i=1;
			UiObject waitingnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mLoading");
			while(i<11){
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
			if(i==11){
				throw new Exception("Loading more than 10s");
			}
			sleep(2000);
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/mFileLayout");
			sleep(1000);
			UiObject loopvideolistwaitnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mProgressBar");
			int waitforloopvideolist=1;
			while(waitforloopvideolist<15){
				if(loopvideolistwaitnote.exists()){
					Common.infoLog(runcase,"Waiting for loop video list"+waitforloopvideolist+"s");
					sleep(1000);
				}else{
					break;
				}
				waitforloopvideolist++;
			}
			sleep(2000);
	
			Common.clickViewById(runcase,in, "cn.com.mobnote.golukmobile:id/video_jcsp");
			Common.infoLog(runcase,"Start to Play Favorite Video");
			sleep(2000);
			UiObject loopvideolistwaitnote2=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mProgressBar");
			waitforloopvideolist=1;
			while(waitforloopvideolist<11){
				if(loopvideolistwaitnote2.exists()){
					Common.infoLog(runcase,"Loading favorite video list"+waitforloopvideolist+"s");
					sleep(1000);
				}else{
					break;
				}
				waitforloopvideolist++;
			}
			sleep(2000);  
			UiScrollable us=Common.findScrollViewById(runcase,in, "cn.com.mobnote.golukmobile:id/mWonderfulVideoList");
			sleep(2000);
			for(int nPlayCount=1;nPlayCount<3;nPlayCount++){
				Common.infoLog(runcase,"The "+nPlayCount+" Page");
				Common.playVideo(runcase,in,"cn.com.mobnote.golukmobile:id/mLoading",11);
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
			s=Common.checkFailReason(in, e.getMessage());
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}
}
