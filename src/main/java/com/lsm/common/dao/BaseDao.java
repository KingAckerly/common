package com.lsm.common.dao;


import com.lsm.common.db.DBCommonPO;
import com.lsm.common.db.DBInputData;

import java.util.HashMap;
import java.util.List;

public interface BaseDao {
    //Integer save(DBCommonPO dbCommonPO);

    Integer saveBatch(DBInputData dbInputData);

    //Integer remove(DBCommonPO dbCommonPO);

    //Integer delete(DBCommonPO dbCommonPO);

    Integer deleteBatch(DBInputData dbInputData);

    //Integer update(DBCommonPO dbCommonPO);

    //Integer getCount(DBCommonPO dbCommonPO);

    //HashMap get(DBCommonPO dbCommonPO);

    //List<HashMap> list(DBCommonPO dbCommonPO);
}
