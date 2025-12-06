package io.jiangbyte.framework.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// 树形构建器
public class TreeBuilder<T> {
    
    private final Function<T, String> getId;
    private final Function<T, String> getPid;
    private final ChildrenSetter<T> setChildren;
    
    public TreeBuilder(Function<T, String> getId, Function<T, String> getPid, ChildrenSetter<T> setChildren) {
        this.getId = getId;
        this.getPid = getPid;
        this.setChildren = setChildren;
    }
    
    // 构建树形结构
    public List<T> buildTree(List<T> list) {
        Map<String, T> nodeMap = new HashMap<>();
        
        // 第一遍遍历：建立映射
        for (T node : list) {
            String id = getId.apply(node);
            if (id != null && !id.isEmpty()) {
                nodeMap.put(id, node);
            }
        }
        
        // 第二遍遍历：构建树
        List<T> roots = new ArrayList<>();
        for (T node : list) {
            String pid = getPid.apply(node);
            String id = getId.apply(node);

            // 判断是否为根节点
            if (pid == null || pid.isEmpty() || "0".equals(pid) || pid.equals(id)) {
                roots.add(node);
                continue;
            }

            if (nodeMap.containsKey(pid)) {
                T parent = nodeMap.get(pid);
                // 获取当前的子节点
                List<T> currentChildren = getChildrenReflect(parent);
                // 添加新子节点
                List<T> newChildren = new ArrayList<>(currentChildren);
                newChildren.add(node);
                // 设置子节点
                setChildren.setChildren(parent, newChildren);
            } else {
                // 找不到父节点，作为根节点
                roots.add(node);
            }
        }
        
        return roots;
    }
    
    // 通过反射获取子节点（通用方法）
    @SuppressWarnings("unchecked")
    private List<T> getChildrenReflect(T node) {
        try {
            Class<?> clazz = node.getClass();
            Field childrenField = null;
            
            // 查找 Children 字段
            for (Field field : clazz.getDeclaredFields()) {
                if ("children".equalsIgnoreCase(field.getName())) {
                    childrenField = field;
                    break;
                }
            }
            
            if (childrenField == null) {
                // 如果没有找到 children 字段，尝试从父类查找
                Class<?> superClass = clazz.getSuperclass();
                while (superClass != null && childrenField == null) {
                    for (Field field : superClass.getDeclaredFields()) {
                        if ("children".equalsIgnoreCase(field.getName())) {
                            childrenField = field;
                            break;
                        }
                    }
                    superClass = superClass.getSuperclass();
                }
            }
            
            if (childrenField != null) {
                childrenField.setAccessible(true);
                Object childrenValue = childrenField.get(node);
                
                if (childrenValue instanceof List) {
                    return (List<T>) childrenValue;
                }
            }
        } catch (IllegalAccessException e) {
            // 反射访问异常，返回空列表
        }
        
        return new ArrayList<>();
    }
    
    // 函数式接口用于设置子节点
    @FunctionalInterface
    public interface ChildrenSetter<T> {
        void setChildren(T parent, List<T> children);
    }
}