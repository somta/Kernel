package com.matecoder.core.base;

import com.matecoder.core.protocol.ResponseDataResult;
import com.matecoder.core.protocol.ResponsePaginationDataResult;

import java.util.List;


public abstract class BaseServiceImpl<T> implements IBaseService<T> {

    @Override
    public abstract IBaseMapper getMapper();

    @Override
    public ResponseDataResult<Boolean> add(T t){
        getMapper().add(t);
        return ResponseDataResult.setResponseResult(true);
    }

    @Override
    public ResponseDataResult<Boolean> deleteById(Object id){
        getMapper().deleteById(id);
        return ResponseDataResult.setResponseResult(true);
    }

    @Override
    public ResponseDataResult<Boolean> update(T t){
        getMapper().update(t);
        return ResponseDataResult.setResponseResult(true);
    }

    @Override
    public T queryById(Object id){
        return getMapper().queryById(id);
    }

    @Override
    public List<T> queryByList(Object param){
        return getMapper().queryByList(param);
    }

    @Override
    public Long queryListCount(Object param){
        return getMapper().queryListCount(param);
    }

    @Override
    public ResponsePaginationDataResult<T> queryByPageList(Integer pageNum, Integer pageSize, Object param){
        Long count = getMapper().queryListCount(param);
        if(count > 0){
            List<T> list = getMapper().queryByList(param);
            return ResponsePaginationDataResult.setPaginationDataResult(count,list);
        }
        return ResponsePaginationDataResult.setPaginationDataResult(0L,null);
    }
}
