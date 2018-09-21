package cn.lvyjx.test;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class CodeUtil {

	private static final int BLACK = 0xff000000;
	 
	public static BufferedImage createQRCode(String str, int widthAndHeight)
			throws WriterException {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				image.setRGB(x,y,matrix.get(x, y) == true? 0xFF000000:0xFFFFFFFF);
			}
		}
		
		return image;
	}

	
	public static BufferedImage creatBarcode(String contents,
			int desiredWidth, int desiredHeight, boolean displayCode) {
		BufferedImage image = null;
		BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
 
		image = encodeAsBitmap(contents, barcodeFormat,
					desiredWidth, desiredHeight);
		return image;
	}
	
	
	protected static BufferedImage encodeAsBitmap(String contents,
			BarcodeFormat format, int desiredWidth, int desiredHeight) {
 
		MultiFormatWriter writer = new MultiFormatWriter();
		 Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
         hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
		BitMatrix matrix = null;
		BufferedImage image = null;
		try {
			matrix = writer.encode(contents, format, desiredWidth,
					desiredHeight, hints);
			image = MatrixToImageWriter.toBufferedImage(matrix);
		} catch (WriterException e) {
			
			e.printStackTrace();
		}
 
	/*	int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		// All are 0, or black, by default
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
			}
		}
 
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				image.setRGB(x,y,matrix.get(x, y) == true?BLACK : WHITE);
			}
		}*/
		return image;
	}
	
	/**
	protected static Bitmap creatCodeBitmap(String contents, int width,
			int height, Context context) {
		TextView tv = new TextView(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(layoutParams);
		tv.setText(contents);
		tv.setHeight(height);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setWidth(width);
		tv.setDrawingCacheEnabled(true);
		tv.setTextColor(Color.BLACK);
		tv.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
 
		tv.buildDrawingCache();
		Bitmap bitmapCode = tv.getDrawingCache();
		return bitmapCode;
	}
	
	
	protected static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
			PointF fromPoint) {
		if (first == null || second == null || fromPoint == null) {
			return null;
		}
		int marginW = 20;
		Bitmap newBitmap = Bitmap.createBitmap(
				first.getWidth() + second.getWidth() + marginW,
				first.getHeight() + second.getHeight(), Config.ARGB_4444);
		Canvas cv = new Canvas(newBitmap);
		cv.drawBitmap(first, marginW, 0, null);
		cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();
 
		return newBitmap;
	}
**/
}
