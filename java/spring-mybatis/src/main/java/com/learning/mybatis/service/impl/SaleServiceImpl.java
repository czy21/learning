package com.learning.mybatis.service.impl;

import com.learning.domain.entity.po.SalePO;
import com.learning.mybatis.mapper.SaleMapper;
import com.learning.mybatis.service.SaleService;
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
    @Autowired
    SaleMapper saleMapper;

    @Override
    public void batchTest() {
        List<SalePO> records = new ArrayList<>();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            Cursor<SalePO> cursor = sqlSession.selectCursor("com.learning.mapper.SaleMapper.selectByCursor");
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

    @Override
    public List<SalePO> findPage() {
        return saleMapper.selectList();
    }

    private void persist(List<SalePO> records, SqlSession sqlSession) {
        records.forEach(t -> {
            SalePO salePO = new SalePO();
            salePO.setId(t.getId());
            salePO.setCreateUser("batchTest");
            sqlSession.update("com.learning.mapper.SaleMapper.insertOne", salePO);
        });
    }
}
