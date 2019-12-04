package com.example.gohome.utils;//package com.example.demo.utils;
//
//import java.awt.AlphaComposite;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.RenderingHints;
//import java.awt.Toolkit;
//import java.awt.color.ColorSpace;
//import java.awt.geom.AffineTransform;
//import java.awt.image.AffineTransformOp;
//import java.awt.image.BufferedImage;
//import java.awt.image.ColorConvertOp;
//import java.awt.image.ColorModel;
//import java.awt.image.CropImageFilter;
//import java.awt.image.DataBufferByte;
//import java.awt.image.FilteredImageSource;
//import java.awt.image.ImageFilter;
//import java.awt.image.ImageProducer;
//import java.awt.image.MemoryImageSource;
//import java.awt.image.PixelGrabber;
//import java.awt.image.WritableRaster;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.Iterator;
//
//import javax.imageio.ImageIO;
//import javax.imageio.ImageReader;
//import javax.imageio.stream.ImageInputStream;
//import javax.swing.JOptionPane;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
//
//@SuppressWarnings("restriction")
//public class ImageUtil{
//
//	private static Logger LoggerUtil = LoggerFactory.getLogger("ImageUtil");
//
//	/*public static void main(String[] args)  throws IOException{
//		//TODO 生成缩略图、图片水印、文字水印、灰度值计算、计算数组的平均值
//		//TODO 缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印
//		// 1-缩放图像：
//		// 方法一：按比例缩放
//		ImageUtil.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);// 测试OK
//		// 方法二：按高度和宽度缩放
//		ImageUtil.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300, true);// 测试OK
//		// 2-切割图像：
//		// 方法一：按指定起点坐标和宽高切割
//		ImageUtil.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400);// 测试OK
//		// 方法二：指定切片的行数和列数
//		ImageUtil.cut2("e:/abc.jpg", "e:/", 2, 2);// 测试OK
//		// 方法三：指定切片的宽度和高度
//		ImageUtil.cut3("e:/abc.jpg", "e:/", 300, 300);// 测试OK
//		// 3-图像类型转换：
//		ImageUtil.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");// 测试OK
//		// 4-彩色转黑白：
//		ImageUtil.gray("e:/abc.jpg", "e:/abc_gray.jpg");// 测试OK
//		// 5-给图片添加文字水印：
//		// 方法一：
//		ImageUtil.pressText("我是水印文字", "e:/abc.jpg", "e:/abc_pressText.jpg",
//				"宋体", Font.BOLD, Color.white, 80, 0, 0, 0.5f);// 测试OK
//		// 方法二：
//		ImageUtil.pressText2("我也是水印文字", "e:/abc.jpg", "e:/abc_pressText2.jpg",
//				"黑体", 36, Color.white, 80, 0, 0, 0.5f);// 测试OK
//		// 6-给图片添加图片水印：
//		ImageUtil.pressImage("e:/abc2.jpg", "e:/abc.jpg",
//				"e:/abc_pressImage.jpg", 0, 0, 0.5f);// 测试OK
//
//		//TODO 图像去噪<cleanImage()>
//
//		//TODO 将文字转换为图片<string2Image()>
//		string2Image(500, 500, 20, 10, 10, "文字转换为图片", "e:/test.jpg");
//
//		// 转tiff<toTiff()>
//
//		//调整亮度/对比度 <filter()>
//
//		//图像二值化 ;提升清晰度,进行锐化;中值滤波;线性灰度变换;转换为黑白灰度图;平滑缩放;按比例放缩图片;将图片转BufferedImage
//
//	}*/
//
//	// 项目根目录路径
//	public static final String path = "E:\\images\\";
//
//	/**
//	 * 生成缩略图 <br/>
//	 * 保存:ImageIO.write(BufferedImage, imgType[jpg/png/...], File);
//	 *
//	 * @param source
//	 *            原图片
//	 * @param width
//	 *            缩略图宽
//	 * @param height
//	 *            缩略图高
//	 * @param b
//	 *            是否等比缩放
//	 * */
//	public static BufferedImage thumb(BufferedImage source, int width,
//			int height, boolean b) {
//		// targetW，targetH分别表示目标长和宽
//		int type = source.getType();
//		BufferedImage target = null;
//		double sx = (double) width / source.getWidth();
//		double sy = (double) height / source.getHeight();
//
//		if (b) {
//			if (sx > sy) {
//				sx = sy;
//				width = (int) (sx * source.getWidth());
//			} else {
//				sy = sx;
//				height = (int) (sy * source.getHeight());
//			}
//		}
//
//		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
//			ColorModel cm = source.getColorModel();
//			WritableRaster raster = cm.createCompatibleWritableRaster(width,
//					height);
//			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
//			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
//		} else
//			target = new BufferedImage(width, height, type);
//		Graphics2D g = target.createGraphics();
//		// smoother than exlax:
//		g.setRenderingHint(RenderingHints.KEY_RENDERING,
//				RenderingHints.VALUE_RENDER_QUALITY);
//		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
//		g.dispose();
//		return target;
//	}
//
//	/**
//	 * 图片水印
//	 *
//	 * @param imgPath
//	 *            待处理图片
//	 * @param markPath
//	 *            水印图片
//	 * @param x
//	 *            水印位于图片左上角的 x 坐标值
//	 * @param y
//	 *            水印位于图片左上角的 y 坐标值
//	 * @param alpha
//	 *            水印透明度 0.1f ~ 1.0f
//	 * */
//	public static void waterMark(String imgPath, String markPath, int x, int y,
//			float alpha) {
//		try {
//			// 加载待处理图片文件
//			Image img = ImageIO.read(new File(imgPath));
//
//			BufferedImage image = new BufferedImage(img.getWidth(null),
//					img.getHeight(null), BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = image.createGraphics();
//			g.drawImage(img, 0, 0, null);
//
//			// 加载水印图片文件
//			Image src_biao = ImageIO.read(new File(markPath));
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//					alpha));
//			g.drawImage(src_biao, x, y, null);
//			g.dispose();
//
//			// 保存处理后的文件
//			FileOutputStream out = new FileOutputStream(imgPath);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(image);
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 文字水印
//	 *
//	 * @param imgPath
//	 *            待处理图片
//	 * @param text
//	 *            水印文字
//	 * @param font
//	 *            水印字体信息
//	 * @param color
//	 *            水印字体颜色
//	 * @param x
//	 *            水印位于图片左上角的 x 坐标值
//	 * @param y
//	 *            水印位于图片左上角的 y 坐标值
//	 * @param alpha
//	 *            水印透明度 0.1f ~ 1.0f
//	 */
//
//	public static void textMark(String imgPath, String text, Font font,
//			Color color, int x, int y, float alpha) {
//		try {
//			Font Dfont = (font == null) ? new Font("宋体", 20, 13) : font;
//
//			Image img = ImageIO.read(new File(imgPath));
//
//			BufferedImage image = new BufferedImage(img.getWidth(null),
//					img.getHeight(null), BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = image.createGraphics();
//
//			g.drawImage(img, 0, 0, null);
//			g.setColor(color);
//			g.setFont(Dfont);
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//					alpha));
//			g.drawString(text, x, y);
//			g.dispose();
//			FileOutputStream out = new FileOutputStream(imgPath);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(image);
//			out.close();
//		} catch (Exception e) {
//			LoggerUtil.info(e.getMessage());
//		}
//	}
//
//	/**
//	 * 灰度值计算
//	 * @param pixels 像素
//	 * @return int 灰度值
//	 */
//	public static int rgbToGray(int pixels) {
//		// int _alpha = (pixels >> 24) & 0xFF;
//		int _red = (pixels >> 16) & 0xFF;
//		int _green = (pixels >> 8) & 0xFF;
//		int _blue = (pixels) & 0xFF;
//		return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
//	}
//
//	/**
//	 * 计算数组的平均值
//	 * @param pixels 数组
//	 * @return int 平均值
//	 */
//	public static int average(int[] pixels) {
//		float m = 0;
//		for (int i = 0; i < pixels.length; ++i) {
//			m += pixels[i];
//		}
//		m = m / pixels.length;
//		return (int) m;
//	}
//
//	//TODO 缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印#####################################################################################################################################################################################
//
//	/**
//	 * 几种常见的图片格式
//	 */
//	//public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
//	//public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
//	//public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
//	//public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
//	//public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
//	//public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop
//
//	/**
//	 * 缩放图像（按比例缩放）
//	 *
//	 * @param srcImageFile
//	 *            源图像文件地址
//	 * @param result
//	 *            缩放后的图像地址
//	 * @param scale
//	 *            缩放比例
//	 * @param flag
//	 *            缩放选择:true 放大; false 缩小;
//	 */
//	public final static void scale(String srcImageFile, String result,
//			double scale, boolean flag) {
//		try {
//			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
//			int width = src.getWidth(); // 得到源图宽
//			int height = src.getHeight(); // 得到源图长
//			if (flag) {// 放大
//				width = (int)(width * scale);
//				height = (int)(height * scale);
//			} else {// 缩小
//				width = (int)(width / scale);
//				height = (int)(height / scale);
//			}
//			Image image = src.getScaledInstance(width, height,
//					Image.SCALE_DEFAULT);
//			BufferedImage tag = new BufferedImage(width, height,
//					BufferedImage.TYPE_INT_RGB);
//			Graphics g = tag.getGraphics();
//			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
//			g.dispose();
//			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 缩放图像（按高度和宽度缩放）
//	 *
//	 * @param srcImageFile
//	 *            源图像文件地址
//	 * @param result
//	 *            缩放后的图像地址
//	 * @param height
//	 *            缩放后的高度
//	 * @param width
//	 *            缩放后的宽度
//	 * @param bb
//	 *            比例不对时是否需要补白：true为补白; false为不补白;
//	 */
//	@SuppressWarnings("static-access")
//	public final static void scale2(String srcImageFile, String result,
//			int height, int width, boolean bb) {
//		try {
//			double ratio = 0.0; // 缩放比例
//			File f = new File(srcImageFile);
//			BufferedImage bi = ImageIO.read(f);
//			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
//			// 计算比例
//			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
//				if (bi.getHeight() > bi.getWidth()) {
//					ratio = (new Integer(height)).doubleValue()
//							/ bi.getHeight();
//				} else {
//					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
//				}
//				AffineTransformOp op = new AffineTransformOp(
//						AffineTransform.getScaleInstance(ratio, ratio), null);
//				itemp = op.filter(bi, null);
//			}
//			if (bb) {// 补白
//				BufferedImage image = new BufferedImage(width, height,
//						BufferedImage.TYPE_INT_RGB);
//				Graphics2D g = image.createGraphics();
//				g.setColor(Color.white);
//				g.fillRect(0, 0, width, height);
//				if (width == itemp.getWidth(null))
//					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
//							itemp.getWidth(null), itemp.getHeight(null),
//							Color.white, null);
//				else
//					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
//							itemp.getWidth(null), itemp.getHeight(null),
//							Color.white, null);
//				g.dispose();
//				itemp = image;
//			}
//			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @param srcImageFile 图片
//	 * @param height 缩小的高
//	 * @param width 缩小的宽
//	 * @return
//	 */
//	public final static byte[] scale(String srcImageFile, int width, int height) {
//		return scale(new File(srcImageFile), width, height);
//	}
//
//	/**
//	 * 缩小图片
//	 * @param srcdata
//	 * @param width
//	 * @param height
//	 * @return
//	 */
//	public final static byte[] scale(byte[] srcdata, int width, int height) {
//		ByteArrayInputStream in = null;
//		BufferedImage image = null;
//		try {
//			in = new ByteArrayInputStream(srcdata);
//			image = ImageIO.read(in);
//			return scale(image, width, height);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally{
//			if(image!=null)
//				image.flush();
//			try {
//				if(in!=null)
//					in.close();
//			} catch (IOException e) {}
//		}
//		return null;
//	}
//
//	/**
//	 * @param srcImageFile 图片
//	 * @param height 缩小的高
//	 * @param width 缩小的宽
//	 * @return
//	 */
//	public final static byte[] scale(File srcImageFile, int width, int height) {
//		BufferedImage src = null;
//		try {
//			if(NullUtil.isNotNull(srcImageFile)){
//				src = ImageIO.read(srcImageFile); // 读入文件
//				return scale(src, width, height);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally{
//			if(src!=null)
//				src.flush();
//		}
//		return null;
//	}
//
//	/**
//	 * @param srcImageFile 图片
//	 * @param height 缩小的高
//	 * @param width 缩小的宽
//	 * @return
//	 */
//	public final static byte[] scale(BufferedImage src, int width, int height) {
//		try {
//			if(NullUtil.isNotNull(src)){
//				if(src.getWidth() <= width)// 缩小
//					width = src.getWidth();
//				if(src.getHeight() <= width)// 缩小
//					height = src.getHeight();
//				//int swidth = src.getWidth(); // 得到源图宽
//				//int sheight = src.getHeight(); // 得到源图长
//				Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
//				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//				Graphics g = tag.getGraphics();
//				g.drawImage(image, 0, 0, null); // 绘制缩小后的图
//				g.dispose();
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				ImageIO.write(tag, "JPEG", out);// 输出到文件流
//				return out.toByteArray();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 图像切割(按指定起点坐标和宽高切割)
//	 *
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param result
//	 *            切片后的图像地址
//	 * @param x
//	 *            目标切片起点坐标X
//	 * @param y
//	 *            目标切片起点坐标Y
//	 * @param width
//	 *            目标切片宽度
//	 * @param height
//	 *            目标切片高度
//	 */
//	public final static void cut(String srcImageFile, String result, int x,
//			int y, int width, int height) {
//		try {
//			// 读取源图像
//			BufferedImage bi = ImageIO.read(new File(srcImageFile));
//			int srcWidth = bi.getHeight(); // 源图宽度
//			int srcHeight = bi.getWidth(); // 源图高度
//			if (srcWidth > 0 && srcHeight > 0) {
//				Image image = bi.getScaledInstance(srcWidth, srcHeight,
//						Image.SCALE_DEFAULT);
//				// 四个参数分别为图像起点坐标和宽高
//				// 即: CropImageFilter(int x,int y,int width,int height)
//				ImageFilter cropFilter = new CropImageFilter(x, y, width,
//						height);
//				Image img = Toolkit.getDefaultToolkit().createImage(
//						new FilteredImageSource(image.getSource(), cropFilter));
//				BufferedImage tag = new BufferedImage(width, height,
//						BufferedImage.TYPE_INT_RGB);
//				Graphics g = tag.getGraphics();
//				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
//				g.dispose();
//				// 输出为文件
//				ImageIO.write(tag, "JPEG", new File(result));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 图像切割（指定切片的行数和列数）
//	 *
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param descDir
//	 *            切片目标文件夹
//	 * @param rows
//	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
//	 * @param cols
//	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
//	 */
//	public final static void cut2(String srcImageFile, String descDir,
//			int rows, int cols) {
//		try {
//			if (rows <= 0 || rows > 20)
//				rows = 2; // 切片行数
//			if (cols <= 0 || cols > 20)
//				cols = 2; // 切片列数
//			// 读取源图像
//			BufferedImage bi = ImageIO.read(new File(srcImageFile));
//			int srcWidth = bi.getHeight(); // 源图宽度
//			int srcHeight = bi.getWidth(); // 源图高度
//			if (srcWidth > 0 && srcHeight > 0) {
//				Image img;
//				ImageFilter cropFilter;
//				Image image = bi.getScaledInstance(srcWidth, srcHeight,
//						Image.SCALE_DEFAULT);
//				int destWidth = srcWidth; // 每张切片的宽度
//				int destHeight = srcHeight; // 每张切片的高度
//				// 计算切片的宽度和高度
//				if (srcWidth % cols == 0) {
//					destWidth = srcWidth / cols;
//				} else {
//					destWidth = (int) Math.floor(srcWidth / cols) + 1;
//				}
//				if (srcHeight % rows == 0) {
//					destHeight = srcHeight / rows;
//				} else {
//					destHeight = (int) Math.floor(srcWidth / rows) + 1;
//				}
//				// 循环建立切片
//				// 改进的想法:是否可用多线程加快切割速度
//				for (int i = 0; i < rows; i++) {
//					for (int j = 0; j < cols; j++) {
//						// 四个参数分别为图像起点坐标和宽高
//						// 即: CropImageFilter(int x,int y,int width,int height)
//						cropFilter = new CropImageFilter(j * destWidth, i
//								* destHeight, destWidth, destHeight);
//						img = Toolkit.getDefaultToolkit().createImage(
//								new FilteredImageSource(image.getSource(),
//										cropFilter));
//						BufferedImage tag = new BufferedImage(destWidth,
//								destHeight, BufferedImage.TYPE_INT_RGB);
//						Graphics g = tag.getGraphics();
//						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
//						g.dispose();
//						// 输出为文件
//						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
//								+ "_c" + j + ".jpg"));
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 图像切割（指定切片的宽度和高度）
//	 *
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param descDir
//	 *            切片目标文件夹
//	 * @param destWidth
//	 *            目标切片宽度。默认200
//	 * @param destHeight
//	 *            目标切片高度。默认150
//	 */
//	public final static void cut3(String srcImageFile, String descDir,
//			int destWidth, int destHeight) {
//		try {
//			if (destWidth <= 0)
//				destWidth = 200; // 切片宽度
//			if (destHeight <= 0)
//				destHeight = 150; // 切片高度
//			// 读取源图像
//			BufferedImage bi = ImageIO.read(new File(srcImageFile));
//			int srcWidth = bi.getHeight(); // 源图宽度
//			int srcHeight = bi.getWidth(); // 源图高度
//			if (srcWidth > destWidth && srcHeight > destHeight) {
//				Image img;
//				ImageFilter cropFilter;
//				Image image = bi.getScaledInstance(srcWidth, srcHeight,
//						Image.SCALE_DEFAULT);
//				int cols = 0; // 切片横向数量
//				int rows = 0; // 切片纵向数量
//				// 计算切片的横向和纵向数量
//				if (srcWidth % destWidth == 0) {
//					cols = srcWidth / destWidth;
//				} else {
//					cols = (int) Math.floor(srcWidth / destWidth) + 1;
//				}
//				if (srcHeight % destHeight == 0) {
//					rows = srcHeight / destHeight;
//				} else {
//					rows = (int) Math.floor(srcHeight / destHeight) + 1;
//				}
//				// 循环建立切片
//				// 改进的想法:是否可用多线程加快切割速度
//				for (int i = 0; i < rows; i++) {
//					for (int j = 0; j < cols; j++) {
//						// 四个参数分别为图像起点坐标和宽高
//						// 即: CropImageFilter(int x,int y,int width,int height)
//						cropFilter = new CropImageFilter(j * destWidth, i
//								* destHeight, destWidth, destHeight);
//						img = Toolkit.getDefaultToolkit().createImage(
//								new FilteredImageSource(image.getSource(),
//										cropFilter));
//						BufferedImage tag = new BufferedImage(destWidth,
//								destHeight, BufferedImage.TYPE_INT_RGB);
//						Graphics g = tag.getGraphics();
//						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
//						g.dispose();
//						// 输出为文件
//						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
//								+ "_c" + j + ".jpg"));
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
//	 *
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param formatName
//	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
//	 * @param destImageFile
//	 *            目标图像地址
//	 */
//	public final static void convert(String srcImageFile, String formatName,
//			String destImageFile) {
//		try {
//			File f = new File(srcImageFile);
//			f.canRead();
//			f.canWrite();
//			BufferedImage src = ImageIO.read(f);
//			ImageIO.write(src, formatName, new File(destImageFile));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 彩色转为黑白
//	 *
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param destImageFile
//	 *            目标图像地址
//	 */
//	public final static void gray(String srcImageFile, String destImageFile) {
//		try {
//			BufferedImage src = ImageIO.read(new File(srcImageFile));
//			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
//			ColorConvertOp op = new ColorConvertOp(cs, null);
//			src = op.filter(src, null);
//			ImageIO.write(src, "JPEG", new File(destImageFile));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 给图片添加文字水印
//	 *
//	 * @param pressText
//	 *            水印文字
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param destImageFile
//	 *            目标图像地址
//	 * @param fontName
//	 *            水印的字体名称
//	 * @param fontStyle
//	 *            水印的字体样式
//	 * @param color
//	 *            水印的字体颜色
//	 * @param fontSize
//	 *            水印的字体大小
//	 * @param x
//	 *            修正值
//	 * @param y
//	 *            修正值
//	 * @param alpha
//	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
//	 */
//	public final static void pressText(String pressText, String srcImageFile,
//			String destImageFile, String fontName, int fontStyle, Color color,
//			int fontSize, int x, int y, float alpha) {
//		try {
//			File img = new File(srcImageFile);
//			Image src = ImageIO.read(img);
//			int width = src.getWidth(null);
//			int height = src.getHeight(null);
//			BufferedImage image = new BufferedImage(width, height,
//					BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = image.createGraphics();
//			g.drawImage(src, 0, 0, width, height, null);
//			g.setColor(color);
//			g.setFont(new Font(fontName, fontStyle, fontSize));
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//					alpha));
//			// 在指定坐标绘制水印文字
//			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
//					/ 2 + x, (height - fontSize) / 2 + y);
//			g.dispose();
//			ImageIO.write((BufferedImage) image, "JPEG",
//					new File(destImageFile));// 输出到文件流
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 给图片添加文字水印
//	 *
//	 * @param pressText
//	 *            水印文字
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param destImageFile
//	 *            目标图像地址
//	 * @param fontName
//	 *            字体名称
//	 * @param fontStyle
//	 *            字体样式
//	 * @param color
//	 *            字体颜色
//	 * @param fontSize
//	 *            字体大小
//	 * @param x
//	 *            修正值
//	 * @param y
//	 *            修正值
//	 * @param alpha
//	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
//	 */
//	public final static void pressText2(String pressText, String srcImageFile,
//			String destImageFile, String fontName, int fontStyle, Color color,
//			int fontSize, int x, int y, float alpha) {
//		try {
//			File img = new File(srcImageFile);
//			Image src = ImageIO.read(img);
//			int width = src.getWidth(null);
//			int height = src.getHeight(null);
//			BufferedImage image = new BufferedImage(width, height,
//					BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = image.createGraphics();
//			g.drawImage(src, 0, 0, width, height, null);
//			g.setColor(color);
//			g.setFont(new Font(fontName, fontStyle, fontSize));
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//					alpha));
//			// 在指定坐标绘制水印文字
//			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
//					/ 2 + x, (height - fontSize) / 2 + y);
//			g.dispose();
//			ImageIO.write((BufferedImage) image, "JPEG",
//					new File(destImageFile));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 给图片添加图片水印
//	 * @param pressImg
//	 *            水印图片
//	 * @param srcImageFile
//	 *            源图像地址
//	 * @param destImageFile
//	 *            目标图像地址
//	 * @param x
//	 *            修正值。 默认在中间
//	 * @param y
//	 *            修正值。 默认在中间
//	 * @param alpha
//	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
//	 */
//	public final static void pressImage(String pressImg, String srcImageFile,
//			String destImageFile, int x, int y, float alpha) {
//		try {
//			File img = new File(srcImageFile);
//			Image src = ImageIO.read(img);
//			int wideth = src.getWidth(null);
//			int height = src.getHeight(null);
//			BufferedImage image = new BufferedImage(wideth, height,
//					BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = image.createGraphics();
//			g.drawImage(src, 0, 0, wideth, height, null);
//			// 水印文件
//			Image src_biao = ImageIO.read(new File(pressImg));
//			int wideth_biao = src_biao.getWidth(null);
//			int height_biao = src_biao.getHeight(null);
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//					alpha));
//			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
//					(height - height_biao) / 2, wideth_biao, height_biao, null);
//			// 水印文件结束
//			g.dispose();
//			ImageIO.write((BufferedImage) image, "JPEG",
//					new File(destImageFile));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 计算text的长度（一个中文算两个字符）
//	 *
//	 * @param text
//	 * @return
//	 */
//	public final static int getLength(String text) {
//		int length = 0;
//		for (int i = 0; i < text.length(); i++) {
//			if (new String(text.charAt(i) + "").getBytes().length > 1) {
//				length += 2;
//			} else {
//				length += 1;
//			}
//		}
//		return length / 2;
//	}
//
//	//TODO 图像去噪########################################################
//	/**
//	 * @param sfile 需要去噪的图像
//	 * @param nfile 去噪后的图像保存地址
//	 * @throws IOException
//	 */
//	public static File cleanImage(File sfile, File nfile)
//			throws IOException {
//		BufferedImage bufferedImage = ImageIO.read(sfile);
//		int h = bufferedImage.getHeight();
//		int w = bufferedImage.getWidth();
//		// 灰度化
//		int[][] gray = new int[w][h];
//		for (int x = 0; x < w; x++) {
//			for (int y = 0; y < h; y++) {
//				int argb = bufferedImage.getRGB(x, y);
//				// 图像加亮（调整亮度识别率非常高）
//				int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
//				int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
//				int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
//				if (r >= 255) {
//					r = 255;
//				}
//				if (g >= 255) {
//					g = 255;
//				}
//				if (b >= 255) {
//					b = 255;
//				}
//				gray[x][y] = (int) Math
//						.pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)
//								* 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
//			}
//		}
//		// 二值化
//		int threshold = ostu(gray, w, h);
//		BufferedImage binaryBufferedImage = new BufferedImage(w, h,
//				BufferedImage.TYPE_BYTE_BINARY);
//		for (int x = 0; x < w; x++) {
//			for (int y = 0; y < h; y++) {
//				if (gray[x][y] > threshold) {
//					gray[x][y] |= 0x00FFFF;
//				} else {
//					gray[x][y] &= 0xFF0000;
//				}
//				binaryBufferedImage.setRGB(x, y, gray[x][y]);
//			}
//		}
//		// 矩阵打印
//		/*for (int y = 0; y < h; y++) {
//			for (int x = 0; x < w; x++) {
//				if (isBlack(binaryBufferedImage.getRGB(x, y))) {
//					System.out.print("*");
//				} else {
//					System.out.print(" ");
//				}
//			}
//			LoggerUtil.info();
//		}*/
//		ImageIO.write(binaryBufferedImage, "jpg", nfile);
//		return nfile;
//	}
//
//	public static boolean isBlack(int colorInt) {
//		Color color = new Color(colorInt);
//		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isWhite(int colorInt) {
//		Color color = new Color(colorInt);
//		if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
//			return true;
//		}
//		return false;
//	}
//
//	public static int isBlackOrWhite(int colorInt) {
//		if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
//			return 1;
//		}
//		return 0;
//	}
//
//	public static int getColorBright(int colorInt) {
//		Color color = new Color(colorInt);
//		return color.getRed() + color.getGreen() + color.getBlue();
//	}
//
//	public static int ostu(int[][] gray, int w, int h) {
//		int[] histData = new int[w * h];
//		// Calculate histogram
//		for (int x = 0; x < w; x++) {
//			for (int y = 0; y < h; y++) {
//				int red = 0xFF & gray[x][y];
//				histData[red]++;
//			}
//		}
//
//		// Total number of pixels
//		int total = w * h;
//
//		float sum = 0;
//		for (int t = 0; t < 256; t++)
//			sum += t * histData[t];
//
//		float sumB = 0;
//		int wB = 0;
//		int wF = 0;
//
//		float varMax = 0;
//		int threshold = 0;
//
//		for (int t = 0; t < 256; t++) {
//			wB += histData[t]; // Weight Background
//			if (wB == 0)
//				continue;
//
//			wF = total - wB; // Weight Foreground
//			if (wF == 0)
//				break;
//
//			sumB += (float) (t * histData[t]);
//
//			float mB = sumB / wB; // Mean Background
//			float mF = (sum - sumB) / wF; // Mean Foreground
//
//			// Calculate Between Class Variance
//			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
//
//			// Check if new maximum found
//			if (varBetween > varMax) {
//				varMax = varBetween;
//				threshold = t;
//			}
//		}
//		return threshold;
//	}
//
//	//TODO 将文字转换为图片################################################################################
//	/**
//	 * 将文字转换为图片
//	 * @param width 宽
//	 * @param height 高
//	 * @param fontSize 字体大小
//	 * @param drawX 字符串应呈现的位置的x坐标
//	 * @param drawY 符串应该被渲染的位置的y坐标
//	 * @param content 内容
//	 * @param filePath 生成绝对路径
//	 */
//	public static void string2Image(int width, int height, int fontSize, int drawX, int drawY, String content, String filePath) {
//		BufferedImage bi = null;
//		Graphics2D g2 = null;
//		OutputStream os = null;
//		try {
//			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//			g2 = (Graphics2D) bi.getGraphics();
//			g2.setBackground(Color.white);
//			Color bg = new Color(255, 255, 255);
//			g2.setColor(bg);
//			g2.fillRect(0, 0, width, height);
//			g2.setColor(Color.black);
//			Font font = new Font("宋体", Font.BOLD, fontSize);
//			g2.setFont(font);
//			g2.drawString(content, drawX, drawY);
//			os = new FileOutputStream(filePath);
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
//			encoder.encode(bi);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (g2 != null) {
//				g2.dispose();
//			}
//			if (os != null) {
//				try {
//					os.flush();
//					os.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//
//    /**
//     * 调整亮度/对比度
//     * @param src 图片
//     * @param contrast 调整对比度
//     * @param brightness 调整亮度
//     * @return
//     */
//	@SuppressWarnings("unused")
//	public static BufferedImage filter(BufferedImage src, float contrast, float brightness) {
//		// 获得源图片长度和宽度
//		int width = src.getWidth();
//		int height = src.getHeight();
//		BufferedImage dest = new BufferedImage(width, height, src.getType());
//		int[] inPixels = new int[width * height];
//		int[] outPixels = new int[width * height];
//		src.getRGB(0, 0, width, height, inPixels, 0, width);
//
//		// 计算一个像素的红，绿，蓝方法
//		int index = 0;
//		int[] rgbmeans = new int[3];
//		double redSum = 0, greenSum = 0, blueSum = 0;
//		double total = height * width;
//		for (int row = 0; row < height; row++) {
//			int ta = 0, tr = 0, tg = 0, tb = 0;
//			for (int col = 0; col < width; col++) {
//				index = row * width + col;
//				ta = (inPixels[index] >> 24) & 0xff;
//				tr = (inPixels[index] >> 16) & 0xff;
//				tg = (inPixels[index] >> 8) & 0xff;
//				tb = inPixels[index] & 0xff;
//				redSum += tr;
//				greenSum += tg;
//				blueSum += tb;
//			}
//		}
//		// 求出图像像素平均值
//		rgbmeans[0] = (int) (redSum / total);
//		rgbmeans[1] = (int) (greenSum / total);
//		rgbmeans[2] = (int) (blueSum / total);
//
//		// 调整对比度，亮度
//		for (int row = 0; row < height; row++) {
//			int ta = 0, tr = 0, tg = 0, tb = 0;
//			for (int col = 0; col < width; col++) {
//				ta = (inPixels[index] >> 24) & 0xff;
//				tr = (inPixels[index] >> 16) & 0xff;
//				tg = (inPixels[index] >> 8) & 0xff;
//				tb = inPixels[index] & 0xff;
//
//				// 移去平均值
//				tr -= rgbmeans[0];
//				tg -= rgbmeans[1];
//				tb -= rgbmeans[2];
//
//				// 调整对比度
//				tr = (int) (tr * contrast);
//				tg = (int) (tg * contrast);
//				tb = (int) (tb * contrast);
//
//				// 调整亮度
//				tr = (int) ((tr + rgbmeans[0]) * brightness);
//				tg = (int) ((tg + rgbmeans[1]) * brightness);
//				tb = (int) ((tb + rgbmeans[2]) * brightness); // end;
//				/*
//				 * tr +=(int)(rgbmeans[0] * brightness); tg +=(int)(rgbmeans[1]
//				 * * brightness); tb +=(int)(rgbmeans[2] * brightness); //end;
//				 */
//
//				outPixels[index] = (ta << 24) | (clamp(tr) << 16)
//						| (clamp(tg) << 8) | clamp(tb);
//
//			}
//		}
//		dest.setRGB(0, 0, width, height, outPixels, 0, width);
//		return dest;
//	}
//
//	/** 图像二值化 */
//	public BufferedImage changeGrey(BufferedImage image) {
//		int iw = image.getWidth();
//		int ih = image.getHeight();
//		int[] pixels = new int[iw * ih];
//		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih,
//				pixels, 0, iw);
//		try {
//			pg.grabPixels();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		// 设定二值化的域值，默认值为100
//		int grey = 100;
//		// 对图像进行二值化处理，Alpha值保持不变
//		ColorModel cm = ColorModel.getRGBdefault();
//		for (int i = 0; i < iw * ih; i++) {
//			int red, green, blue;
//			int alpha = cm.getAlpha(pixels[i]);
//			if (cm.getRed(pixels[i]) > grey) {
//				red = 255;
//			} else {
//				red = 0;
//			}
//			if (cm.getGreen(pixels[i]) > grey) {
//				green = 255;
//			} else {
//				green = 0;
//			}
//			if (cm.getBlue(pixels[i]) > grey) {
//				blue = 255;
//			} else {
//				blue = 0;
//			}
//			pixels[i] = alpha << 24 | red << 16 | green << 8 | blue;
//		}
//		// 将数组中的象素产生一个图像
//		return imageProducerToBufferedImage(new MemoryImageSource(iw, ih,
//				pixels, 0, iw));
//	}
//
//	/** 提升清晰度,进行锐化 */
//	public BufferedImage sharp(BufferedImage image) {
//		int iw = image.getWidth();
//		int ih = image.getHeight();
//		int[] pixels = new int[iw * ih];
//		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih,
//				pixels, 0, iw);
//		try {
//			pg.grabPixels();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		// 象素的中间变量
//		int tempPixels[] = new int[iw * ih];
//		for (int i = 0; i < iw * ih; i++) {
//			tempPixels[i] = pixels[i];
//		}
//		// 对图像进行尖锐化处理，Alpha值保持不变
//		ColorModel cm = ColorModel.getRGBdefault();
//		for (int i = 1; i < ih - 1; i++) {
//
//			for (int j = 1; j < iw - 1; j++) {
//
//				int alpha = cm.getAlpha(pixels[i * iw + j]);
//
//				// 对图像进行尖锐化
//				int red6 = cm.getRed(pixels[i * iw + j + 1]);
//				int red5 = cm.getRed(pixels[i * iw + j]);
//				int red8 = cm.getRed(pixels[(i + 1) * iw + j]);
//				int sharpRed = Math.abs(red6 - red5) + Math.abs(red8 - red5);
//				int green5 = cm.getGreen(pixels[i * iw + j]);
//				int green6 = cm.getGreen(pixels[i * iw + j + 1]);
//				int green8 = cm.getGreen(pixels[(i + 1) * iw + j]);
//				int sharpGreen = Math.abs(green6 - green5)
//						+ Math.abs(green8 - green5);
//				int blue5 = cm.getBlue(pixels[i * iw + j]);
//				int blue6 = cm.getBlue(pixels[i * iw + j + 1]);
//				int blue8 = cm.getBlue(pixels[(i + 1) * iw + j]);
//				int sharpBlue = Math.abs(blue6 - blue5)
//						+ Math.abs(blue8 - blue5);
//				if (sharpRed > 255) {
//					sharpRed = 255;
//				}
//				if (sharpGreen > 255) {
//					sharpGreen = 255;
//				}
//				if (sharpBlue > 255) {
//					sharpBlue = 255;
//				}
//				tempPixels[i * iw + j] = alpha << 24 | sharpRed << 16
//						| sharpGreen << 8 | sharpBlue;
//			}
//		}
//		// 将数组中的象素产生一个图像
//
//		return imageProducerToBufferedImage(new MemoryImageSource(iw, ih,
//				tempPixels, 0, iw));
//	}
//
//	/** 中值滤波 */
//	public BufferedImage median(BufferedImage image) {
//		int iw = image.getWidth();
//		int ih = image.getHeight();
//		int[] pixels = new int[iw * ih];
//		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih,
//				pixels, 0, iw);
//		try {
//			pg.grabPixels();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		// 对图像进行中值滤波，Alpha值保持不变
//		ColorModel cm = ColorModel.getRGBdefault();
//		for (int i = 1; i < ih - 1; i++) {
//			for (int j = 1; j < iw - 1; j++) {
//				int red, green, blue;
//				int alpha = cm.getAlpha(pixels[i * iw + j]);
//				// int red2 = cm.getRed(pixels[(i - 1) * iw + j]);
//				int red4 = cm.getRed(pixels[i * iw + j - 1]);
//				int red5 = cm.getRed(pixels[i * iw + j]);
//				int red6 = cm.getRed(pixels[i * iw + j + 1]);
//				// int red8 = cm.getRed(pixels[(i + 1) * iw + j]);
//				// 水平方向进行中值滤波
//				if (red4 >= red5) {
//					if (red5 >= red6) {
//						red = red5;
//					} else {
//						if (red4 >= red6) {
//							red = red6;
//						} else {
//							red = red4;
//						}
//					}
//				} else {
//					if (red4 > red6) {
//						red = red4;
//					} else {
//						if (red5 > red6) {
//							red = red6;
//						} else {
//							red = red5;
//						}
//					}
//				}
//				// int green2 = cm.getGreen(pixels[(i - 1) * iw + j]);
//				int green4 = cm.getGreen(pixels[i * iw + j - 1]);
//				int green5 = cm.getGreen(pixels[i * iw + j]);
//				int green6 = cm.getGreen(pixels[i * iw + j + 1]);
//				// int green8 = cm.getGreen(pixels[(i + 1) * iw + j]);
//				// 水平方向进行中值滤波
//				if (green4 >= green5) {
//					if (green5 >= green6) {
//						green = green5;
//					} else {
//						if (green4 >= green6) {
//							green = green6;
//						} else {
//							green = green4;
//						}
//					}
//				} else {
//					if (green4 > green6) {
//						green = green4;
//					} else {
//						if (green5 > green6) {
//							green = green6;
//						} else {
//							green = green5;
//						}
//					}
//				}
//				// int blue2 = cm.getBlue(pixels[(i - 1) * iw + j]);
//				int blue4 = cm.getBlue(pixels[i * iw + j - 1]);
//				int blue5 = cm.getBlue(pixels[i * iw + j]);
//				int blue6 = cm.getBlue(pixels[i * iw + j + 1]);
//				// int blue8 = cm.getBlue(pixels[(i + 1) * iw + j]);
//				// 水平方向进行中值滤波
//				if (blue4 >= blue5) {
//					if (blue5 >= blue6) {
//						blue = blue5;
//					} else {
//						if (blue4 >= blue6) {
//							blue = blue6;
//						} else {
//							blue = blue4;
//						}
//					}
//				} else {
//					if (blue4 > blue6) {
//						blue = blue4;
//					} else {
//						if (blue5 > blue6) {
//							blue = blue6;
//						} else {
//							blue = blue5;
//						}
//					}
//				}
//				pixels[i * iw + j] = alpha << 24 | red << 16 | green << 8
//						| blue;
//			}
//		}
//		// 将数组中的象素产生一个图像
//		return imageProducerToBufferedImage(new MemoryImageSource(iw, ih,
//				pixels, 0, iw));
//	}
//
//	/** 线性灰度变换 */
//
//	public BufferedImage lineGrey(BufferedImage image) {
//		int iw = image.getWidth();
//		int ih = image.getHeight();
//		int[] pixels = new int[iw * ih];
//		PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih,
//				pixels, 0, iw);
//		try {
//			pg.grabPixels();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		// 对图像进行进行线性拉伸，Alpha值保持不变
//		ColorModel cm = ColorModel.getRGBdefault();
//		for (int i = 0; i < iw * ih; i++) {
//			int alpha = cm.getAlpha(pixels[i]);
//			int red = cm.getRed(pixels[i]);
//			int green = cm.getGreen(pixels[i]);
//			int blue = cm.getBlue(pixels[i]);
//			// 增加了图像的亮度
//			red = (int) (1.1 * red + 30);
//			green = (int) (1.1 * green + 30);
//			blue = (int) (1.1 * blue + 30);
//			if (red >= 255) {
//				red = 255;
//			}
//			if (green >= 255) {
//				green = 255;
//			}
//			if (blue >= 255) {
//				blue = 255;
//			}
//			pixels[i] = alpha << 24 | red << 16 | green << 8 | blue;
//		}
//		// 将数组中的象素产生一个图像
//		return imageProducerToBufferedImage(new MemoryImageSource(iw, ih,
//				pixels, 0, iw));
//	}
//
//	/** 转换为黑白灰度图 */
//	public BufferedImage grayFilter(BufferedImage image) {
//		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
//		ColorConvertOp op = new ColorConvertOp(cs, null);
//		return op.filter(image, null);
//	}
//
//	/** 平滑缩放 */
//	public BufferedImage scaling(BufferedImage image, double s) {
//		AffineTransform tx = new AffineTransform();
//		tx.scale(s, s);
//		AffineTransformOp op = new AffineTransformOp(tx,
//				AffineTransformOp.TYPE_BILINEAR);
//		return op.filter(image, null);
//	}
//
//	/**
//	 * 按比例放缩图片
//	 * @param image
//	 * @param s
//	 * @return
//	 */
//	public BufferedImage scale(BufferedImage image, Float s) {
//		int srcW = image.getWidth();
//		int srcH = image.getHeight();
//		int newW = Math.round(srcW * s);
//		int newH = Math.round(srcH * s);
//		// 先做水平方向上的伸缩变换
//		BufferedImage tmp = new BufferedImage(newW, newH, image.getType());
//		Graphics2D g = tmp.createGraphics();
//		for (int x = 0; x < newW; x++) {
//			g.setClip(x, 0, 1, srcH);
//			// 按比例放缩
//			g.drawImage(image, x - x * srcW / newW, 0, null);
//		}
//		// 再做垂直方向上的伸缩变换
//		BufferedImage dst = new BufferedImage(newW, newH, image.getType());
//		g = dst.createGraphics();
//		for (int y = 0; y < newH; y++) {
//			g.setClip(0, y, newW, 1);
//			// 按比例放缩
//			g.drawImage(tmp, 0, y - y * srcH / newH, null);
//		}
//		return dst;
//	}
//
//
//	/**
//	 * 将图片转BufferedImage
//	 * @param imageFile
//	 * @return
//	 */
//	public static BufferedImage getBufferedImage(File imageFile) {
//		BufferedImage al = null;
//		try {
//			String imageFileName = imageFile.getName();
//			String imageFormat = imageFileName.substring(imageFileName
//					.lastIndexOf('.') + 1);
//			Iterator<ImageReader> readers = ImageIO
//					.getImageReadersByFormatName(imageFormat);
//			ImageReader reader = readers.next();
//			if (reader == null) {
//				JOptionPane
//						.showConfirmDialog(null,
//						"Need to install JAI Image I/O package./nhttps://jai-imageio.dev.java.net");
//				return null;
//			}
//			ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
//			reader.setInput(iis);
//			al = reader.read(0);
//			reader.dispose();
//		} catch (IOException ioe) {
//			System.err.println(ioe.getMessage());
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		return al;
//	}
//
//	public static byte[] getImageByteData(BufferedImage image) {
//		WritableRaster raster = image.getRaster();
//		DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
//		return buffer.getData();
//	}
//
//	private static BufferedImage imgToBufferedImage(Image image) {
//		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
//				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
//		Graphics2D g = bufferedImage.createGraphics();
//		g.drawImage(image, 0, 0, null);
//		return bufferedImage;
//	}
//
//	private static BufferedImage imageProducerToBufferedImage(ImageProducer imageProducer) {
//		return imgToBufferedImage(Toolkit.getDefaultToolkit().createImage(
//				imageProducer));
//	}
//
//	private static int clamp(int value) {
//		return value > 255 ? 255 : (value < 0 ? 0 : value);
//	}
//
//}