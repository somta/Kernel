package com.matecoder.core.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<T> queryByPageList(Integer pageNum, Integer pageSize, Object param){
        Page<T> page = PageHelper.startPage(pageNum, pageSize);
        List<T> list = getMapper().queryByList(param);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
}
