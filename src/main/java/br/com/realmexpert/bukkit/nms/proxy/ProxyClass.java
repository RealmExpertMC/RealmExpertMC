package br.com.realmexpert.bukkit.nms.proxy;

import br.com.realmexpert.bukkit.nms.utils.ASMUtils;
import br.com.realmexpert.bukkit.nms.utils.ReflectionUtils;
import br.com.realmexpert.bukkit.nms.utils.RemapUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author pyz
 * @date 2019/7/1 12:24 AM
 */
public class ProxyClass {

    public static Class<?> forName(String className) throws ClassNotFoundException {
        return forName(className, true, ReflectionUtils.getCallerClassLoader());
    }

    public static Class<?> forName(String className, boolean initialize, ClassLoader loader) throws ClassNotFoundException {
        try {
            return Class.forName(ASMUtils.toClassName(RemapUtils.map(className.replace('.', '/'))), initialize, loader);
        } catch (NullPointerException e) {
            throw new ClassNotFoundException(className);
        }
    }

    public static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        if (clazz.getName().startsWith("net.minecraft.")) {
            name = RemapUtils.mapMethodName(clazz, name, parameterTypes);
        }
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoClassDefFoundError e) {
            throw new NoSuchMethodException(e.toString());
        }
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        if (clazz.getName().startsWith("net.minecraft.")) {
            name = RemapUtils.mapMethodName(clazz, name, parameterTypes);
        }
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch (NoClassDefFoundError e) {
            throw new NoSuchMethodException(e.toString());
        }
    }

    public static Field getDeclaredField(Class<?> clazz, String name) throws NoSuchFieldException, SecurityException {
        if (clazz.getName().startsWith("net.minecraft.")) {
            name = RemapUtils.mapFieldName(clazz, name);
        }
        return clazz.getDeclaredField(name);
    }

    public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException, SecurityException {
        if (clazz.getName().startsWith("net.minecraft.")) {
            name = RemapUtils.mapFieldName(clazz, name);
        }
        return clazz.getField(name);
    }

    public static String getName(Class<?> clazz) {
        return RemapUtils.inverseMapName(clazz);
    }

    public static String getSimpleName(Class<?> clazz) {
        return RemapUtils.inverseMapSimpleName(clazz);
    }

    public static Method[] getDeclaredMethods(Class<?> inst) {
        try {
            return inst.getDeclaredMethods();
        } catch (NoClassDefFoundError e) {
            return new Method[]{};
        }
    }

    public static String getName(Field field) {
        return RemapUtils.inverseMapFieldName(field.getDeclaringClass(), field.getName());
    }

    public static String getName(Method method) {
        return RemapUtils.inverseMapMethodName(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
    }

    public static Class<?> loadClass(ClassLoader inst, String className) throws ClassNotFoundException {
        if (className.startsWith("net.minecraft.")) {
            className = ASMUtils.toClassName(RemapUtils.map(ASMUtils.toInternalName(className)));
        }
        return inst.loadClass(className);
    }
}
