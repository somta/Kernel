package net.somta.core.utils;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class CommonUtil {


  @SuppressWarnings("rawtypes")
  public static boolean isNullOrEmpty(Object obj) {
	    if (obj == null) {
	      return true;
	    }

	    if (obj instanceof CharSequence) {
	      return ((CharSequence) obj).length() == 0;
	    }

	    if (obj instanceof Collection) {
	      return ((Collection) obj).isEmpty();
	    }

	    if (obj instanceof Map) {
	      return ((Map<?, ?>) obj).isEmpty();
	    }

	    if (obj instanceof Object[]) {
	      Object[] object = (Object[]) obj;
	      boolean empty = true;
	      for (Object anObject : object) {
	        if (!isNullOrEmpty(anObject)) {
	          empty = false;
	          break;
	        }
	      }
	      return empty;
	    }
	    return false;
  }

  public static String getUUID(){
  	return UUID.randomUUID().toString().replaceAll("\\-", "");
  }

}
