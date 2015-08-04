package com.goluk.common;

import java.io.File;
import java.io.IOException;
import java.util.Random;

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
	public final static String TestResult = "[Test Result]: ";
	public final static String FailReason = "[Fail Reason]: ";
	// 华为MT7
	public final static int[][] al = { { 253, 603 }, { 782, 581 },
			{ 252, 930 }, { 772, 930 }, { 276, 1190 }, { 814, 2101 },
			{ 295, 1493 }, { 901, 1542 } };
	public final static int[] idlelocation = { 958, 1360 };
	public final static int[] pause = { 136, 960 };
	// 小米Note
	// public final static int[][] al = { { 268, 598 }, { 768, 573 }, { 276, 922
	// }, { 779, 919 },
	// { 279, 1186 }, { 751, 1204 }, { 303, 1506 },{752,1471} };
	// public final static int[] idlelocation={935,1514};
	// public final static int[] pause={118,949};
	// 三星
	// public final static int[][] al = { { 268, 598 }, { 768, 573 }, { 276, 922
	// }, { 779, 919 },
	// { 279, 1186 }, { 751, 1204 }, { 303, 1506 },{752,1471} };
	// public final static int[] idlelocation={928,1234};
	// public final static int[] pause={118,949};

	// 魅蓝Note
	// public final static int[][] al = { { 274, 612 }, { 811, 583 },
	// { 283, 896 }, { 738, 935 }, { 280, 1201 }, { 808, 1199 },
	// { 262, 1525 }, { 762, 1524 } };
	// public final static int[] idlelocation = { 908, 1282 };
	// public final static int[] pause = { 141, 969 };

	public final static String[] filterName = { "无", "清新淡雅", "黑白经典", "多彩夏日",
			"复古怀旧", "缤纷梦幻", "柔和静谧", "古典胶片" };

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

	/**
	 * 通过坐标点击App
	 * @param runcase
	 *            测试用例名称
	 * @param device
	 *            UiDevice.getInstance()实例
	 * @param resourceid
	 *            验证App打开的控件，比如行车记录仪控件
	 * @throws Exception
	 *             打开失败跑出异常
	 */
	public static void openActivity(String runcase, UiDevice device,
			String resourceid) throws Exception {
		// 点击启动App
		int x = idlelocation[0];
		int y = idlelocation[1];
		// 点击Goluk坐标
		device.click(x, y);
		waitTime(2);
		UiObject object = findViewById2(device, resourceid);
		int i = 1;
		while (i < 30) {
			// 取消升级提醒
			if (findViewByText2(device, "稍后再说").exists()) {
				findViewByText2(device, "稍后再说").clickAndWaitForNewWindow();
				infoLog(runcase, TestInfo + "Goluk已经取消升级");
			}
			// 取消续播提示
			if (findViewByText2(device, "取消").exists()) {
				findViewByText2(device, "取消").clickAndWaitForNewWindow();
			}
			if (object.exists()) {
				infoLog(runcase, TestInfo + "Goluk可以正常工作");
				break;
			}
			if (findViewById2(device,
					"cn.com.mobnote.golukmobile:id/loading_bg").exists()) {
				infoLog(runcase, TestInfo + "Goluk正在加载 " + i + "s");
				waitTime(1);
				i++;
			}
		}
		waitTime(3);
		if (i == 30) {
			backToHome(runcase, device);
			throw new Exception(FailedTestInfo + "Goluk 打开失败, 请检查");
		}
	}

	/**
	 * 强查找控件，不存在抛出异常，当前用例失败
	 * 
	 * @param runcase
	 *            测试用例名称
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param resourceid
	 *            控件id
	 * @return 如果存在返回控件，不存在抛出控件不存在的异常
	 * @throws Exception
	 */
	public static UiObject findViewById(String runcase, UiDevice devices,
			String resourceid) throws Exception {
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(resourceid));
		if (object.exists()) {
			infoLog(runcase, FindObject + resourceid);
			return object;
		} else {
			throw new Exception(NotFindObject + resourceid);
		}
	}

	/**
	 * 弱查找空间，不存在返回空，不会抛异常，当前用例正常执行
	 * 
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param resourceid
	 *            控件id
	 * @return 如果存在返回控件，不存在返回空
	 */
	public static UiObject findViewById2(UiDevice devices, String resourceid) {
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(resourceid));
		return object;

	}

	/**
	 * 强查找控件，不存在抛出异常，当前用例失败
	 * 
	 * @param runcase
	 *            测试用例名称
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param text
	 *            控件text
	 * @return 如果存在返回控件，不存在抛出控件不存在的异常
	 * @throws Exception
	 */
	public static UiObject findViewByText(String runcase, UiDevice devices,
			String text) throws Exception {
		UiObject object = new UiObject(new UiSelector().resourceIdMatches(text));
		if (object.exists()) {
			infoLog(runcase, FindObject + text);
			return object;
		} else {
			infoLog(runcase, NotFindObject + text);
			throw new Exception("NotFindObject" + text);
		}
	}

	/**
	 * 弱查找控件，不存在返回空，不会抛异常，当前用例正常执行
	 * 
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param text
	 *            控件文本text
	 * @return 如果存在返回控件，不存在返回空
	 */
	public static UiObject findViewByText2(UiDevice devices, String text) {
		UiObject object = new UiObject(new UiSelector().textContains(text));
		return object;

	}

	/**
	 * 强查找控件通过控件id,之后点击
	 * 
	 * @param runcase
	 *            测试用例名称
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param resourceid
	 *            控件id
	 * @throws Exception
	 *             控件找不到抛出不存在异常，测试用例执行失败
	 */
	public static void clickViewById(String runcase, UiDevice devices,
			String resourceid) throws Exception {
		UiObject object = new UiObject(
				new UiSelector().resourceIdMatches(resourceid));
		if (object.exists()) {
			infoLog(runcase, FindObject + resourceid);
			object.clickAndWaitForNewWindow();
			infoLog(runcase, TestClick + resourceid);
		} else {
			infoLog(runcase, NotFindObject + resourceid);
			throw new Exception("NotFindObject" + resourceid);
		}
	}

	/**
	 * 强查找控件通过控件text,之后点击
	 * 
	 * @param runcase
	 *            测试用例名称
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param text
	 *            控件text
	 * @throws Exception
	 *             控件找不到抛出不存在异常，测试用例执行失败
	 */
	public static void clickViewByText(String runcase, UiDevice devices,
			String text) throws Exception {
		UiObject object = new UiObject(new UiSelector().resourceIdMatches(text));
		if (object.exists()) {
			infoLog(runcase, FindObject + text);
			object.clickAndWaitForNewWindow();
			infoLog(runcase, TestClick + text);
		} else {
			infoLog(runcase, NotFindObject + text);
			throw new Exception("NotFindObject" + text);
		}
	}

	/**
	 * 强查找可翻滚控件，存在返回控件对象，不存在抛异常，当前测试停止
	 * 
	 * @param runcase
	 *            测试用例名称
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param resourceid
	 *            控件id
	 * @return 返回控件对象
	 * @throws Exception
	 *             抛出不存在异常
	 */
	public static UiScrollable findScrollViewById(String runcase,
			UiDevice devices, String resourceid) throws Exception {
		UiScrollable object = new UiScrollable(
				new UiSelector().resourceIdMatches(resourceid));
		if (object.exists()) {
			infoLog(runcase, FindObject + resourceid);
			return object;
		} else {
			infoLog(runcase, NotFindObject + resourceid);
			throw new Exception("NotFindObject" + resourceid);
		}
	}

	/**
	 * 返回桌面
	 * 
	 * @param runcase
	 *            测试用例名称
	 * @param device
	 *            UiDevice.getInstance()实例
	 */
	public static void backToHome(String runcase, UiDevice device) {
		for (int i = 0; i < 10; i++) {
			device.pressBack();
		}
		waitTime(5);
		UiObject exitbtn = findViewByText2(device, "确认");
		if (exitbtn.exists()) {
			try {
				exitbtn.clickAndWaitForNewWindow();
				waitTime(1);
				infoLog(runcase, TestInfo + "点击 确认 按钮退出");
			} catch (UiObjectNotFoundException e) {
				e.printStackTrace();
			}
		}
		device.pressBack();
		waitTime(3);
		device.pressHome();
		infoLog(runcase, TestInfo + "返回Home主页");
	}

	/**
	 * 失败测试用例结果呈现
	 * 
	 * @param runcase
	 *            测试用例
	 */
	public static void failcase(String runcase) {
		Log.d(TAG + runcase, TestResult + "The Test Case " + runcase
				+ " Failed");
		System.out.println("[" + TAG + runcase + "] " + TestResult
				+ "The Test Case " + runcase + " Failed");

	}

	/**
	 * 
	 * @param device
	 *            UiDevice.getInstance()实例
	 * @param eMessage
	 * @return 返回失败原因，ANR,APPCRASH或控件找不到等
	 */
	public static String checkFailReason(String runcase, UiDevice device,
			String eMessage) {
		String s = null;
		Common.infoLog(runcase, "发生异常，等待30秒捕获");
		waitTime(30);
		UiObject AppCrash = findViewByText2(device, "Unfortunately");
		UiObject ANR = findViewByText2(device, "极路客 isn't responding.");
		if (AppCrash.exists()) {
			s = "App Crash happened";
		} else if (ANR.exists()) {
			s = "ANR happened";
		} else {
			s = eMessage;
		}
		waitTime(10);
		UiObject AppCrashBtn = Common.findViewByText2(device, "OK");
		if (AppCrashBtn.exists()) {
			try {
				waitTime(5);
				AppCrashBtn.clickAndWaitForNewWindow();
				waitTime(20);
				backToHome(runcase, device);
				waitTime(10);
				backToHome(runcase, device);
			} catch (UiObjectNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * 失败测试用例失败原因
	 * 
	 * @param runcase
	 *            测试用例
	 * @param errlog
	 *            失败Trace
	 */
	public static void errorLog(String runcase, String errlog) {
		Log.d(TAG + runcase, FailReason + errlog);
		System.out.println("[" + TAG + runcase + "] " + FailReason + errlog);
	}

	/**
	 * 通过测试用例总结
	 * 
	 * @param runcase
	 *            测试用例
	 */
	public static void passcase(String runcase) {
		Log.d(TAG + runcase, TestResult + "The Test case " + runcase + " Pass");
		System.out.println("[" + TAG + runcase + "] " + TestResult
				+ "The Test case " + runcase + " Pass");
	}

	/**
	 * 
	 * @param runcase
	 *            测试用例
	 * @param logmsg
	 *            需要打印的LOG
	 */
	public static void infoLog(String runcase, String logmsg) {
		Log.d(TAG + runcase, logmsg);
		System.out.println("[" + TAG + runcase + "] " + logmsg);
	}

	/**
	 * 
	 * @param runcase
	 *            测试用例
	 * @param logmsg
	 *            开始/结束执行用例提示
	 */
	public static void startLog(String runcase, String logmsg) {
		Log.d(TAG + runcase, logmsg);
	}

	/**
	 * 选择进入某个列表
	 * 
	 * @param runcase
	 *            测试用例
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param resourceid
	 *            列表ID
	 * @throws Exception
	 *             抛出找不到异常
	 */
	public static void entryVideoList(String runcase, UiDevice devices,
			String resourceid) throws Exception {
		UiObject videoList = findViewById(runcase, devices, resourceid);
		waitTime(1);
		if (videoList.exists()) {
			infoLog(runcase, FindObject + resourceid);
			videoList.clickAndWaitForNewWindow();
			infoLog(runcase, TestClick + resourceid);
		} else {
			infoLog(runcase, NotFindObject + resourceid);
			throw new Exception("NotFindObject" + resourceid);
		}
	}

	/**
	 * 在手机路径/sdcard/GolukTest/创建对应用例的文件夹，用于存放失败截图等
	 * 
	 * @param folderName
	 * @throws IOException
	 */
	public static void createFolder(String runcase) throws IOException {
		waitTime(1);
		Runtime.getRuntime().exec("mkdir -p /sdcard/GolukTest/" + runcase);

	}

	/**
	 * 截取当前失败用例的图，并存放在对应的测试用例文件夹下
	 * 
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param runcase
	 *            测试用例
	 * @param currentTime
	 *            当前时间
	 * @throws IOException
	 */
	public static void takeScreen(UiDevice devices, String runcase,
			String currentTime) throws IOException {
		createFolder(runcase);
		devices.takeScreenshot(new File("/sdcard/GolukTest/" + runcase + "/"
				+ currentTime + ".png"), 0, 50);
	}

	// Take Bug Report
	public static void takeBugReport(String folderName, String currentTime)
			throws IOException {
		// System.out.println("Begin to Bugreport");
		// Runtime.getRuntime().exec("bugreport > /sdcard/zzzzz.txt");
		// waitTime(10);
		// //Runtime.getRuntime().exec("adb kill-server ");

	}

	/**
	 * 下载视频
	 * 
	 * @param runcase
	 *            测试用例
	 * @param device
	 *            UiDevice.getInstance()实例
	 * @param editbtnid
	 *            编辑按钮id
	 * @throws Exception
	 */
	public static void downloadVideo(String runcase, UiDevice device,
			String editbtnid) throws Exception {
		// 点击 编辑 按钮
		clickViewById(runcase, device, editbtnid);
		for (int n = 0; n < al.length; n++) {
			infoLog(runcase, TestInfo + "第 " + (n + 1) + " 个视频被选择");
			int ax = al[n][0];
			int ay = al[n][1];
			device.click(ax, ay);
		}
		UiObject downloadbtn = findViewById(runcase, device,
				"cn.com.mobnote.golukmobile:id/mDownloadBtn");
		if (downloadbtn.exists()) {
			infoLog(runcase, TestInfo + "准备下载的视频已经被选中");
			downloadbtn.clickAndWaitForNewWindow();
			infoLog(runcase, TestClick
					+ "cn.com.mobnote.golukmobile:id/mDownloadBtn");
		} else {
			throw new Exception("编辑按钮选择失败");
		}
	}

	/**
	 * 播放本地/远程视频
	 * @param runcase
	 *            测试用例
	 * @param device
	 *            UiDevice.getInstance()实例
	 * @param resourceid
	 *            播放器id
	 * @param loadtime
	 *            等待时间
	 */
	public static void playVideo(String runcase, UiDevice device,
			String resourceid, int loadtime) {
		for (int n = 0; n < al.length; n++) {
			infoLog(runcase, TestInfo + "第 " + (n + 1) + " 个视频位置被点击");
			int ax = al[n][0];
			int ay = al[n][1];
			// 点击视频坐标
			device.click(ax, ay);
			waitTime(2);
			UiObject loadingNote;
			try {
				UiObject playView = findViewById2(device, resourceid);
				waitTime(3);
				// 判断播放器是否打开
				if (playView.exists()) {
					infoLog(runcase, TestInfo + "视频已经打开");
					loadingNote = findViewById2(device,
							"cn.com.mobnote.golukmobile:id/mLoading");
					// 如果出现等待提示
					if (loadingNote.exists()) {
						infoLog(runcase, TestInfo + "正在加载视频...");
						int nloadtime = 1;
						while (loadingNote.exists() && nloadtime < loadtime) {
							waitTime(1);
							infoLog(runcase, TestInfo + "视频加载 " + nloadtime
									+ "s");
							nloadtime++;
						}
						// 如果视频异常，无法播放，提示出错，退出
						if(findViewByText2(device, "视频出错，请重试！").exists()){
							findViewByText2(device,"确定");
							infoLog(runcase,"此视频出错，无法播放");
						}
						// 如果等待超时，提示播放失败
						if (nloadtime == loadtime) {
							infoLog(runcase, TestInfo + "加载超时，播放失败");
							device.pressBack();
							waitTime(2);
							throw new Exception("视频加载超时，播放失败");
						} else {
							infoLog(runcase, TestInfo + "开始播放视频，播放10秒钟");
							waitTime(10);
							device.click(pause[0], pause[1]);
							waitTime(2);
							device.click(pause[0], pause[1]);
							waitTime(2);
							infoLog(runcase, TestInfo + "视频播放结束");
							waitTime(1);
						}
						device.pressBack();
						waitTime(2);
					} else {
						infoLog(runcase, TestInfo + "正在播放视频,播放10秒");
						waitTime(10);
						device.click(pause[0], pause[1]);
						waitTime(1);
						infoLog(runcase, TestInfo + "点击暂定按钮");
						device.click(pause[0], pause[1]);
						waitTime(1);
						device.pressBack();
						infoLog(runcase, TestInfo + "视频播放结束");
						checkIPCConnect(runcase, device);
						waitTime(5);
					}
				} else {
					// 如果播放器没有打开，判断如果是出现OK,表示出现CRASH,抛异常，否则判断为此处没有视频
					UiObject AppCrashBtn = Common.findViewByText2(device, "OK");
					if (AppCrashBtn.exists()) {
						throw new Exception();
					} else {
						infoLog(runcase, TestInfo + "点击此处没有视频");
						System.out.println("点击此处没有视频");
					}
				}
			} catch (Exception e) {
				infoLog(runcase, e.getMessage());
			}
		}
	}

	/**
	 * 播放视频广场/热门视频
	 * 
	 * @param runcase
	 *            测试用例
	 * @param device
	 *            UiDevice.getInstance()实例
	 */
	public static void playSquareVide(String runcase, UiDevice device) {
		// 选择一个视频
		try {
			// 选择广场或者热门任意视频播放
			clickViewById(runcase, device,
					"cn.com.mobnote.golukmobile:id/mPlayerLayout");
			waitTime(3);
			int waittime = 1;
			while (waittime < 60) {
				// 判断加载等待提示是否存在，如果存在等待，60秒超时后提示网络问题
				UiObject waitNote = Common.findViewById2(device,
						"cn.com.mobnote.golukmobile:id/mLoading");
				if (waitNote.exists()) {
					infoLog(runcase, "等待热门/广场视频加载中... " + waittime + " 秒");
					waittime = waittime + 1;
					waitTime(1);
				} else {
					// 如果加载等待提示不存在，认为开始播放,播放20秒后，返回
					infoLog(runcase, "热门/广场视频加载完成，开始播放,播放20秒后返回");
					waitTime(20);
					device.pressBack();
					break;
				}
				// 如果视频异常，无法播放，提示出错，退出
				if(findViewByText2(device, "视频出错，请重试！").exists()){
					findViewByText2(device,"确定");
					infoLog(runcase,"此视频出错，无法播放");
					break;
				}
			}
			if (waittime == 60) {
				infoLog(runcase, "热门/广场视频加载超过60秒，请检查网络");
				device.pressBack();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否已经连接IPC
	 * 
	 * @param runcase
	 *            测试用例
	 * @param device
	 *            UiDevice.getInstance()实例
	 * @param text
	 *            查找控件的text
	 * @throws Exception
	 */
	public static void connectWifi(String runcase, UiDevice device, String text)
			throws Exception {
		waitTime(2);
		int waitIPCConnect = 1;
		while (waitIPCConnect < 121) {
			UiObject wifi = Common.findViewByText2(device, text);
			if (wifi.exists()) {
				infoLog(runcase, TestInfo + "IPC已经连接");
				break;
			} else {
				waitTime(1);
				infoLog(runcase, TestInfo + "IPC正在连接 " + waitIPCConnect + "s");
				waitIPCConnect++;
			}
		}
		if (waitIPCConnect == 121) {
			throw new Exception("IPC连接超过" + waitIPCConnect + "秒，失败");
		}
	}

	/**
	 * 
	 * @param runcase
	 *            测试用例
	 * @param devices
	 *            UiDevice.getInstance()实例
	 * @param hanldtimeout
	 *            滤镜处理耗时
	 * @throws Exception
	 */
	public static void selectVideoFilter(String runcase, UiDevice devices,
			int hanldtimeout) throws Exception {
		for (int n = 0; n < al.length - 3; n++) {
			infoLog(runcase, TestInfo + "第 " + (n + 1) + " 个视频被点击");
			int ax = al[n][0];
			int ay = al[n][1];
			devices.click(ax, ay);
			waitTime(3);
			UiObject filterbtn = Common.findViewById2(devices,
					"cn.com.mobnote.golukmobile:id/filter_btn");
			if (filterbtn.exists()) {
				infoLog(runcase, TestInfo + "进入滤镜处理界面");
				waitTime(1);
				handleVideoByFilter(runcase, devices, hanldtimeout);
				waitTime(3);
			} else {
				infoLog(runcase, TestInfo + "此处没有视频");
			}
		}
	}

	/**
	 * 随机选择滤镜处理视频
	 * 
	 * @param runcase 测试用例
	 * @param devices UiDevice.getInstance()实例 
	 * @param hanldtimeout 滤镜处理超时
	 * @throws Exception
	 */
	public static void handleVideoByFilter(String runcase, UiDevice devices,
			int hanldtimeout) throws Exception {
		for (int n = 0; n < filterName.length; n++) {
			UiObject filter = findViewByText2(devices, filterName[n]);
			if (filter.exists()) {
				filter.clickAndWaitForNewWindow();
				infoLog(runcase, TestInfo + "滤镜 " + filterName[n] + " 被选择");
			} else {
				throw new Exception(filterName[n] + " 滤镜不存在");
			}
		}
		Random rand = new Random(47);
		int randInt = rand.nextInt(filterName.length);
		UiObject filter = findViewByText2(devices, filterName[randInt]);
		filter.clickAndWaitForNewWindow();
		UiObject nextStepBtn = findViewById2(devices,
				"cn.com.mobnote.golukmobile:id/next_btn");
		nextStepBtn.clickAndWaitForNewWindow();
		waitTime(2);
		UiObject share_image = Common.findViewById2(devices,
				"cn.com.mobnote.golukmobile:id/share_layout");
		int i = 1;
		while (i < hanldtimeout) {
			if (share_image.exists()) {
				infoLog(runcase, TestInfo + "视频滤镜效果处理完成");
				break;
			}
			i++;
			waitTime(1);
			infoLog(runcase, TestInfo + "滤镜效果正在处理，第 " + i + "秒");
		}
		if (i == hanldtimeout) {

			throw new Exception("滤镜处理超时 " + hanldtimeout + "秒");
		}
		waitTime(5);
		devices.pressBack();
		waitTime(3);
		UiObject cancelbtn = Common.findViewByText2(devices, "确定");
		if (cancelbtn.exists()) {
			cancelbtn.clickAndWaitForNewWindow();
			waitTime(2);
		}
		devices.pressBack();
		if (nextStepBtn.exists()) {
			devices.pressBack();
		}
	}

	/**
	 * 判断IPC是否断开
	 * @param runcase 测试用例
	 * @param devices UiDevice.getInstance()实例
	 * @throws Exception
	 */
	public static void checkIPCConnect(String runcase, UiDevice devices)
			throws Exception {
		waitTime(4);
		UiObject waitConnectIPC = Common.findViewByText2(devices,
				"摄像头断开，正在为您重连…");
		int waitConnect = 1;
		while (waitConnect < 40) {
			if (!waitConnectIPC.exists()) {
				break;
			}
			waitConnect++;
		}
		UiObject disconnectIPC = Common.findViewByText2(devices, "您好像没有连接摄像头哦。");
		if (disconnectIPC.exists()) {
			UiObject oo2 = Common.findViewById2(devices,
					"cn.com.mobnote.golukmobile:id/leftButton");
			oo2.clickAndWaitForNewWindow();
			infoLog(runcase, TestInfo + "IPC 断开连接");
			throw new Exception("IPC 断开连接");
		}
	}
	/**
	 * 等待时间设置
	 * @param n 等待时间，单位为秒
	 */
	public static void waitTime(int n) {
		long time = n * 1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下拉
	 * @param runcase 测试用例
	 * @param device UiDevice.getInstance()实例
	 * @param className 列表的classType
	 * @param scrollTime 下拉次数
	 */
	public static void scrollDown(String runcase, UiDevice device,
			String className, int scrollTime) {
		UiScrollable object = new UiScrollable(
				new UiSelector().className(className));
		try {
			for (int i = 0; i < scrollTime; i++) {
				object.scrollBackward(8);
			}

		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
		infoLog(runcase, TestInfo + "向下拉动");
	}
	/**
	 * 上翻
	 * @param runcase 测试用例
	 * @param device UiDevice.getInstance()实例
	 * @param className 列表的classType
	 * @param scrollTime 上翻次数
	 */
	public static void scrollUp(String runcase, UiDevice device,
			String className, int scrollTime) {
		UiScrollable object = new UiScrollable(
				new UiSelector().className(className));
		try {
			for (int i = 0; i < scrollTime; i++) {
				object.scrollForward();
			}

		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
		infoLog(runcase, TestInfo + "向上翻动");
	}

}
