package com.goluk.common;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import android.os.RemoteException;
import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

public class Common {

	public final static String TAG = "GolukTest-";
	public final static String FindObject = "[Find Object]: ";
	public final static String NotFindObject = "[Not Find Object]: ";
	public final static String TestClick = "[Click Step]: ";
	public final static String TestInfo = "[Test Info]: ";
	public final static String FailedTestInfo = "[Test Step Failed]: ";
	public final static String TestResult="[Test Result]: ";
	public final static String FailReason="[Fail Reason]: ";
	//小米4
//	public final static int[][] al = { { 268, 598 }, { 768, 573 }, { 276, 922 }, { 779, 919 },
//			{ 279, 1186 }, { 751, 1204 }, { 303, 1506 },{752,1471} };
//	public final static int[] idlelocation={930,1375};
//	public final static int[] pause={123,1003};
	//小米Note
//	public final static int[][] al = { { 268, 598 }, { 768, 573 }, { 276, 922 }, { 779, 919 },
//		{ 279, 1186 }, { 751, 1204 }, { 303, 1506 },{752,1471} };
//	public final static int[] idlelocation={935,1514};
//	public final static int[] pause={118,949};
// 三星
//	public final static int[][] al = { { 268, 598 }, { 768, 573 }, { 276, 922 }, { 779, 919 },
//		{ 279, 1186 }, { 751, 1204 }, { 303, 1506 },{752,1471} };
//	public final static int[] idlelocation={928,1234};
//	public final static int[] pause={118,949};
	
	 //魅蓝Note
		public final static int[][] al = { { 274, 612 }, { 811,583 }, { 283,896 }, { 738,935 },
			{ 280,1201 }, { 808,1199 }, { 262,1525 },{ 762,1524 } };
		public final static int[] idlelocation={908,1282};
		public final static int[] pause={141,969};
	
	
	public final static String[] filterName = {"无","清新淡雅","黑白经典","多彩夏日","复古怀旧","缤纷梦幻","柔和静谧","古典胶片"};
	// Open the activity
	public static void openActivity() {
		try {
			Runtime.getRuntime()
					.exec("am start -n cn.com.mobnote.golukmobile/.UserStartActivity");
		} catch (IOException e) {
			System.out.println("Not open");
			e.printStackTrace();
		}
	}

	public static void openActivity(String runcase,UiDevice device, String resourceid)
			throws Exception {
		int x = idlelocation[0];
		int y = idlelocation[1];
		device.click(x, y);
		waitTime(2);
		//skipDownloadnote(runcase,device);
		//skipUpdatenote(runcase,device);
		waitTime(2);
		UiObject object = findViewById2(device,resourceid);
		int i=1;
		while(i<30){
			UiObject liveCancelBtn=Common.findViewByText2(device, "取消");
			if(liveCancelBtn.exists()){
				liveCancelBtn.clickAndWaitForNewWindow();
			}
			if (object.exists()) {
				infoLog(runcase,TestInfo + "The Goluk has been Open");
				break;
			} else {
				infoLog(runcase,TestInfo + "Goluk is loading "+i+"s");
				waitTime(1);
				i++;
			}
		}
		waitTime(3);
		//skipDownloadnote(runcase,device);
		UiObject liveCancelBtn=Common.findViewByText2(device, "取消");
		if(liveCancelBtn.exists()){
			liveCancelBtn.clickAndWaitForNewWindow();
		}
		if (object.exists()) {
			//infoLog(runcase,TestInfo + "The Goluk has been Open");
		}else{
			backToHome(runcase, device);
			infoLog(runcase,TestInfo + "The Goluk has been Open failed, try again.");
			device.click(x, y);
			waitTime(10);
			if(!object.exists()){
				throw new Exception(FailedTestInfo
						+ "Goluk open failed, please check it");
			}
		}
	}

	// Wake up phone
	public static void wakeup(UiDevice device) throws RemoteException {
		device.wakeUp();
		Log.d(TAG, TestInfo + "The device is waked up");
	}

	// Find the item by Id
	public static UiObject findViewById(String runcase,UiDevice devices, String resourceid) throws Exception {
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(resourceid));
		if (object.exists()) {
			infoLog(runcase,FindObject + resourceid);
			return object;
		} else {
			throw new Exception(NotFindObject + resourceid);
		}
	}
	public static UiObject findViewById2(UiDevice devices, String resourceid){
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(resourceid));
			return object;

	}
	public static UiObject findViewByText2(UiDevice devices, String text){
		UiObject object = new UiObject(
				new UiSelector().textContains(text));
		return object;

	}

	public static void skipDownloadnote(String runcase,UiDevice devices) throws UiObjectNotFoundException{
		UiObject object=new UiObject(new UiSelector().resourceIdMatches("cn.com.mobnote.golukmobile:id/rightButton"));
		waitTime(2);
		if(object.exists()){
			infoLog(runcase, TestInfo+"Find the Download Note");
			object.click();
			infoLog(runcase, TestClick+"Click the Cancel Button");
		}else{
			infoLog(runcase, TestInfo+"Not Exist the Download Note");
		}
		
	}
	public static void skipUpdatenote(String runcase,UiDevice devices) throws UiObjectNotFoundException{
		UiObject object=new UiObject(new UiSelector().resourceIdMatches("android:id/button1"));
		waitTime(2);
		if(object.exists()){
			infoLog(runcase, TestInfo+"Find the Update Note");
			object.click();
			infoLog(runcase, TestClick+"Click the Cancel Button");
		}else{
			infoLog(runcase, TestInfo+"Not Exist the Update Note");
		}
		
	}
	public static void clickViewById(String runcase,UiDevice devices, String resourceid) throws Exception {
		waitTime(1);
		Common.checkIPCConnect(runcase, devices);
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(resourceid));
		if (object.exists()) {
			infoLog(runcase,FindObject + resourceid);
			object.clickAndWaitForNewWindow();
			infoLog(runcase,TestClick + resourceid);
		}else{
			infoLog(runcase,NotFindObject + resourceid);
			throw new Exception("NotFindObject" + resourceid);
		} 
	}
	
	public static void clickViewByText(String runcase,UiDevice devices, String text) throws Exception {
		waitTime(1);
		Common.checkIPCConnect(runcase, devices);
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(text));
		if (object.exists()) {
			infoLog(runcase,FindObject + text);
			object.clickAndWaitForNewWindow();
			infoLog(runcase,TestClick + text);
		}else{
			infoLog(runcase,NotFindObject + text);
			throw new Exception("NotFindObject" + text);
		} 
	}
	// Find the item by text
	public static UiObject findViewByText(String runcase,UiDevice devices, String resourceid) throws Exception {
			UiObject object = new UiObject(
					new UiSelector().resourceIdMatches(resourceid));
			if(object.exists()){
				infoLog(runcase,FindObject + resourceid);
				return object;
			}else{
				infoLog(runcase,NotFindObject + resourceid);
				throw new Exception("NotFindObject" + resourceid);
			}
	}

	// Find scrollable view by id
	public static UiScrollable findScrollViewById(String runcase,UiDevice devices, String resourceid) throws Exception {
			UiScrollable object = new UiScrollable(
					new UiSelector().resourceIdMatches(resourceid));
			waitTime(5);
			if(object.exists()){
				infoLog(runcase,FindObject + resourceid);
				return object;
			}else{
				infoLog(runcase,NotFindObject + resourceid);
				throw new Exception("NotFindObject" + resourceid);	
			}
	}

	// Back to Home
	public static void backToHome(String runcase,UiDevice device) {
		for (int i = 0; i < 10; i++) {
			device.pressBack();
		}
		waitTime(5);
		UiObject exitbtn=Common.findViewByText2(device, "确认");
		if(exitbtn.exists()){
			try {
				exitbtn.clickAndWaitForNewWindow();
				waitTime(1);
				infoLog(runcase, TestInfo+"Clicked the exit confirm btn");
			} catch (UiObjectNotFoundException e) {
				e.printStackTrace();
			}
		}
		waitTime(3);
		UiObject exitbtn2=Common.findViewByText2(device, "确认");
		if(exitbtn2.exists()){
			try {
				exitbtn2.clickAndWaitForNewWindow();
			} catch (UiObjectNotFoundException e) {
				e.printStackTrace();
			}
		}
		waitTime(3);
		device.pressBack();
		waitTime(5);
		device.pressHome();
		infoLog(runcase, TestInfo+"Back to Home");
	}

	public static void failcase(String runcase) {
		Log.d(TAG+runcase, TestResult+"The Test Case " + runcase + " Failed");
		System.out.println("["+TAG+runcase+"] "+TestResult+"The Test Case " + runcase + " Failed");

	}

	public static void errorLog(String runcase,String errlog) {
		Log.d(TAG+runcase, FailReason + errlog);
		System.out.println("["+TAG+runcase+"] "+FailReason + errlog);
	}

	public static void passcase(String runcase) {
		Log.d(TAG+runcase, TestResult+"The Test case " + runcase + " Pass");
		System.out.println("["+TAG+runcase+"] "+TestResult+"The Test case " + runcase + " Pass");
	}

	public static void infoLog(String runcase,String logmsg) {
		Log.d(TAG+runcase, logmsg);
		System.out.println("["+TAG+runcase+"] "+logmsg);
	}
	
	public static void startLog(String runcase,String logmsg) {
		Log.d(TAG+runcase, logmsg);
	}

	// Entry the loop video
	public static void entryVideoList(String runcase,UiDevice devices, String resourceid)
			throws Exception {

		UiObject loopVideo = Common.findViewById(runcase,devices,
				resourceid);
		waitTime(2);
		if(loopVideo.exists()){
			infoLog(runcase,FindObject + resourceid);
			loopVideo.clickAndWaitForNewWindow();
			infoLog(runcase,TestClick + resourceid);
		}else{
			infoLog(runcase,NotFindObject + resourceid);
			throw new Exception("NotFindObject" + resourceid);	
		}
	}

	// Create the log folder
	public static void createFolder(String folderName) throws IOException {
		waitTime(1);
		Runtime.getRuntime().exec("mkdir -p /sdcard/GolukTest/" + folderName);	

	}

	// Take a screen
	public static void takeScreen(UiDevice devices, String folderName,String currentTime) throws IOException {
		createFolder(folderName);
		devices.takeScreenshot(new File("/sdcard/GolukTest/" + folderName + "/" + currentTime
				+ ".png"), 0, 50);
	}
	//Take Bug Report
	public static void takeBugReport(String folderName,String currentTime) throws IOException {
//		System.out.println("Begin to Bugreport");
//		Runtime.getRuntime().exec("bugreport > /sdcard/zzzzz.txt");
//		waitTime(10);
//		//Runtime.getRuntime().exec("adb kill-server ");
		
	}
	// Download Video
	public static void downloadVideo(String runcase,UiDevice device, String editbtnid) throws Exception {
		clickViewById(runcase, device, editbtnid);
		for (int n = 0; n < al.length; n++) {
			infoLog(runcase,TestInfo+"The " + (n+1) + " video has been marked");
			int ax = al[n][0];
			int ay = al[n][1];
			device.click(ax, ay);
			waitTime(1);
			}
		UiObject downloadbtn=findViewById(runcase,device,"cn.com.mobnote.golukmobile:id/mDownloadBtn");
		if(downloadbtn.exists()){
			infoLog(runcase,TestInfo+"video has been marked completely");
			downloadbtn.clickAndWaitForNewWindow();
			infoLog(runcase,TestClick+"cn.com.mobnote.golukmobile:id/mDownloadBtn");
		}else{
			infoLog(runcase,TestInfo+"No video has been marked");
			throw new Exception("No video in list since no one to mark");
		}
	}
	// Play Video
	public static void playVideo(String runcase,UiDevice device, String resourceid,int loadtime) {
		for (int n = 0; n < al.length; n++) {
			infoLog(runcase,TestInfo+"The " + (n+1) + " video has been click");
			int ax = al[n][0];
			int ay = al[n][1];
			device.click(ax, ay);
			waitTime(2);
			UiObject obj;
			try {
				UiObject playView =findViewById2(device,"cn.com.mobnote.golukmobile:id/mSurfaceView");

				waitTime(3);
				if(playView.exists()){
					infoLog(runcase,TestInfo+"In Play Screen");
					obj = findViewById2(device,resourceid);
					if (obj.exists()) {
						infoLog(runcase,TestInfo+"Prepare playing...");
						int nloadtime=1;
						while (obj.exists()&& nloadtime<loadtime) {
							waitTime(1);
							infoLog(runcase,TestInfo+"Waiting "+nloadtime+"s");
							nloadtime++;					
						}
						if(nloadtime==loadtime){
							infoLog(runcase,TestInfo+"Loading Timeout");
							device.pressBack();
							waitTime(2);
							throw new Exception("Play Video loading timeout");
						}else{
							infoLog(runcase,TestInfo+"Playing new video...");
							waitTime(10);
							device.click(pause[0], pause[1]);
							waitTime(2);
							device.click(pause[0], pause[1]);
							waitTime(5);
							infoLog(runcase,TestInfo+"Playing End ");
							waitTime(1);
							checkIPCConnect(runcase, device);
						}
						device.pressBack();
						waitTime(2);
					}else{
						infoLog(runcase,TestInfo+"Playing new Video...");
						waitTime(2);
						UiObject playView2=findViewById(runcase,device,"cn.com.mobnote.golukmobile:id/mSurfaceView");
						waitTime(2);
						if(playView2.exists()){
							waitTime(10);
							device.click(pause[0], pause[1]);
							waitTime(1);
							infoLog(runcase, TestInfo+"Click the Pause Btn");
							device.click(pause[0], pause[1]);
							infoLog(runcase, TestInfo+"The video is playing");
							device.pressBack();
						}
						infoLog(runcase,TestInfo+"Playing End ");
						checkIPCConnect(runcase, device);
						waitTime(5);
					}
					}else{
						infoLog(runcase,TestInfo+"No Video in this part");
						System.out.println("No Video in this part");
					}
				} catch (Exception e) {
					infoLog(runcase,e.getMessage());
				}
			}
	}

	//判断是否已连接IPC
	public static void connectWifi(String runcase,UiDevice device, String text)
			throws Exception {
		waitTime(2);
		int waitIPCConnect=1;
		while(waitIPCConnect<31){
			UiObject wifi=Common.findViewByText2(device, text);
			if(wifi.exists()){
				infoLog(runcase,TestInfo+"The IPC is connected");
				break;
			}else{
				waitTime(1);
				infoLog(runcase,TestInfo+"Wait for connected "+waitIPCConnect+"s");
				waitIPCConnect++;
			}
		}
		if(waitIPCConnect==30){
			throw new Exception("The IPC connected Time out for 15s");
		}
		//skipDownloadnote(runcase,device);
	}

	// Skip the user guide
	public static void skipUserGuide(String runcase,UiDevice devices, String userguideId)
			throws Exception {
		waitTime(3);
		skipDownloadnote(runcase,devices);
		UiObject oo = Common.findViewById2(devices, userguideId);
		//UiObject oo = Common.findViewById2(runcase,devices, userguideId);
		if (oo.exists()) {
			oo.clickAndWaitForNewWindow();
			infoLog(runcase,TestClick+userguideId);
		} if(Common.findViewById2(devices, "cn.com.mobnote.golukmobile:id/share_btn").exists()){
			infoLog(runcase,TestInfo+"In map screen");
		}else{
			infoLog(runcase,NotFindObject+userguideId);
			throw new Exception("NotFindObject" + userguideId);
		}

	}
	//遍历滤镜
public static void selectVideoFilter(String runcase,UiDevice devices,int hanldtimeout)
			throws Exception {
		for (int n = 0; n < al.length-3; n++) {
			infoLog(runcase,TestInfo+"The " + (n+1) + " video has been clicked");
			int ax = al[n][0];
			int ay = al[n][1];
			devices.click(ax, ay);
			waitTime(3);
			UiObject filterbtn=Common.findViewById2(devices, "cn.com.mobnote.golukmobile:id/filter_btn");
			if(filterbtn.exists()){
				infoLog(runcase,TestInfo+"Enter the Filter screen successful");
				waitTime(3);
				Common.scrollVideoFilter2(runcase, devices,hanldtimeout);
				waitTime(3);
			}else{
				infoLog(runcase,TestInfo+"No Video in this part");
			}
			}
	}
	//遍历滤镜
	public static void scrollVideoFilter2(String runcase,UiDevice devices,int hanldtimeout)
			throws Exception {
		for (int n = 0; n < filterName.length; n++) {
			UiObject filter=Common.findViewByText2(devices, filterName[n]);
			waitTime(1);
			if(filter.exists()){
				filter.clickAndWaitForNewWindow();
				infoLog(runcase,TestInfo+"The " + filterName[n] + "  has been clicked");
			}else{
				throw new Exception("The filter is not existed");
			}
		}
		Random rand=new Random(47);
		int randInt=rand.nextInt(filterName.length);
		UiObject filter=Common.findViewByText2(devices, filterName[randInt]);
		filter.clickAndWaitForNewWindow();
		waitTime(3);
		UiObject nextStepBtn=Common.findViewById2(devices, "cn.com.mobnote.golukmobile:id/next_btn");
		nextStepBtn.clickAndWaitForNewWindow();
		waitTime(5);
		UiObject playBtn=Common.findViewById2(devices, "cn.com.mobnote.golukmobile:id/play_image");
		if(!playBtn.exists()){
			throw new Exception("The Video is not available");
		}
		UiObject share_image=Common.findViewById2(devices, "cn.com.mobnote.golukmobile:id/share_layout");
		int i=1;
		while(i<hanldtimeout){
			if(share_image.exists()){
				infoLog(runcase,TestInfo+"The video handled Finish");
				break;
			}
			i++;
			waitTime(1);
			infoLog(runcase,TestInfo+"Handled "+i+"s");
		}
		if(i==hanldtimeout){
			
			throw new Exception("Handled time out "+hanldtimeout+"s");
		}
		waitTime(10);
		devices.pressBack();
		waitTime(5);
		UiObject cancelbtn=Common.findViewByText2(devices, "确定");
		if(cancelbtn.exists()){
			cancelbtn.clickAndWaitForNewWindow();
			waitTime(4);
		}
		devices.pressBack();
		if(nextStepBtn.exists()){
			devices.pressBack();
		}
		
	}
	//Not Connect IPC
	public static void checkIPCConnect(String runcase,UiDevice devices)
			throws Exception {
		waitTime(6);
		UiObject waitConnectIPC=Common.findViewByText2(devices, "摄像头断开，正在为您重连…");
		int waitConnect=1;
		while(waitConnect<20){
			if(!waitConnectIPC.exists()){
				break;
			}
			waitConnect++;
		}
		UiObject oo = Common.findViewByText2(devices, "您好像没有连接摄像头哦。");
		//UiObject oo = Common.findViewById2(runcase,devices, userguideId);
		if (oo.exists()) {
			UiObject oo2 = Common.findViewById2(devices, "cn.com.mobnote.golukmobile:id/leftButton");
			oo2.clickAndWaitForNewWindow();
			infoLog(runcase,TestInfo+"IPC disconnected");
			throw new Exception("IPC disconnected");
		}

	}
	//Update file
	public static void updatefile(String runcase,UiDevice device,String msgId){
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(msgId));
		if (object.exists()) {
			infoLog(runcase,FindObject + msgId);
			try {
				object.clickAndWaitForNewWindow();
			} catch (UiObjectNotFoundException e){
				e.printStackTrace();
			}
		} 
	}
	
	public static void waitTime(int n) {
		long time = n * 1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static String checkFailReason(UiDevice device, String eMessage){
		String s=null;
		UiObject AppCrash=Common.findViewByText2(device, "Unfortunately");
		UiObject ANR=Common.findViewByText2(device, "极路客 isn't responding.");
		if(AppCrash.exists()){
			s="App Crash happened";
		}else if(ANR.exists()){
			s="ANR happened";
		}else{
			s=eMessage;
		}
		waitTime(10);
		UiObject AppCrashBtn=Common.findViewByText2(device, "OK");
		if(AppCrashBtn.exists()){
			try {
				waitTime(3000);
				AppCrashBtn.clickAndWaitForNewWindow();
			} catch (UiObjectNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return s;
	}
	public static void scrollDown(String runcase,UiDevice device){
		device.swipe(557, 721, 557, 1653, 10);
		infoLog(runcase,TestInfo+"Scroll Down");
	}
	public static void scrollUp(String runcase,UiDevice device){
		device.swipe(557, 1653, 557, 721, 10);
		infoLog(runcase,TestInfo+"Scroll Down");
	}
	
}
