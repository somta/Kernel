package net.somta.common.utils.io;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.somta.common.utils.encrypt.Base64;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;

public class QRCodeUtil {
	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "PNG";
	public static final String PIC_PNG = "data:image/png;base64,";
	private static final int QRCODE_SIZE = 200;
	private static final int WIDTH = 60;
	private static final int HEIGHT = 60;

	public QRCodeUtil() {
	}

	private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
		return createImage(content, imgPath, needCompress, 200);
	}

	public static BufferedImage createImage(String content, String imgPath, boolean needCompress, int qrcodeSize) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, Integer.valueOf(1));
		BitMatrix bitMatrix = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, 1);

		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				image.setRGB(x, y, bitMatrix.get(x, y)?-16777216:-1);
			}
		}

		if(imgPath != null && !"".equals(imgPath)) {
			insertImage(image, imgPath, needCompress, qrcodeSize);
			return image;
		} else {
			return image;
		}
	}

	/**
	 * 将base64图片转换成BufferedImage
	 * @param content 二维码内容
	 * @param needCompress 是否需要压缩
	 * @param qrcodeSize 二维码大小
	 * @return BufferedImage
	 * @throws Exception 异常
	 */
	public static BufferedImage createImages(String content, boolean needCompress, int qrcodeSize) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, Integer.valueOf(1));
		BitMatrix bitMatrix = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, 1);

		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				image.setRGB(x, y, bitMatrix.get(x, y)?-16777216:-1);
			}
		}

		return image;
	}

	public static InputStream converStream(BufferedImage image, String Type) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, Type, os);
		return new ByteArrayInputStream(os.toByteArray());
	}

	public static void insertImage(BufferedImage source, String imgPath, boolean needCompress, int qrcodeSize) throws Exception {
		File file = new File(imgPath);
		if(file.exists()) {
			Image src = ImageIO.read(new File(imgPath));
			int width = ((Image)src).getWidth((ImageObserver)null);
			int height = ((Image)src).getHeight((ImageObserver)null);
			if(needCompress) {
				if(width > qrcodeSize / 200 * 60) {
					width = qrcodeSize / 200 * 60;
				}

				if(height > qrcodeSize / 200 * 60) {
					height = qrcodeSize / 200 * 60;
				}

				Image image = ((Image)src).getScaledInstance(width, height, 4);
				BufferedImage tag = new BufferedImage(width, height, 1);
				Graphics g = tag.getGraphics();
				g.drawImage(image, 0, 0, (ImageObserver)null);
				g.dispose();
				src = image;
			}

			Graphics2D graph = source.createGraphics();
			int x = (qrcodeSize - width) / 2;
			int y = (qrcodeSize - height) / 2;
			graph.drawImage((Image)src, x, y, width, height, (ImageObserver)null);
			Shape shape = new Float((float)x, (float)y, (float)width, (float)width, 6.0F, 6.0F);
			graph.setStroke(new BasicStroke(3.0F));
			graph.draw(shape);
			graph.dispose();
		}
	}

	public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, imgPath, needCompress);
		FileUtil.makeDir(destPath, false);
		ImageIO.write(image, "PNG", new File(destPath));
	}

	public static void encode(String content, String imgPath, String destPath, boolean needCompress, int qrcodeSize) throws Exception {
		BufferedImage image = createImage(content, imgPath, needCompress, qrcodeSize);
		FileUtil.makeDir(destPath, false);
		ImageIO.write(image, "PNG", new File(destPath));
	}

	public static void encode(String content, String imgPath, String destPath) throws Exception {
		encode(content, imgPath, destPath, false);
	}

	public static void encode(String content, String imgPath, String destPath, int qrcodeSize) throws Exception {
		encode(content, imgPath, destPath, false);
	}

	public static void encode(String content, String destPath, boolean needCompress) throws Exception {
		encode(content, (String)null, (String)destPath, needCompress);
	}

	public static void encode(String content, String destPath) throws Exception {
		encode(content, (String)null, (String)destPath, false);
	}

	public static void encode(String content, String imgPath, OutputStream output, boolean needCompress) throws Exception {
		BufferedImage image = createImage(content, imgPath, needCompress);
		ImageIO.write(image, "PNG", output);
	}

	public static void encode(String content, String imgPath, OutputStream output, boolean needCompress, int qrcodeSize) throws Exception {
		BufferedImage image = createImage(content, imgPath, needCompress, qrcodeSize);
		ImageIO.write(image, "PNG", output);
	}

	/**
	 * 根据内容直接生成图片的BASE64的字符串
	 * @param content 字符串内容
	 * @param needCompress 是否需要压缩
	 * @param qrcodeSize 二维码大小
	 * @return String
	 */
	public static String encode(String content, boolean needCompress, int qrcodeSize) {
		BufferedImage image = null;
		try {
			image = createImages(content, needCompress, qrcodeSize);
		} catch (Exception var11) {
			var11.printStackTrace();
		}

		byte[] btSt = ImageUtil.getBytes(image);
		byte[] byteStream = Base64.encodeByte(btSt).getBytes();
		StringBuilder sb = new StringBuilder("data:image/png;base64,");
		byte[] var10 = byteStream;
		int var9 = byteStream.length;

		for(int var8 = 0; var8 < var9; ++var8) {
			byte b = var10[var8];
			sb.append((char)b);
		}
		return sb.toString();
	}

	public static void encode(String content, OutputStream output, int qrcodeSize) throws Exception {
		encode(content, (String)null, (OutputStream)output, false, qrcodeSize);
	}

	public static void encode(String content, OutputStream output) throws Exception {
		encode(content, (String)null, (OutputStream)output, false);
	}

	public static String decode(File file) throws Exception {
		BufferedImage image = ImageIO.read(file);
		if(image == null) {
			return null;
		} else {
			BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Hashtable<DecodeHintType, Object> hints = new Hashtable();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			Result result = (new MultiFormatReader()).decode(bitmap, hints);
			String resultStr = result.getText();
			return resultStr;
		}
	}

	public static String decode(BufferedImage image) throws Exception {
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Hashtable<DecodeHintType, Object> hints = new Hashtable();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		Result result = (new MultiFormatReader()).decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}

	public static String decode(String path) throws Exception {
		return decode(new File(path));
	}


	/*public static void main(String[] args) {
		String code = encode("1234",false,200);
		System.out.println(code);
	}*/

}
