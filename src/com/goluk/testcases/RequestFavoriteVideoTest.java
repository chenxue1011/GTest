package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class RequestFavoriteVideoTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="RequestFavoriteVideoTest";
	public void testcase() throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			Common.openActivity(runcase,in,"cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(2000);
			Common.connectWifi(runcase,in, "已连接Goluk");
			sleep(1000);
			//点击IPC连接按钮
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/index_carrecoder_btn");
			sleep(1000);
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/mPlayBtn");
			sleep(1000);
			int i=1;
			UiObject waitingnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mLoading");
			while(i<16){
				if(waitingnote.exists()){
					sleep(1000);
					Common.infoLog(runcase,"视频预览加载第 "+i+"秒");
				}else{
					Common.infoLog(runcase,"加载完成，预览成功");
					sleep(1000);
					break;
				}
				i++;
			}
			if(i==16){
				throw new Exception("视频预览加载超时"+i+"秒，失败");
			}
			sleep(2000);
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/m8sBtn");
			sleep(7000);
			int waitforshare=1;
			UiObject shareBtn=null;
			while(waitforshare<30){
				shareBtn=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mShareBtn");
				if(shareBtn.exists()){
					Common.infoLog(runcase, "找到 即可分享 按钮");
					sleep(3000);
					break;
				}
				sleep(5000);
				waitforshare++;
			}
			if(waitforshare==30){
				throw new Exception("失败，因为 即可分享 按钮没有出现.");
			}
			Common.backToHome(runcase,in);
			Common.passcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		} catch (Exception e) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date curDate = new Date(System.currentTimeMillis());
			String str = formatter.format(curDate);
			Common.infoLog(runcase,"截图存储在 in /sdcard/GolukTest/"+runcase+"/"+str+".png");
			Common.takeScreen(in, runcase,str);
			String s=null;
			s=Common.checkFailReason(runcase,in, e.getMessage());
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}
}
