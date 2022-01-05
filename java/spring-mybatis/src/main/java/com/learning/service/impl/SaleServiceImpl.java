package com.learning.service.impl;

import com.clearning.entity.po.SalePO;
import com.learning.mapper.SaleMapper;
import com.learning.service.SaleService;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public void batchTest() {
        List<SalePO> records = new ArrayList<>();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            Cursor<SalePO> cursor = sqlSession.getMapper(SaleMapper.class).selectByCursor();
            for (SalePO t : cursor) {
                records.add(t);
                if (records.size() >= 1000) {
                    persist(records, sqlSession);
                    sqlSession.commit();
                    records.clear();
                }
            }
            if (records.size() > 0) {
                persist(records, sqlSession);
                sqlSession.commit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(List<SalePO> records, SqlSession sqlSession) {
        records.forEach(t -> {
            SalePO salePO = new SalePO();
            salePO.setId(t.getId());
            salePO.setCreatedUser("batchTest");
            sqlSession.update("com.learning.mapper.SaleMapper.updateBy", salePO);
        });
    }
}
