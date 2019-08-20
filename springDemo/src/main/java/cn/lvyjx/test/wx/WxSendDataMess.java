package cn.lvyjx.test.wx;

public class WxSendDataMess {

	
	public static void sendWxMessToUser(WxMessVo mess) throws Exception{
		FinalSendDataMsg sendMsg = new FinalSendDataMsg();
		String rStr = sendMsg.sendMessDataUser(mess,sendMsg);
		System.out.println("rstr >>>>>>>>>>>>>"+rStr);
	}
	
}
