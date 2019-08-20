package cn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyProfit {

	public static void main(String[] args) {
		double originalPrice = 375.406;
		double nowPrice = 397.939;
		double times = 20;
	
		try {
			while(true){
				BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
				String s = read.readLine();
				originalPrice = Double.parseDouble(s);
				System.out.println("rate: "+calculate(nowPrice,originalPrice,times));
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*try {
			System.out.print("rate: "+calculate(nowPrice,originalPrice,times));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 收益率
	 * @param originalPrice 原价
	 * @param nowPrice 现价
	 * @param times 倍数
	 * @return
	 * @throws Exception
	 */
	public static double calculate(double originalPrice,double nowPrice,double times) throws Exception{
		
		double rate = (nowPrice - originalPrice)/originalPrice;
		
		return rate * 2 * 1000;
	}
}
