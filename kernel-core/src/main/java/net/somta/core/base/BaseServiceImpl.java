package net.somta.core.base;

import net.somta.core.exception.BizException;
import net.somta.core.protocol.ResponseDataResult;
import net.somta.core.protocol.ResponsePaginationDataResult;

import java.util.List;

import static net.somta.core.enums.SystemErrorEnum.*;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    @Override
    public abstract IBaseMapper getMapper();

    @Override
    public <T> ResponseDataResult add(T t){
        getMapper().add(t);
        return ResponseDataResult.setResponseResult();
    }

    @Override
    public ResponseDataResult deleteById(Object id){
        getMapper().deleteById(id);
        return ResponseDataResult.setResponseResult();
    }

    @Override
    public <T> ResponseDataResult update(T t){
        getMapper().update(t);
        return ResponseDataResult.setResponseResult();
    }

    @Override
    public <T> T queryById(Object id){
        return getMapper().queryById(id);
    }

    @Override
    public <T> List<T> queryByList(Object param){
        List<T> list = getMapper().queryByList(param);
        return list;
    }

    @Override
    public Long queryListCount(Object param){
        return getMapper().queryListCount(param);
    }

    @Override
    public <T> ResponsePaginationDataResult queryByPageList(Integer pageNum, Integer pageSize, Object param){
        Long count = getMapper().queryListCount(param);
        if(count > 0){
            List list = getMapper().queryByList(param);
            return ResponsePaginationDataResult.setPaginationDataResult(count,list);
        }
        return ResponsePaginationDataResult.setPaginationDataResult(0L,null);
    }
}
