package com.example.gohome.utils.object;

import com.example.gohome.utils.NullUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EntityUtil{
	
	/**
	 * 初始化有引用关系的对象关系
	 *@author chencaihui 
	 *@datetime 创建时间：2017年12月6日 下午4:37:53 
	 * @return
	 */
	public static Map<String, List<String[]>> getFkEntityNames(){
		if (NullUtil.isNull(AnnotationUtil.fk_entity_map)){
			AnnotationUtil.fk_entity_map = new HashMap<String, List<String[]>>();
//			List<String[]> fkList = new ArrayList<String[]>(1);
//			fkList.add(new String[]{SysRolePermission.class.getSimpleName(), "permissionId"});
//			AnnotationUtil.fk_entity_map.put(SysPermission.class.getSimpleName(), fkList);
//
//			fkList = new ArrayList<String[]>(2);
//			fkList.add(new String[]{SysRolePermission.class.getSimpleName(), "roleId"});
//			fkList.add(new String[]{SysRoleUser.class.getSimpleName(), "roleId"});
//			AnnotationUtil.fk_entity_map.put(SysRole.class.getSimpleName(), fkList);
//			
//			fkList = new ArrayList<String[]>(2);
//			fkList.add(new String[]{SysRoleUser.class.getSimpleName(), "userId"});
//			fkList.add(new String[]{SysMessageUser.class.getSimpleName(), "userId"});//关系
//			AnnotationUtil.fk_entity_map.put(SysLoginUser.class.getSimpleName(), fkList);
//			
//			fkList = new ArrayList<String[]>(1);
//			fkList.add(new String[]{SysAttachment.class.getSimpleName(), "folderId"});//关系
//			AnnotationUtil.fk_entity_map.put(SysFolder.class.getSimpleName(), fkList);//删除
//			
//			
//			
//			//针对所有
//			fkList = new ArrayList<String[]>(1);
//			fkList.add(new String[]{SysBizFile.class.getSimpleName(), "bizId"});//关系
//			AnnotationUtil.fk_entity_map.put("all-fk", fkList);//所有删除
		}
		return AnnotationUtil.fk_entity_map;
	}
}