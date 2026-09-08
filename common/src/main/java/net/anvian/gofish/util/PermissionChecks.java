package net.anvian.gofish.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class PermissionChecks {
    private static final int OP_LEVEL = 2;

    private PermissionChecks() {}

    public static boolean hasPermission(Object source) {
        if (source == null) {
            return false;
        }

        try {
            for (Method method : source.getClass().getMethods()) {
                if (method.getParameterCount() == 1
                        && method.getParameterTypes()[0] == int.class
                        && method.getReturnType() == boolean.class) {
                    return (boolean) method.invoke(source, OP_LEVEL);
                }
            }

            for (Method sourceMethod : source.getClass().getMethods()) {
                if (sourceMethod.getParameterCount() != 0) {
                    continue;
                }

                for (Method permissionMethod : sourceMethod.getReturnType().getMethods()) {
                    if (permissionMethod.getParameterCount() != 1
                            || permissionMethod.getReturnType() != boolean.class) {
                        continue;
                    }

                    Object permission = permissionCheck(permissionMethod.getParameterTypes()[0]);
                    if (permission == null) {
                        continue;
                    }
                    Object permissions = sourceMethod.invoke(source);
                    if (permissions == null) {
                        continue;
                    }
                    return (boolean) permissionMethod.invoke(permissions, permission);
                }
            }
        } catch (ReflectiveOperationException | RuntimeException | LinkageError ignored) {
            return false;
        }

        return false;
    }

    private static Object permissionCheck(Class<?> permissionType) throws ReflectiveOperationException {
        for (Class<?> implementation : permissionType.getDeclaredClasses()) {
            if (!permissionType.isAssignableFrom(implementation)) {
                continue;
            }

            for (Constructor<?> constructor : implementation.getDeclaredConstructors()) {
                if (constructor.getParameterCount() != 1 || !constructor.getParameterTypes()[0].isEnum()) {
                    continue;
                }

                Object[] levels = constructor.getParameterTypes()[0].getEnumConstants();
                if (levels != null && levels.length > OP_LEVEL) {
                    return constructor.newInstance(levels[OP_LEVEL]);
                }
            }
        }
        return null;
    }
}
