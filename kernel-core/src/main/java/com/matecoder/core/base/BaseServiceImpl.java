package com.matecoder.core.base;

import com.matecoder.core.base.page.PageParam;
import com.matecoder.core.base.vo.BaseVO;
import com.matecoder.core.context.ApplicationContext;
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
    public ResponseDataResult<Boolean> deleteById(Long id,Long tenantId){
        getMapper().deleteById(new BaseVO(id, tenantId));
        return ResponseDataResult.setResponseResult(true);
    }

    @Override
    public ResponseDataResult<Boolean> logicalDeleteById(Long id,Long tenantId){
        getMapper().logicalDeleteById(new BaseVO(id, tenantId));
        return ResponseDataResult.setResponseResult(true);
    }

    @Override
    public ResponseDataResult<Boolean> update(T t){
        getMapper().update(t);
        return ResponseDataResult.setResponseResult(true);
    }

    @Override
    public T queryById(Long id,Long tenantId){
        return getMapper().queryById(new BaseVO(id, tenantId));
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
            PageParam pageParam = new PageParam();
            pageParam.setPageNum(pageNum);
            pageParam.setPageSize(pageSize);
            List<T> list = getMapper().queryByPageList(param, pageParam.getOffset(), pageSize);
            return ResponsePaginationDataResult.setPaginationDataResult(count,list);
        }
        return ResponsePaginationDataResult.setPaginationDataResult(0L,null);
    }
}
