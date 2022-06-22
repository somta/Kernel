package net.somta.common.utils.io;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Position;
import net.somta.core.utils.CommonUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片工具处理类
 * Blog: https://www.somta.net/
 * Date: 2020/1/20
 * @author 明天的地平线
 * @version  1.0.0
 */
public class ImageUtil {

    /**
     * 定义允许上传的图片格式
     */
	private static String[] imgTypes = new String[] { "gif", "jpeg", "jpg", "png", "bmp" };

    /**
     * 检查格式是否合法
     * @param imageType 文件类型
     * @return boolean
     */
    public static boolean checkType(String imageType) {
        boolean flag = false;
        if (Arrays.asList(imgTypes).contains(imageType.toLowerCase())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 指定大小 压缩图片
     * 默认输出50%质量图片
     * 图片宽高与缩放比例不能同时存在
     * @param in      输出流
     * @param width   图片长度
     * @param height  图片宽度
     * @param quality 图片压缩质量 范围：0.0~1.0，1为最高质量
     * @param scale  按照比例进行缩放 0.0~1.0
     * @return InputStream 输入流
     * @throws IOException 异常
     */
    public static InputStream compress(InputStream in, Integer width, Integer height, Float quality,Double scale) throws IOException {
        if (in == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Builder<? extends InputStream> builder=Thumbnails.of(in);
        if(width!=null && height!=null){
            builder.size(width.intValue(),height.intValue());
        }

        if(scale!=null){
            builder.scale(scale.doubleValue());
        }
        if(quality!=null){
            builder.outputQuality(quality.floatValue());
        }
        builder.toOutputStream(os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        in.close();
        os.close();
        return is;
    }

    /**
     * 根据图片对象获取对应InputStream
     * @param image 图片对象
     * @param readImageFormat 图片格式
     * @return 输入流
     * @throws IOException 异常
     */
    public static InputStream getInputStream(BufferedImage image, String readImageFormat) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, readImageFormat, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        os.close();
        return is;
    }

    /**
     * 将文件流转成文件
     * @param ins 输入流
     * @param file 文件
     * @throws IOException 异常
     */
    public static void inputstreamToFile(InputStream ins,File file) throws IOException{
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }


    /**
     * 指定大小进行缩放 若图片横比width小,高比height小,不变 若图片横比width小,高比height大,高缩小到height,图片比例不变
     * 若图片横比width大,高比height小,横缩小到width,图片比例不变
     * 若图片横比width大,高比height大,图片按比例缩小,横为width或高为height
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param quality 压缩比例
     * @throws IOException 异常
     */
    public static void imgThumb(String source, String output, int width, int height, float quality) throws IOException {
        Thumbnails.of(source).size(width, height).outputQuality(quality).outputFormat("jpg").toFile(output);
    }

    /**
     * 指定大小进行缩放
     * @param source 输入文件地址
     * @param output 输出文件地址
     * @param width  宽
     * @param height 高
     * @param quality 图片品质
     * @throws IOException 异常
     */
    public static void imgThumb(File source, String output, int width, int height, float quality) throws IOException {
        Thumbnails.of(source).size(width, height).outputQuality(quality).outputFormat("jpg").toFile(output);
    }

    /**
     * 按照比例进行缩放
     * @param source 输入文件地址
     * @param output 输出文件地址
     * @param scale 缩放比例
     * @throws IOException 异常
     */
    public static void imgScale(String source, String output, double scale) throws IOException {
        Thumbnails.of(source).scale(scale).outputFormat("jpg").toFile(output);
    }

    public static void imgScale(File source, String output, double scale) throws IOException {
        Thumbnails.of(source).scale(scale).outputFormat("jpg").toFile(output);
    }

    /**
     * 不按照比例,指定大小进行缩放
     * @param source          输入源
     * @param output          输出源
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     * @throws IOException 异常
     */
    public static void imgNoScale(String source, String output, int width, int height, boolean keepAspectRatio)
            throws IOException {
        Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).outputFormat("jpg").toFile(output);
    }

    public static void imgNoScale(File source, String output, int width, int height, boolean keepAspectRatio)
            throws IOException {
        Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).outputFormat("jpg").toFile(output);
    }

    /**
     * 传入输入流，返回带水印的输入流
     * @param in 图片流
     * @param width 图片宽度
     * @param height 图片高度
     * @param position 水印显示位置：Positions.BOTTOM_RIGHT
     * @param watermark 水印图片地址(字符串)
     * @param transparency 透明度 0.5f
     * @param quality 图片质量 0.8f
     * @return 输入流
     * @throws IOException 异常
     */
    public static InputStream imgWatermark(InputStream in, Integer width, Integer height,Position position,
            String watermark, float transparency, float quality) throws IOException{
    	if (in == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Builder<? extends InputStream> builder=Thumbnails.of(in);
        if(width!=null && height!=null){
            builder.size(width.intValue(),height.intValue());
        }
        if(!CommonUtil.isNullOrEmpty(position) && !CommonUtil.isNullOrEmpty(watermark)
        		&& !CommonUtil.isNullOrEmpty(transparency)){
        	builder.watermark(position, stringToBufferedImage(watermark), transparency);
        }
        if(!CommonUtil.isNullOrEmpty(quality)){
        	builder.outputQuality(quality);
        }
        builder.scale(1);
        builder.toOutputStream(os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        in.close();
        os.close();
        return is;
    }

    /**
     * 将一个图片水印到另一个图片上
     *
     * @param source       输入源
     * @param output       输入源
     * @param width        宽
     * @param height       高
     * @param position     水印位置 Positions.BOTTOM_RIGHT o.5f
     * @param watermark    水印图片地址 （图片地址）
     * @param transparency 透明度 0.5f
     * @param quality      图片质量 0.8f
     * @throws IOException 异常
     */
    public static void imgWatermark(String source, String output, int width, int height, Position position,
                                    String watermark, float transparency, float quality) throws IOException {
        Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency)
                .outputQuality(0.8f).outputFormat("jpg").toFile(output);
    }

    public static void imgWatermark(File source, String output, int width, int height, Position position,
                                    String watermark, float transparency, float quality) throws IOException {
        Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency)
                .outputQuality(0.8f).outputFormat("jpg").toFile(output);
    }

    /**
     * 裁剪图片
     * @param source          输入源
     * @param output          输出源
     * @param position        裁剪位置
     * @param x               裁剪区域x
     * @param y               裁剪区域y
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     * @throws IOException 异常
     */
    public static void imgSourceRegion(String source, String output, Position position, int x, int y, int width,
                                       int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio)
                .outputFormat("jpg").toFile(output);
    }

    public static void imgSourceRegion(File source, String output, Position position, int x, int y, int width,
                                       int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio)
                .outputFormat("jpg").toFile(output);
    }

    /**
     * 按坐标裁剪
     * @param source          输入源
     * @param output          输出源
     * @param x               起始x坐标
     * @param y               起始y坐标
     * @param x1              结束x坐标
     * @param y1              结束y坐标
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     * @throws IOException 异常
     */
    public static void imgSourceRegion(String source, String output, int x, int y, int x1, int y1, int width,
                                       int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio)
                .toFile(output);
    }

    public static void imgSourceRegion(File source, String output, int x, int y, int x1, int y1, int width, int height,
                                       boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio)
                .outputFormat("jpg").toFile(output);
    }

    /**
     * 将一个文件地址的个图片转换输出到另一个地方
     * @param source 输入源 源文件的输入地址
     * @param output 输出源 文件输出路径
     * @param width  宽
     * @param height 高
     * @param format 图片类型,gif、png、jpg
     * @throws IOException 异常
     */
    public static void imgFormat(String source, String output, int width, int height, String format)throws IOException {
        Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
    }

    /**
     *
     * @param source 输入源
     * @param output 输出源
     * @param width 宽
     * @param height 高
     * @param format 图片格式
     * @throws IOException 异常
     */
    public static void imgFormat(File source, String output, int width, int height, String format) throws IOException {
        Thumbnails.of(source).size(width, height).outputFormat(format).outputFormat("jpg").toFile(output);
    }

    /**
     * 输出到OutputStream
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @return toOutputStream(流对象)
     * @throws IOException 异常
     */
    public static OutputStream imgOutputStream(String source, String output, int width, int height) throws IOException {
        OutputStream os = new FileOutputStream(output);
        Thumbnails.of(source).size(width, height).outputFormat("jpg").toOutputStream(os);
        return os;
    }

    public static OutputStream imgOutputStream(File source, String output, int width, int height) throws IOException {
        OutputStream os = new FileOutputStream(output);
        Thumbnails.of(source).size(width, height).outputFormat("jpg").toOutputStream(os);
        return os;
    }

    /**
     * 输出到BufferedImage
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型,gif、png、jpg
     * @return BufferedImage
     * @throws IOException 异常
     */
    public static BufferedImage imgBufferedImage(String source, String output, int width, int height, String format)
            throws IOException {
        BufferedImage buf = Thumbnails.of(source).size(width, height).outputFormat("jpg").asBufferedImage();
        ImageIO.write(buf, format, new File(output));
        return buf;
    }

    public static BufferedImage imgBufferedImage(File source, String output, int width, int height, String format)
            throws IOException {
        BufferedImage buf = Thumbnails.of(source).size(width, height).outputFormat("jpg").asBufferedImage();
        ImageIO.write(buf, format, new File(output));
        return buf;
    }

    /**
     * 将字符串转成BufferedImage
     * @param str 图片Base64
     * @return BufferedImage
     * @throws IOException 异常
     */
    public static BufferedImage stringToBufferedImage(String str) throws IOException{
    	int width = 200;
    	int height = 40;
    	//创建BufferedImage对象
    	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    	//得到图片
        Graphics2D g = image.createGraphics();

        // 增加下面代码使得背景透明
        image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        g = image.createGraphics();
        // 背景透明代码结束
        g.setColor(new Color(192, 192, 192));
        g.setStroke(new BasicStroke(1f));
        //向图片上写写数据
        g.setFont(new Font("宋体",Font.ITALIC,22));
        //把想要写的字符串画在图片上
        g.drawString(str, 6, 15);//画图片
        return image;
    }

    /**
     * 图片转化成base64字符串
     * @param imgPath 图片路径
     * @return String
     */
    public static String imageToString(String imgPath) {
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * 将BASE64的字符串转成图片
     * @param imgStr 图片的BASE64字符串
     * @param imgFilePath 生成的文件地址
     * @return boolean
     * @throws IOException 异常
     */
    public static boolean stringToImage(String imgStr, String imgFilePath)throws IOException {
        if (imgStr == null){
            return false;
        }
        File file = new File(imgFilePath);
        if (!file.exists()) {
            new File(file.getParent()).mkdirs();
        }
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            imgStr=imgStr.substring(imgStr.indexOf(",") + 1);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return true;
        }
    }

    public static byte[] getBytes(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "PNG", baos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

}
