package cn.lvyjx.test.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 下划线转驼峰
	 * @param line
	 * @param smallCamel
	 * @return
	 */
	public static String underline2Camel(String line,boolean smallCamel){
		if(line  == null || "".equals(line)){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()){
			String word = matcher.group();
			System.out.println("word:"+word);
			sb.append(smallCamel && matcher.start() == 0? Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf("_");
			if(index > 0){
				sb.append(word.substring(1,index).toLowerCase());
			}else{
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();		
	}
	
	/**
	 * 驼峰转下划线
	 * @param line
	 * @return
	 */
	public static String camel2Underline(String line){
		if(line == null || "".equals(line)){
			return "";
		}
		boolean flag = true;
		int size = line.length();
		for(int i = 0 ; i < size && flag; i++){
			if(Character.isUpperCase(line.charAt(i)) || Character.isLowerCase(line.charAt(i))){
				line = String.valueOf(line.charAt(i)).toUpperCase().concat(line.substring(i+1));
				flag = false;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()){
			String word = matcher.group();
			System.out.println("work："+word);
			sb.append(word.toLowerCase());
			System.out.println("matcher.end():"+matcher.end());
			sb.append(matcher.end() == line.length()?"":"_");
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		System.out
				.println(underline2Camel("_abcdefg_hijklnm_opq_rst_uvw_", true));
		
		System.out
		.println(camel2Underline("_abcdefgHijklnmOpqRstUvw_"));
		System.out
		.println(underline2Camel("abcdefgHijklnmOpqRstUvw",true));
	}
}
