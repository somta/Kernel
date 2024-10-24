package net.somta.core.context;

import java.util.Map;

/**
 *
 * 从header中取出应该相关的信息，并放到ThreadLocal中作为上下文传递
 * @author husong
 * @date  2022/9/7
 **/
public class ApplicationContext {
    private static ThreadLocal<IdentityContext> identityContextThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<AppContext> appContextThreadLocal = new ThreadLocal<>();

    public static IdentityContext getIdentityContext(){
        return identityContextThreadLocal.get();
    }

    public static void removeIdentityContext(){
        identityContextThreadLocal.remove();
    }

    public static void putIdentityContext(Long userId, Long tenantId, Map<String,String> extend){
        IdentityContext identityContext = new IdentityContext(userId,tenantId,extend);
        identityContextThreadLocal.set(identityContext);
    }

    public static AppContext getAppContext(){
        return appContextThreadLocal.get();
    }

    public static void putAppContext(String grayVersion){
        AppContext appContext = new AppContext(grayVersion);
        appContextThreadLocal.set(appContext);
    }

    public static void removeAppContext(){
        appContextThreadLocal.remove();
    }

    public static void removeApplicationContext(){
        identityContextThreadLocal.remove();
        appContextThreadLocal.remove();
    }
}
