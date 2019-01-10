package cn.lvyjx.test.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class FileManage {

	public static void splitFile(String filePath,int fileCount) throws IOException{
		FileInputStream fis = new FileInputStream(filePath);
		FileChannel inputChannel = fis.getChannel();
		final long fileSize = inputChannel.size();
		long average = fileSize / fileCount; //平均值 
		long bufferSize = 100;
		ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize+"")); //分配了一段内存空间，作为缓存，allocate方法对缓存自动清零
		long startPosition = 0; //子文件开始位置
		long endPosition = average < bufferSize ? 0 : average - bufferSize;//结束位置
		for(int i = 0 ; i < fileCount; i++){
			if(i+1 != fileCount){
				int read = inputChannel.read(byteBuffer,endPosition); //读取数据,从结束的位置读取（主要判断是不是一个完整的语句，轮询这200个字符）
				readW:
					while(read != -1){
						int tn = 0;
						byteBuffer.flip();//切换读模式
						byte[] array = byteBuffer.array();
						for(int j = 0; j < array.length ; j++){
							byte b = array[j];
							tn++;
							if(b == 10 || b == 13){//判断 \n\r
								endPosition += j;
								break readW;
							}
						}
						endPosition += bufferSize;
						byteBuffer.clear(); //重置缓存块指针
						read = inputChannel.read(byteBuffer,endPosition);
					}
			}else{
				endPosition = fileSize; //最后一个文件
			}
			String startName = filePath.substring(0, filePath.lastIndexOf("."))+(i+1);
			String endName = filePath.substring(filePath.lastIndexOf("."));
			FileOutputStream fos = new FileOutputStream(startName+endName);
			FileChannel outputChannel = fos.getChannel();
			inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);
			outputChannel.close();
			fos.close();
			startPosition = endPosition+1;
			endPosition += average;
			
		}
		inputChannel.close();
		fis.close();
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
	    scanner.nextLine();
		long startTime = System.currentTimeMillis();
		try {
			splitFile("E:\\test\\log.txt", 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("耗时："+(endTime-startTime));
		scanner.nextLine();
	}
}
