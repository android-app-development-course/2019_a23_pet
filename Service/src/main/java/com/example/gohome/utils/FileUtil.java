package com.example.gohome.utils;

import com.example.gohome.utils.time.DateUtils;
import eu.medsea.mimeutil.MimeException;
import eu.medsea.mimeutil.MimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 文件相关工具类
 * 
 * @author chencaihui
 */
public class FileUtil {
	
	private static Logger LoggerUtil = LoggerFactory.getLogger("FileUtil");

	/**
	 * 获取系统桌面路径
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2016年10月12日 下午10:04:59
	 * @return
	 */
	public static String getSystemView() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		return fsv.getHomeDirectory().toString();// 获取当前用户桌面路径
	}

	/**
	 * 获取java包的绝对路径
	 * 
	 * @param packageName 项目包url
	 */
	public static String getJavaPath(final String packageName) {
		try {
			return new File(".\\src\\main\\java\\" + packageName.replace(".", "\\")).getCanonicalPath()
					+ File.separator;
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 创建目录并返回
	 */
	public static String createDir(String pathName) {
		File file = new File(ConstantUtil.PROJECT_ROOT_PATH + File.separator + pathName);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file.getPath();
	}

	public static void createDir(File dirFile) {
		if (dirFile != null && !dirFile.isFile() && !dirFile.exists()) {
			dirFile.mkdirs();
		}
	}

	/**
	 * 创建固定大小的文件并返回
	 */
	public static File createFile(String filePath, long fLength) {
		File file = new File(filePath);
		return createFile(file, fLength);
	}

	/**
	 * 创建固定大小的文件并返回
	 */
	public static File createFile(File file, long fLength) {
		try (RandomAccessFile raf = new RandomAccessFile(file, "rw");) {
			raf.setLength(fLength);
		} catch (Exception e) {
			LoggerUtil.error("创建固定大小的文件异常", e);
		}
		return file;
	}

	/**
	 * 创建文件
	 */
	public static void createFile(File file) {
		try {
			if (file != null) {
				if (!file.exists()) {
					if (file.isDirectory()) {
						file.mkdirs();
					} else {
						if (!file.getParentFile().exists()) {
							file.getParentFile().mkdirs();
						}
						file.createNewFile();
					}
				} else if (file.isDirectory()) {
					file.delete();
					file.createNewFile();
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("创建文件异常", e);
		}
	}

	/**
	 * 创建固定大小的文件并返回
	 */
	public static File createFile(String filePath) {
		File file = new File(decode(filePath));
		createFile(file);
		return file;
	}

	/**
	 * 读取文件
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年6月21日 上午9:33:46
	 * @param resourcefile
	 * @return
	 */
	public static final String readFile(File resourcefile) {
		try (FileInputStream fis = new FileInputStream(resourcefile);) {
			String encoding = ConstantUtil.UTF8;
			byte[] buf = new byte[1024];
			int hasRead = 0;
			StringBuffer sbkey = new StringBuffer();
			while ((hasRead = fis.read(buf)) > 0) {
				sbkey.append(new String(buf, 0, hasRead, encoding));
			}
			return sbkey.toString();
		} catch (Exception e) {
			LoggerUtil.error("读取文件内容异常", e);
		}
		return null;
	}

	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName 带有完整绝对路径的文件名
	 * @param encoding        文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public static String readTxt(File resourcefile) {
		StringBuffer str = new StringBuffer();
		try (FileInputStream fs = new FileInputStream(resourcefile);
				InputStreamReader isr = new InputStreamReader(fs, ConstantUtil.UTF8);
				BufferedReader br = new BufferedReader(isr);) {
			String data = null;
			while ((data = br.readLine()) != null) {
				if (str.length() == 0)
					str.append(data);
				else
					str.append("\n" + data);
			}
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage());
		}
		return str.toString();
	}

	/**
	 * 删除附件
	 * 
	 * @param file
	 */
	public static void delFile(File file) {
		if (NullUtil.isNotNull(file)) {
			try {
				boolean result = file.delete();
				if (!result) {
					System.gc();// 强制删除
					result = file.delete();
					if (!result) {
						Runtime.getRuntime().exec("rm -f " + file.getPath());
					}
				}
			} catch (Exception e) {
				LoggerUtil.error("删除文件异常", e);
			}
		}
	}

	/**
	 * 将流数据写入应用服务器
	 * 
	 * @param is：InputStream
	 * @param savePath:文件的绝对路径
	 * @return
	 */
	public static boolean write(InputStream is, String savePath) {
		File file = createFile(savePath);
		try (FileOutputStream fos = new FileOutputStream(file);) {
			byte[] buffer = new byte[1024 * 3];// 每次读3K字节
			int count = 0;// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count); // 向服务端文件写入字节流
			}
			return true;
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
		}
		return false;
	}

	/**
	 * 将流数据写入应用服务器
	 * 
	 * @param datas:二进制数据
	 * @param savePath:文件的绝对路径
	 * @return
	 */
	public static boolean write(byte[] datas, String savePath) {
		File file = createFile(savePath);
		try (FileOutputStream fos = new FileOutputStream(file);) {
			fos.write(datas); // 向服务端文件写入字节流
			return true;
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 将流数据写入应用服务器
	 * 
	 * @param datas:二进制数据
	 * @param savePath:文件的绝对路径
	 * @return
	 */
	public static boolean write(byte[] datas, File file) {
		createFile(file);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(datas); // 向服务端文件写入字节流
			return true;
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 从文件的某个位置开始写入
	 * 
	 * @param datas:二进制数据
	 * @param 写入文件
	 * @param start       从这里开始写入
	 * @return
	 */
	public static boolean writersf(byte[] datas, File file) {
		createFile(file);
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw");) {
			synchronized (accessFile) {
				accessFile.seek(accessFile.length());
				accessFile.write(datas);
				return true;
			}
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath 文件夹完整绝对路径
	 * @return
	 */
	public static void delFolder(File file) {
		try {
			delAllFile(file); // 删除完里面所有内容
			delFile(file);// 删除空文件夹
		} catch (Exception e) {
			LoggerUtil.error("删除文件夹操作出错");
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path 文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public static void delAllFile(File file) {
		if (file.exists()) {
			if (!file.isDirectory()) {
				delFile(file);
			}
			String[] tempList = file.list();
			if (NullUtil.isNotNull(tempList)) {
				for (int i = 0; i < tempList.length; i++) {
					File itemFile = new File(file.getPath() + File.separator + tempList[i]);
					if (itemFile.isFile()) {
						delFile(itemFile);
					} else if (itemFile.isDirectory()) {
						delFolder(itemFile);// 再删除空文件夹
					}
				}
			}
		}
	}

	/**
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s 源文件
	 * @param t 复制到的新文件
	 */
	public static void copyFile(final File s, final File t) {
		try (FileInputStream fis = new FileInputStream(s);
				FileOutputStream fos = new FileOutputStream(t);
				FileChannel in = fis.getChannel(); // 得到对应的文件通道
				FileChannel out = fos.getChannel();) {// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			LoggerUtil.error("复制文件失败");
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath 准备拷贝的目录
	 * @param newPath 指定绝对路径的新目录
	 * @return
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			if (NullUtil.isNotNull(file)) {
				for (int i = 0; i < file.length; i++) {
					File temp = new File(oldPath + File.separator + file[i]);
					if (temp.isFile()) {
						copyFile(temp, new File(newPath + File.separator + temp.getName()));
					}
					if (temp.isDirectory()) {// 如果是子文件夹
						copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i]);
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("复制整个文件夹内容操作出错");
		}
	}

	/**
	 * 搜索包含关键字的文件
	 * 
	 * @param folder  文件目录
	 * @param keyWord 关键字
	 */
	public static File[] searchFile(File folder, final String keyWord) {// 递归查找包含关键字的文件
		return folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
			public boolean accept(File file) {// 实现FileFilter类的accept方法
				if (file.getName().contains(keyWord)) {// 目录或文件包含关键字
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * 合并文件
	 * 
	 * @param outFile
	 * @param files
	 */
	public static void mergeFiles(String outFile, List<String> files) {
		try (FileOutputStream fos = new FileOutputStream(outFile); FileChannel outChannel = fos.getChannel();) {
			for (String file : files) {
				FileInputStream fis = null;
				FileChannel fc = null;
				try {
					fis = new FileInputStream(file);
					fc = fis.getChannel();
					ByteBuffer bb = ByteBuffer.allocate(1024);
					while (fc.read(bb) != -1) {
						bb.flip();
						outChannel.write(bb);
						bb.clear();
					}
				} finally {
					if (fc != null)
						fc.close();
					if (fis != null)
						fis.close();
				}
			}
		} catch (IOException e) {
			LoggerUtil.error("合并附件异常:" + e);
		}
	}

	// 处理/\//\\
	public static String separator(String path) {
		if (path != null) {
			if (path.startsWith("http://")) {
				return "http://" + path.replace("http://", "").replace("\\", "/").replace("//", "/").replace("\\/", "/")
						.replace("/\\", "/");
			} else if (path.startsWith("https://")) {
				return "https://" + path.replace("https://", "").replace("\\", "/").replace("//", "/")
						.replace("\\/", "/").replace("/\\", "/");
			}
			return path.replace("\\", "/").replace("//", "/").replace("\\/", "/").replace("/\\", "/");
		}
		return path;
	}

	/**
	 * 文件路径%20转换空格
	 */
	public static String decode(String path) {
		if (path != null) {
			return path.replace("%20", " ");
		}
		return path;
	}

	/**
	 * 获取文件名称
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年5月24日 上午10:17:06
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (NullUtil.isNull(filePath) || filePath.indexOf('.') == -1)
			return null;
		if (filePath.indexOf('/') == -1) {
			return filePath.substring(0, filePath.lastIndexOf('.'));
		}
		return filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
	}

	/**
	 * 获取后缀名，带“.”
	 */
	public static String getSuffix(String filePath) {
		if (NullUtil.isNull(filePath) || filePath.indexOf(".") == -1)
			return null;
		return filePath.substring(filePath.lastIndexOf("."), filePath.length());
	}

	/**
	 * 获取后缀名，不带“.”
	 */
	public static String getNSuffix(String filePath) {
		if (NullUtil.isNull(filePath) || filePath.indexOf(".") == -1)
			return null;
		return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
	}

	/**
	 * 获取文件的mimetype
	 */
	public static String getFileType(File file) {
		try {
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
			return MimeUtil.getMimeTypes(file).toString();
		} catch (MimeException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取文件的mimetype
	 */
	public static String getFileType(String fileName) {
		try {
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
			return MimeUtil.getMimeTypes(fileName).toString();
		} catch (MimeException e) {
			e.printStackTrace();
		}
		return "unknown";
	}

	/**
	 * 获取文件的mimetype
	 */
	public static String getFileType(byte[] datas) {
		try {
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
			return MimeUtil.getMimeTypes(datas).toString();
		} catch (MimeException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 截取rootPath后面路径,包括rootPath
	 * 
	 * @param realPath
	 * @param rootPath
	 */
	public static String getFileRealPath(String realPath, String rootPath) {
		// 统一格式
		realPath = separator(File.separator + realPath + File.separator);
		rootPath = separator(File.separator + rootPath + File.separator);
		if (realPath.indexOf(rootPath) != -1) {
			return realPath.substring(realPath.indexOf(rootPath));
		}
		return null;
	}

	/**
	 * 截取rootPath后面路径,包括rootPath,不包含文件名
	 * 
	 * @param realPath
	 * @param rootPath
	 */
	public static String getNFileRealPath(String realPath, String rootPath) {
		// 统一格式
		realPath = separator(realPath);
		rootPath = separator(rootPath);
		if (realPath.indexOf(rootPath) != -1 && realPath.indexOf("/") != -1) {
			return realPath.substring(realPath.indexOf(rootPath), realPath.lastIndexOf("/"));
		}
		return null;
	}

	/**
	 * 创建存储根目录
	 * 
	 * @return
	 */
	public static String getStoragePath() {
		return separator(ConfigureUtil.getString("FILE_STORAGE_ROOT_PATH") + File.separator
				+ DateUtils.getDate(DateUtils.YYYY) + File.separator + DateUtils.getDate(DateUtils.YYYY_MM)
				+ File.separator + DateUtils.getDate(DateUtils.YYYY_MM_DD));
	}

	/**
	 * 获取本地上传根目录
	 */
	/*
	 * public static String getLocalTemporaryCatalog(final String fixedPath){ String
	 * path = separator(ConstantUtil.PROJECT_ROOT_PATH + File.separator +
	 * ConfigureUtil.getString("LOCAL_TEMPORARY_CATALOG") + File.separator +
	 * fixedPath + File.separator); File dir = new File(path); if(!dir.exists())
	 * dir.mkdirs(); return path; }
	 */
	public static String getLocalTemporaryCatalog(final String fixedPath) {
		String path = separator(
				ConfigureUtil.getString("FTP_ROOT_FILE_PATH") + File.separator + fixedPath + File.separator);
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		return path;
	}

	/**
	 * 获取ftp根目录
	 * 
	 * @return
	 */
	public static String getFtpRootPath(final String fixedPath) {
		String path = separator(
				ConfigureUtil.getString("FTP_ROOT_FILE_PATH") + File.separator + fixedPath + File.separator);
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		return path;
	}

	/**
	 * 获取集群根目录
	 * 
	 * @return
	 */
	public static String getClusterRootPath(final String fixedPath) {
		String path = separator(
				ConfigureUtil.getString("CLUSTER_ROOT_FILE_PATH") + File.separator + fixedPath + File.separator);
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		return path;
	}

	/**
	 * 获取http访问url
	 */
	public static String getHttpRootPath(String fixedPath, String fileName) {
		final String httpFile = separator(File.separator + ConfigureUtil.getString("LOCAL_TEMPORARY_CATALOG")
				+ File.separator + fixedPath + File.separator + fileName);
		if (ConfigureUtil.getBoolean("IS_CLUSTER_ENVIRONMENT")) {
			return ConfigureUtil.getString("HTTP_CLUSTER_REQUEST_ADDRESS") + httpFile;
		}
		return ConstantUtil.getHttpRoot() + httpFile;// 后台
	}

	/**
	 * 判断文件是否可以上传
	 * 
	 * @param fileName 文件名或者文件路径，带后缀
	 * @return
	 */
	public static boolean isAllowUpload(String fileName) {
		if (NullUtil.isNotNull(fileName) && fileName.indexOf(".") != -1) {
			final String suffix = getNSuffix(fileName).toLowerCase();
			String[] ftypes = ConfigureUtil.getString("UPLOAD_FILE_TYPES").split(";");
			for (int i = 0; i < ftypes.length; i++) {
				if (NullUtil.isNotNull(ftypes[i]) && suffix.equals(ftypes[i].trim().toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否图片类型
	 * 
	 * @param suffix
	 * @param uploadFileTypes
	 */
	public static boolean checkImageType(String fileName, byte[] datas, File file) {
		boolean result = false;
		if (NullUtil.isNotNull(fileName)) {
			if (fileName.endsWith("jpg") || fileName.endsWith("gif") || fileName.endsWith("png")
					|| fileName.endsWith("bmp") || fileName.endsWith("JPG") || fileName.endsWith("GIF")
					|| fileName.endsWith("PNG") || fileName.endsWith("BMP")) {
				result = true;
			}
		}
		if (!result && NullUtil.isNotNull(fileName)) {
			String fileType = getFileType(fileName);
			result = NullUtil.isNotNull(fileType) && fileType.toLowerCase().indexOf("image") != -1;
		}
		if (!result && NullUtil.isNotNull(datas)) {
			String fileType = getFileType(datas);
			result = NullUtil.isNotNull(fileType) && fileType.toLowerCase().indexOf("image") != -1;
		}
		if (!result && NullUtil.isNotNull(file)) {
			String fileType = getFileType(file);
			result = NullUtil.isNotNull(fileType) && fileType.toLowerCase().indexOf("image") != -1;
		}
		return result;
	}

	/**
	 * 传统的IO方式
	 * 
	 * @param sourceFile
	 */
	public static byte[] getBytes(InputStream is) {
		if (is != null) {
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream(is.available());
					BufferedInputStream in = new BufferedInputStream(is);) {
				int buf_size = 1024 * 3;
				byte[] buffer = new byte[buf_size];
				int len = 0;
				while (-1 != (len = in.read(buffer, 0, buf_size))) {
					bos.write(buffer, 0, len);
				}
				return bos.toByteArray();
			} catch (IOException e) {
				LoggerUtil.error(e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 传统的IO方式
	 * 
	 * @param sourceFile
	 */
	public static byte[] getBytes(File sourceFile) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream((int) sourceFile.length());
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(sourceFile));) {
			int buf_size = 1024 * 3;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage());
		}
		return null;
	}

	/**
	 * NIO方式
	 * 
	 * @param sourceFile
	 */
	public static byte[] getBytesByNIO(File sourceFile) {
		try (FileInputStream fs = new FileInputStream(sourceFile); FileChannel channel = fs.getChannel();) {
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
			}
			return byteBuffer.array();
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 处理大文件
	 * 
	 * @throws IOException
	 */
	public static byte[] getBytesByBig(File sourceFile) throws IOException {
		try (FileChannel fc = new RandomAccessFile(sourceFile, "r").getChannel();) {
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			LoggerUtil.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 将数据数值信息保存到文件，（例如：保存下载信息（文件指针位置））
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年4月11日 上午10:25:36
	 * @param file
	 * @param start 存储的值数组
	 */
	public static void writeInfo(File file, long[] start) {
		createFile(file);
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));) {
			for (int i = 0; i < start.length; i++) {
				dos.writeLong(start[i]);
			}
			dos.close();
		} catch (Exception e) {
			LoggerUtil.error("将数据数值信息保存到文件异常", e);
		}
	}

	/**
	 * 从文件读取数据数值信息(例如：读取保存的下载信息（文件指针位置））
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年4月11日 上午10:25:03
	 * @param tmpFile
	 * @param len     读取多少个值
	 */
	public static long[] readInfo(File tmpFile, int len) {
		try (DataInputStream dis = new DataInputStream(new FileInputStream(tmpFile));) {
			long[] start = new long[len];
			for (int i = 0; i < len; i++) {
				start[i] = dis.readLong();
			}
			return start;
		} catch (Exception e) {
			LoggerUtil.error("从文件读取数据数值信息异常", e);
		}
		return null;
	}

	/**
	 * 上传附件到指定服务器
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年4月24日 下午5:04:02
	 * @param httpUrl 服务器url
	 * @param fileUrl 文件
	 * @param params  需要发送的参数map集
	 * @return
	 */
	public static String upload(String httpUrl, File file, Map<String, String> params) {
		HttpURLConnection conn = null;
		OutputStream out = null;
		DataInputStream in = null;
		try {
			String BOUNDARY = "--------------" + RandomUtil.getRandomLowerString(20); // 定义数据分隔线
			URL url = new URL(httpUrl);
			conn = (HttpURLConnection) url.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("User-agent", "Mozilla/4.0 (compatible; MSIE 6.0;
			// Windows NT 5.1; SV1)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			out = new DataOutputStream(conn.getOutputStream());
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线

			StringBuilder sb = new StringBuilder();
			////////////// 参数/////////////可以多个
			if (NullUtil.isNotNull(params)) {
				Iterator<String> its = params.keySet().iterator();
				while (its.hasNext()) {
					String key = its.next();
					sb = sb.append("--");
					sb = sb.append(BOUNDARY);
					sb = sb.append("\r\n");
					sb = sb.append("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n");// 参数名
					sb = sb.append(params.get(key));// 参数值
					sb = sb.append("\r\n");
				}
			}
			///////////// 文件//////////////可以多个
			// sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			String onlyName = ConfigureUtil.getString("FILE_UPLOAD_ONLY_NAME");
			sb.append("Content-Disposition: form-data; name=\"" + (onlyName == null ? "file-server-files" : onlyName)
					+ "\"; filename=\"" + file.getPath() + "\"\r\n");
			sb.append("Content-Type:" + getFileType(file) + "\r\n\r\n");// text/plain
			byte[] data = sb.toString().getBytes();
			out.write(data);
			in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			//////////// 结束请求///////////////
			out.write(end_data);
			StringBuffer sBuffer = new StringBuffer();
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sBuffer.append(line);
			}
			return sBuffer.toString();
		} catch (Exception e) {
			LoggerUtil.error("发送POST请求出现异常", e);
		} finally {
			try {
				if (conn != null) {
					conn.disconnect();
				}
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
			}
		}
		return null;
	}

	public static String upload(String httpUrl, String fileUrl, Map<String, String> params) {
		return upload(httpUrl, new File(fileUrl), params);
	}

	public static String upload(String httpUrl, String fileUrl) {
		return upload(httpUrl, new File(fileUrl), null);
	}

	public static String upload(String httpUrl, File file) {
		return upload(httpUrl, file, null);
	}

	/**
	 * 下载http文件
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年4月24日 下午5:51:54
	 * @param httpFileUrl  http文件路径
	 * @param downloadFile 下载文件
	 * @return
	 */
	public static boolean downloadHttpFile(String httpFileUrl, File downloadFile) {
		BufferedInputStream bis = null;
		HttpURLConnection httpURLConnection = null;
		try (OutputStream out = new FileOutputStream(downloadFile);) {
			// 统一资源
			URL url = new URL(httpFileUrl);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			httpURLConnection = (HttpURLConnection) urlConnection;
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("GET");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();
			// 文件大小
			// int fileLength = httpURLConnection.getContentLength();
			// 文件名
			// String filePathUrl = httpURLConnection.getURL().getFile();
			bis = new BufferedInputStream(httpURLConnection.getInputStream());
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = bis.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			return true;
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage());
		} finally {
			try {
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
			}
		}
		return false;
	}

}
