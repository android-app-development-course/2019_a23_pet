package com.example.gohome.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;

/**
 * 编译
 * 
 * @author chencaihui
 */
public class JavacUtil {

	private static Logger LoggerUtil = LoggerFactory.getLogger("JavacUtil");

	/**
	 * 编译java类
	 * 
	 * @param javaPath
	 */
	public static void javac(File javaFile) {
		try {
			// java编译器
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			// 文件管理器，参数1：diagnosticListener 监听器,监听编译过程中出现的错误
			StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
			// java文件转换到java对象，可以是多个文件
			Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(javaFile);
			// 编译任务,可以编译多个文件
			CompilationTask t = compiler.getTask(null, manager, null, null, null, it);
			// 执行任务
			t.call();
			manager.close();
		} catch (IOException e) {
			LoggerUtil.warn("编译" + javaFile.getName() + "失败:" + e.getMessage());
		}
	}
}