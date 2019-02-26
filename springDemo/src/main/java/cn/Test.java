package cn;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;


public class Test {

	public static void main(String[] args) {
		 
		for(int i = 0; i < 1000; i++){
			getPrice2(100,5);
		}
		//System.out.println(div(100, 3, 3));
	}
	//#####################################################
	
	public static void getPrice2(double price,int num){
		RedPackage redPackage = new RedPackage(num,price);
		redPackage.setTotal(price);
		double sum = 0;
		getRandomPrice2(redPackage);
		System.out.println("      sum="+redPackage.surplus);
	}
	
	public static double getRandomPrice2(RedPackage redPackage){
		double price = redPackage.money  ;
		int num = redPackage.size ;
		double money = price;
		
		if(num >=1){
			money = getRandomPrice3(redPackage);
			num = num -1;
			price = price-money;
			redPackage.setMoney(price);
			redPackage.setSize(num);
			redPackage.surplus += money;
			
			if(num>=1){
				getRandomPrice2(redPackage);
			}
		}
		
		System.out.print(money+",");
		return money;
	}
	
    public static double getRandomPrice3(RedPackage redPackage){
    	
    	double price = redPackage.money  ;
		int num = redPackage.size ;
		
		if(num == 1) {
			return sub(redPackage.total,redPackage.surplus);// Math.(price*100)/100;
			//return Math.round(price*100)/100;
		}
		double mix = ((price-0.01)/num+1);
		double max = ((price-0.01)/num-1);
		Random random = new Random();
		double money = random.nextDouble() * (max-mix);
		
		money = mix+money;
		money = Math.floor(money * 100)/100;
		
		return money;
	} 
	
	
	//####################################################
	
	
	/**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    } 
    
    /**
     * 提供精确的加法运算
     * @param redPackage
     * @param v2
     * @return
     */
    public static double add(RedPackage redPackage,double v2){
		 double v1 = redPackage.surplus;
       BigDecimal b1 = new BigDecimal(Double.toString(v1));
       BigDecimal b2 = new BigDecimal(Double.toString(v2));
       double result = b1.add(b2).doubleValue();
       redPackage.surplus = result;
       return result;
   }
	
	public static String getRandom(int min, int max){
	    Random random = new Random();
	    int s = random.nextInt(max) % (max - min + 1) + min;
	    return String.valueOf(s);

	}
	
	public static void getPrice(double price,int num){
		RedPackage redPackage = new RedPackage(num,price);
		double sum = 0;
		getRandomPrice(redPackage);
		System.out.println("      sum="+redPackage.surplus);
	}
	
	public static double getRandomPrice(RedPackage redPackage){
		double price = redPackage.money  ;
		int num = redPackage.size ;
		double money = price;
		if(num >= 1){
			money = getRandomPrice(price,num);
			num = num -1;
			price = price-money;
			redPackage.setMoney(price);
			redPackage.setSize(num);
			redPackage.surplus += money;
			
			if(num>=1){
				getRandomPrice(redPackage);
			}
			
		}
		
		System.out.print(money+",");
		return money;
	}
	
	public static double getRandomPrice(double price,int num){
		
		if(num == 1) {
			return Double.valueOf(new DecimalFormat("00.##E0").format(price));// Math.(price*100)/100;
			//return Math.round(price*100)/100;
		}
		double mix = 0.01;
		double max = (price/num)*2;
		Random random = new Random();
		double money = random.nextDouble() * max;
		
		money = money <= mix ? 0.01:money;
		money = Math.floor(money * 100)/100;
		//money = Double.valueOf(new DecimalFormat("00.##E0").format(money));
		return money;
	}
	
	
}
class RedPackage{
	public int size;
	public double money;
	public double surplus;
	public double total;
	
	public RedPackage(int size,double money){
		this.size = size;
		this.money = money;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
}
