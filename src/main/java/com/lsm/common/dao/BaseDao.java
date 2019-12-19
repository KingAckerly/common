package com.lsm.common.dao;


import com.lsm.common.db.DBCommonPO;

import java.util.HashMap;

public interface BaseDao {
    Integer save(DBCommonPO dbCommonPO);

    Integer remove(DBCommonPO dbCommonPO);

    Integer update(DBCommonPO dbCommonPO);

    HashMap get(DBCommonPO dbCommonPO);
}
