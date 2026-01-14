package com.matecoder.core.context;

import java.util.Map;

/**
 *
 * 从header中取出应该相关的信息，并放到ThreadLocal中作为上下文传递
 * @author husong
 **/
public class ApplicationContext {
    private static final ThreadLocal<IdentityContext> identityContextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<AppContext> appContextThreadLocal = new ThreadLocal<>();

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

    public static void putAppContext(){
        AppContext appContext = new AppContext();
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
