package net.somta.core.base;

import net.somta.core.base.result.ResponseDataResult;
import net.somta.core.base.result.ResponsePaginationDataResult;
import net.somta.core.exception.BizException;

import java.util.List;

import static net.somta.core.enums.SystemErrorEnum.*;

public abstract class BaseServiceImpl implements IBaseService {

    public abstract IBaseMapper getMapper();

    public <T> ResponseDataResult add(T t){
            if(getMapper().add(t) > 0){
                throw new BizException(BASE_ADD_ERROR);
        }
        return ResponseDataResult.setResponseResult();
    }

    public ResponseDataResult deleteById(Object id){
        if(getMapper().deleteById(id) > 0){
            throw new BizException(BASE_DELETE_BY_ID_ERROR);
        }
        return ResponseDataResult.setResponseResult();
    }

    public <T> ResponseDataResult update(T t){
        if(getMapper().update(t) > 0){
            throw new BizException(BASE_UPDATE_ERROR);
        }
        return ResponseDataResult.setResponseResult();
    }

    public <T> T queryById(Object id){
        return getMapper().queryById(id);
    }

    public Long queryListCount(Object param){
        return getMapper().queryListCount(param);
    }

    public <T> ResponsePaginationDataResult queryByList(Integer pageNum, Integer pageSize, Object param){
        Long count = getMapper().queryListCount(param);
        if(count > 0){
            List list = getMapper().queryByList(param);
            return ResponsePaginationDataResult.setPaginationDataResult(count,list);
        }
        return ResponsePaginationDataResult.setPaginationDataResult(0L,null);
    }
}
