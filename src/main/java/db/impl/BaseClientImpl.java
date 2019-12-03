package db.impl;

import annotation.Column;
import annotation.Id;
import annotation.Table;
import dao.BaseDao;
import db.BaseClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "BaseClient")
public class BaseClientImpl<T> implements BaseClient<T> {

    //private static Logger logger = LoggerFactory.getLogger(BaseClientImpl.class);

    @Resource
    private BaseDao baseDao;

    private Map<String, Object> buildParams(T t) {
        //获取表名
        if (null == t.getClass().getAnnotation(Table.class)) {
            throw new RuntimeException("Error Input Object! Error @Table Annotation.");
        }
        Map<String, Object> re = new HashMap<String, Object>();
        re.put("TABLE_NAME", t.getClass().getAnnotation(Table.class).value());
        Method[] m = t.getClass().getMethods();
        if (null == m || m.length <= 0) {
            throw new RuntimeException("Error Input Object! No Method.");
        }
        List k = new ArrayList();//存放列名
        List v = new ArrayList();//存放列值
        for (Method i : m) {
            //获取列名和值
            try {
                if (null != i.getAnnotation(Column.class)) {
                    k.add(i.getAnnotation(Column.class).value());
                    v.add(i.invoke(t, null));
                    continue;
                }
                //获取主键
                if (null != i.getAnnotation(Id.class)) {
                    re.put("KEY_ID", i.getAnnotation(Id.class).value());
                    re.put("KEY_VALUE", i.invoke(t, null));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error Input Object! Error Invoke Get Method.", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Error Input Object! Error Invoke Get Method.", e);
            }
        }
        re.put("COLUMNS", k);
        re.put("VALUES", v);
        if (k.size() != v.size()) {
            throw new RuntimeException("Error Input Object! Internal Error.");
        }
        return re;
    }


    public Integer save(T t) {
        Map<String, Object> params = buildParams(t);
        //logger.info("Function Save.Params:" + params);
        return baseDao.save(params);
    }

    public String saveRetPK(T t) {
        return null;
    }

    public Integer saveBatch(List<T> list) {
        return null;
    }

    public List<String> saveBatchRetPK(List<T> list) {
        return null;
    }

    public Integer removeByPK(T t) {
        return null;
    }

    public Integer removeBatchByPK(List<T> list) {
        return null;
    }

    public Integer removeByCondition(T t, String condition) {
        return null;
    }

    public Integer update(T t, String condition) {
        return null;
    }

    public Integer updateBatch(T t) {
        return null;
    }

    public Integer count(T t, String condition) {
        return null;
    }

    public HashMap list(T t, String condition) {
        return null;
    }


}
