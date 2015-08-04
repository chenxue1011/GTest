package com.goluk.testcases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goluk.common.Common;

public class DownloadFavoriteVideoTest extends UiAutomatorTestCase {
	public final static UiDevice in=UiDevice.getInstance();
	public final static String runcase="DownloadFavoriteVideoTest";
	public void testcase()throws IOException{
		try{
			Common.startLog(runcase,"*****Start to run "+runcase+" *****");
			//通过是否有IPC按钮判断是否进入
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
					Common.infoLog(runcase,"视频预览加载第 "+i+" 秒");
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
			//选择IPC文件管理
			Common.clickViewById(runcase, in, "cn.com.mobnote.golukmobile:id/mFileLayout");
			sleep(1000);
			UiObject loopvideolistwaitnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mProgressBar");
			int waitforloopvideolist=1;
			while(waitforloopvideolist<15){
				if(loopvideolistwaitnote.exists()){
					Common.infoLog(runcase,"加载远程循环影像列表第 "+waitforloopvideolist+"秒");
					sleep(1000);
				}else{
					break;
				}
				waitforloopvideolist++;
			}
			sleep(1000);
			Common.infoLog(runcase,"开始下载精彩视频测试用例");
			Common.entryVideoList(runcase,in,"cn.com.mobnote.golukmobile:id/video_jcsp");
			UiObject favoritevideolistwaitnote=Common.findViewById2(in, "cn.com.mobnote.golukmobile:id/mProgressBar");
			int waitforfavoritevideolist=1;
			while(waitforfavoritevideolist<11){
				if(favoritevideolistwaitnote.exists()){
					Common.infoLog(runcase,"加载远程精彩视频列表第"+waitforfavoritevideolist+"秒");
					sleep(1000);
				}else{
					break;
				}
				waitforfavoritevideolist++;
			}
			sleep(1000);
			UiScrollable us=Common.findScrollViewById(runcase,in, "cn.com.mobnote.golukmobile:id/mWonderfulVideoList");
			sleep(2000);
			for(int nPlayCount=1;nPlayCount<3;nPlayCount++){
				Common.infoLog(runcase,"第 "+nPlayCount+" 页");
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
			Common.infoLog(runcase,"截图保存在 /sdcard/GolukTest/"+runcase+"/"+str+".png");
			Common.takeScreen(in, runcase,str);
			sleep(2000);
			//Common.takeBugReport(runcase, str);
			String s=null;
			s=Common.checkFailReason(runcase,in, e.getMessage());
			Common.backToHome(runcase,in);
			Common.errorLog(runcase,s);
			Common.failcase(runcase);
			Common.startLog(runcase,"*****End to run "+runcase+" *****");
		}
	}	
}
