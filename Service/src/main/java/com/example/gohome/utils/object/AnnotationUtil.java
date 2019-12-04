package com.example.gohome.utils.object;

import com.example.gohome.utils.ConfigureUtil;
import com.example.gohome.utils.ConstantUtil;
import com.example.gohome.utils.FileUtil;
import com.example.gohome.utils.NullUtil;
import com.example.gohome.utils.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Transient;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class AnnotationUtil {
	
	private static Logger LoggerUtil = LoggerFactory.getLogger("AnnotationUtil");

	private static List<String> tree_entity_names = null;
	private static List<String> all_entity_names = null;
	private static Map<String, Class<?>> entity_classes_map = null;
	public static Map<String, List<String[]>> fk_entity_map = null;

	static {
		getAllEntityNames();
		getTreeClasses();// 树形数据对象
//		EntityUtil.getFkEntityNames();// 被引用的数据关系(对象：引用对象，引用的字段)
	}

	/**
	 * 获取类class
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:08:53
	 * @param classUrl 类路径
	 * @return
	 */
	public static Class<?> getClasses(String className) {
		if (entity_classes_map.containsKey(className)) {
			return entity_classes_map.get(className);
		}
		return null;
	}

	/**
	 * 获取class
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年8月26日 下午12:17:04
	 * @param classUrl
	 * @return
	 */
	public static Class<?> getClass(String classUrl) {
		if (NullUtil.isNotNull(classUrl)) {
			try {
				return Class.forName(classUrl);
			} catch (ClassNotFoundException e) {
			}
		}
		return null;
	}

	public static List<String> getAllEntityNames() {
		if (NullUtil.isNull(all_entity_names)) {
			all_entity_names = new ArrayList<String>(0);
			entity_classes_map = new HashMap<String, Class<?>>();
			String packageName = ConfigureUtil.getString("ALL_MODEL_PACKAGE");
			String[] packages = packageName.split(";");
			for (String pname : packages) {
				try {
					File modelFile = new File(ConstantUtil.CLASSES_ROOT_PATH + pname.replace(".", File.separator));
					File[] classFiles = FileUtil.searchFile(modelFile, ".class");
					if (NullUtil.isNotNull(classFiles)) {
						for (File classFile : classFiles) {
							String className = classFile.getName().substring(0, classFile.getName().lastIndexOf("."));
							all_entity_names.add(className);
							entity_classes_map.put(className, Class.forName(pname + "." + className));
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return all_entity_names;
	}

	/**
	 * 实例化对象
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:09:17
	 * @param classUrl 类路径
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T instance(String classUrl) {
		try {
			return (T) getClasses(classUrl).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 查找字段中的注解
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:20:46
	 * @param classes
	 * @param annClass
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean existsFieldAnnotation(Class<?> classes, Class annClass) {
		List<Field> fieldList = getAllFields(classes);
		if (NullUtil.isNotNull(fieldList)) {
			for (Field field : fieldList) {
				Object object = field.getAnnotation(annClass);
				if (object != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 查找字段中的注解
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:20:46
	 * @param classes
	 * @param annClass
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean existsFieldAnnotation(Class<?> classes, String fieldName, Class annClass) {
		List<Field> fieldList = getAllFields(classes);
		if (NullUtil.isNotNull(fieldList)) {
			for (Field field : fieldList) {
				if (fieldName.equals(field.getName())) {
					Object object = field.getAnnotation(annClass);
					if (object != null) {
						return true;
					}
					break;
				}
			}
		}
		return false;
	}

	/**
	 * 查找方法中的注解
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:20:46
	 * @param classes
	 * @param annClass
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean existsMethodAnnotation(Class<?> classes, Class annClass) {
		List<Method> methodList = getAllMethods(classes);
		if (NullUtil.isNotNull(methodList)) {
			for (Method Method : methodList) {
				Object object = Method.getAnnotation(annClass);
				if (object != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 查找方法中的注解
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:20:46
	 * @param classes
	 * @param annClass
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean existsMethodAnnotation(Class<?> classes, String methodName, Class annClass) {
		List<Method> methodList = getAllMethods(classes);
		if (NullUtil.isNotNull(methodList)) {
			for (Method method : methodList) {
				if (methodName.equals(method.getName())) {
					Object object = method.getAnnotation(annClass);
					if (object != null) {
						return true;
					}
					break;
				}
			}
		}
		return false;
	}

	/**
	 * 获取字段的所有注解
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:24:18
	 * @param classes
	 * @return
	 */
	public static Map<String, Annotation> getFieldAnnotations(Class<?> classes) {
		List<Field> fieldList = getAllFields(classes);
		if (NullUtil.isNotNull(fieldList)) {
			Map<String, Annotation> annotationMap = new LinkedHashMap<String, Annotation>();
			for (Field field : fieldList) {
				Annotation[] annotations = field.getAnnotations();
				for (int i = 0; i < annotations.length; i++) {
					annotationMap.put(field.getName(), annotations[i]);
				}
			}
			return annotationMap;
		}
		return null;
	}

	/**
	 * 获取方法的所有注解
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:24:18
	 * @param classes
	 * @return
	 */
	public static Map<String, Annotation> getMethodAnnotations(Class<?> classes) {
		List<Method> methodList = getAllMethods(classes);
		if (NullUtil.isNotNull(methodList)) {
			Map<String, Annotation> annotationMap = new LinkedHashMap<String, Annotation>();
			for (Method method : methodList) {
				Annotation[] annotations = method.getAnnotations();
				for (int i = 0; i < annotations.length; i++) {
					annotationMap.put(method.getName(), annotations[i]);
				}
			}
			return annotationMap;
		}
		return null;
	}

	/**
	 * 获取当前类所有方法
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:38:05
	 * @param classes
	 * @return
	 */
	public static List<Method> getCurrentClassMethods(Class<?> classes) {
		List<Method> methodList = new ArrayList<Method>();
		Method[] methods = classes.getDeclaredMethods();
		if (NullUtil.isNotNull(methods)) {
			for (Method method : methods) {
				methodList.add(method);
			}
		}
		return methodList;
	}

	/**
	 * 获取当前类和父类所有方法
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年3月18日 下午12:38:13
	 * @param classes
	 * @return
	 */
	public static List<Method> getAllMethods(Class<?> classes) {
		List<Method> methodList = new ArrayList<Method>();
		Method[] methods = classes.getDeclaredMethods();
		if (NullUtil.isNotNull(methods)) {
			for (Method method : methods) {
				methodList.add(method);
			}
		}
		if (classes != Object.class && classes.getSuperclass() != Object.class) {
			methods = classes.getSuperclass().getDeclaredMethods();
			if (NullUtil.isNotNull(methods)) {
				for (Method method : methods) {
					methodList.add(method);
				}
			}
		}
		return methodList;
	}

	/**
	 * 获取当前类所有字段
	 */
	public static List<Field> getCurrentClassFields(Class<?> classes) {
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = classes.getDeclaredFields();
		if (NullUtil.isNotNull(fields)) {
			for (Field field : fields) {
				if ("serialVersionUID".equals(field.getName()))
					continue;
				fieldList.add(field);
			}
		}
		return fieldList;
	}

	/**
	 * 获取当前类所有字段（不包含Transient）
	 */
	public static List<Field> getCurrentClassFieldsNotTransient(Class<?> classes) {
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = classes.getDeclaredFields();
		if (NullUtil.isNotNull(fields)) {
			for (Field field : fields) {
				if ("serialVersionUID".equals(field.getName()))
					continue;
				Transient ts = field.getAnnotation(Transient.class);
				if (NullUtil.isNotNull(ts))// 过滤Transient
					continue;
				fieldList.add(field);
			}
		}
		return fieldList;
	}

	/**
	 * 获取当前类和父类所有字段（不包含Transient）
	 */
	public static List<Field> getAllFieldsNotTransient(Class<?> classes) {
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fields = classes.getDeclaredFields();
		if (NullUtil.isNotNull(fields)) {
			for (Field field : fields) {
				if ("serialVersionUID".equals(field.getName()))
					continue;
				Transient ts = field.getAnnotation(Transient.class);
				if (NullUtil.isNotNull(ts))// 过滤Transient
					continue;
				fieldList.add(field);
			}
		}
		if (classes != Object.class && classes.getSuperclass() != Object.class) {
			fields = classes.getSuperclass().getDeclaredFields();
			if (NullUtil.isNotNull(fields)) {
				for (Field field : fields) {
					if ("serialVersionUID".equals(field.getName()))
						continue;
					Transient ts = field.getAnnotation(Transient.class);
					if (NullUtil.isNotNull(ts))// 过滤Transient
						continue;
					fieldList.add(field);
				}
			}
		}
		return fieldList;
	}

	/**
	 * 获取当前类和父类所有字段
	 */
	public static List<Field> getAllFields(Class<?> classes) {
		List<Field> fieldList = new ArrayList<Field>();
		if (classes != null) {
			Field[] fields = classes.getDeclaredFields();
			if (NullUtil.isNotNull(fields)) {
				for (Field field : fields) {
					if (Modifier.isStatic(field.getModifiers())) {// 跳过静态变量
						continue;
					}
					fieldList.add(field);
				}
			}
			if (classes != Object.class && classes.getSuperclass() != Object.class) {
				fields = classes.getSuperclass().getDeclaredFields();
				if (NullUtil.isNotNull(fields)) {
					for (Field field : fields) {
						if (Modifier.isStatic(field.getModifiers())) {// 跳过静态变量
							continue;
						}
						fieldList.add(field);
					}
				}
			}
		}
		return fieldList;
	}

	/**
	 * 利用反射设置指定对象的指定属性为指定的值
	 * 
	 * @param obj        目标对象
	 * @param fieldName  目标属性
	 * @param fieldValue 目标值
	 */
	public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
		try {
			Field field = getField(obj, fieldName);
			if (field != null) {
				String type = field.getType().toString();
				field.setAccessible(true);
				if (type.endsWith("String")) {
					field.set(obj, fieldValue);
				} else if (type.endsWith("long") || type.endsWith("Double")) {
					field.set(obj, Long.parseLong(fieldValue.toString()));
				} else if (type.endsWith("int") || type.endsWith("Integer")) {
					field.set(obj, Integer.parseInt(fieldValue.toString()));
				} else if (type.endsWith("double") || type.endsWith("Double")) {
					field.set(obj, Double.parseDouble(fieldValue.toString()));
				} else if (type.endsWith("Date")) {
					field.set(obj, DateUtils.isDate(fieldValue.toString()));
				} else {
					field.set(obj, fieldValue);
				}
			}
		} catch (Exception e) {
			LoggerUtil.warn("setFieldValue error[" + obj.getClass().getSimpleName() + "." + fieldName
					+ ConstantUtil.SPLITE + fieldValue + "]:" + e.getMessage());
		}
	}

	public static void setFieldValue(Object obj, Field field, Object fieldValue) {
		if (NullUtil.isNotNull(fieldValue)) {
			try {
				if (field != null) {
					String type = field.getType().toString();
					field.setAccessible(true);
					if (type.endsWith("String")) {
						field.set(obj, fieldValue);
					} else if (type.endsWith("long") || type.endsWith("Double")) {
						field.set(obj, Long.parseLong(fieldValue.toString()));
					} else if (type.endsWith("int") || type.endsWith("Integer")) {
						field.set(obj, Integer.parseInt(fieldValue.toString()));
					} else if (type.endsWith("double") || type.endsWith("Double")) {
						field.set(obj, Double.parseDouble(fieldValue.toString()));
					} else if (type.endsWith("Date")) {
						field.set(obj, DateUtils.isDate(fieldValue.toString()));
					}
				}
			} catch (Exception e) {
				LoggerUtil.warn("setFieldValue error[" + obj.getClass().getSimpleName() + "." + field.getName()
						+ ConstantUtil.SPLITE + fieldValue + "]:" + e.getMessage());
			}
		}
	}

	/**
	 * 利用反射获取指定对象里面的指定属性
	 * 
	 * @param obj       目标对象
	 * @param fieldName 目标属性
	 * @return 目标字段
	 */
	public static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
			}
		}
		return field;
	}

	/**
	 * 获取对象字段
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2018年3月15日 下午5:41:42
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		Field field = null;
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
			}
		}
		return field;
	}

	/**
	 * 利用反射获取指定对象的指定属性的值
	 * 
	 * @param obj       目标对象
	 * @param fieldName 目标属性
	 * @return 目标属性的值
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Object result = null;
		Field field = getField(obj, fieldName);
		if (field != null) {
			field.setAccessible(true);// setAccessible(true)的作用就是让我们在用反射时访问私有变量
			try {
				result = field.get(obj);// 方法返回指定对象上由此Field表示的字段的值。如果该对象具有原始类型，则该值将自动包装在对象中。
			} catch (IllegalArgumentException e) {
				LoggerUtil.error(e.getMessage());
			} catch (IllegalAccessException e) {
				LoggerUtil.error(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 获取对象结构是树型结构的对象名
	 * 
	 * @author chencaihui
	 * @datetime 创建时间：2017年7月15日 下午4:31:58
	 */
	public static List<String> getTreeClasses() {
		if (NullUtil.isNull(tree_entity_names)) {
			tree_entity_names = new ArrayList<String>(0);
			List<String> entityNames = AnnotationUtil.getAllEntityNames();
			if (NullUtil.isNotNull(entityNames)) {
				for (String className : entityNames) {
					List<Field> fields = AnnotationUtil.getAllFields(AnnotationUtil.getClasses(className));
					boolean isId = false, isParentId = false, isName = false;
					for (Field field : fields) {
						if ("id".equalsIgnoreCase(field.getName())) {
							isId = true;
						} else if ("parentId".equalsIgnoreCase(field.getName())) {
							isParentId = true;
						} else if ("name".equalsIgnoreCase(field.getName())) {
							isName = true;
						}
						if (isId && isParentId && isName) {
							tree_entity_names.add(className);
							break;
						}
					}
				}
			}
		}
		return tree_entity_names;
	}
}