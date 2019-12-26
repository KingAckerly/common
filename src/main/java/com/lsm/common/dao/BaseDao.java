package com.lsm.common.dao;


import com.lsm.common.db.DBInputData;

import java.util.HashMap;
import java.util.List;

public interface BaseDao {

    Integer saveBatch(DBInputData dbInputData);

    Integer removeBatch(DBInputData dbInputData);

    Integer deleteBatch(DBInputData dbInputData);

    Integer updateBatch(DBInputData dbInputData);

    Integer getCount(DBInputData dbInputData);

    HashMap get(DBInputData dbInputData);

    List<HashMap> list(DBInputData dbInputData);
}
