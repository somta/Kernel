package net.somta.core.base;

import net.somta.core.base.result.ResponseDataResult;
import net.somta.core.base.result.ResponsePaginationDataResult;
import net.somta.core.exception.BizException;

import java.util.List;

public abstract class BaseServiceImpl implements IBaseService {

    public abstract IBaseMapper getMapper();

    public <T> ResponseDataResult add(T t){
            if(getMapper().add(t) > 0){
                throw new BizException("add.error","新增数据失败");
        }
        return ResponseDataResult.setResponseResult();
    }

    public ResponseDataResult deleteById(Object id){
        if(getMapper().deleteById(id) > 0){
            throw new BizException("delete.error","根据ID删除数据失败");
        }
        return ResponseDataResult.setResponseResult();
    }

    public <T> ResponseDataResult update(T t){
        if(getMapper().update(t) > 0){
            throw new BizException("update.error","修改数据失败");
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
