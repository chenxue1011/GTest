package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class PlayLocalLoopVideoTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="PlayLocalLoopVideoTest";
	public void testcase() throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(2000);
			//选择我
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/more_btn");
			//选择本地视频
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/local_video_item");
			
			UiObject loopvideolistwaitnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mProgressBar");
			int waitforloopvideolist=1;
			while(waitforloopvideolist<15){
				if(loopvideolistwaitnote.exists()){
					sleep(1000);
					Common.infoLog(runcase,"加载本地循环影像列表"+waitforloopvideolist+"秒");
				}else{
					break;
				}
				waitforloopvideolist++;
			}
			sleep(2000);
			
			Common.clickViewById(runcase,in, "cn.com.mobnote.golukmobile:id/video_xhyx");
			Common.infoLog(runcase,"开始执行播放本地循环视频影像测试用例");
			sleep(5000);
			UiScrollable us=Common.findScrollViewById(runcase,in, "cn.com.mobnote.golukmobile:id/mLoopVideoList");
			sleep(2000);
			for(int nPlayCount=1;nPlayCount<3;nPlayCount++){
				Common.infoLog(runcase,"第 "+nPlayCount+" 页");
				Common.playVideo(runcase,in,"cn.com.mobnote.golukmobile:id/mSurfaceView",5);
				us.scrollForward();
			}
			Common.backToHome(runcase,in);
			Common.passcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		} catch (Exception e) {			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date curDate = new Date(System.currentTimeMillis());
			String str = formatter.format(curDate);
			Common.infoLog(runcase,"截图存储在 /sdcard/GolukTest/"+runcase+"/"+str+".png");
			Common.takeScreen(in, runcase,str);
			sleep(2000);
			String s=null;
			s=Common.checkFailReason(runcase,in, e.getMessage());
			Common.backToHome(runcase,in);
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
			
		}
	}

}
