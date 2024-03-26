package com.learning.mybatis.mapper;

import com.learning.domain.entity.po.SalePO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;

public interface SaleMapper {

    /*
     fetchSize = Integer.MIN_VALUE 调用ResultsetRowsStreaming
     fetchSize > Integer.MIN_VALUE and useCursorFetch=true调用ResultsetRowsCursor
    */
    @Select(value = "select * from ent_sale")
    @Options(fetchSize = 200)
    Cursor<SalePO> selectByCursor();

    @Select(value = "select * from ent_sale limit 1,1000")
    List<SalePO> selectList();

    @Update("update ent_sale es set es.created_user = #{createdUser} where es.id = #{id}")
    void updateBy(SalePO salePO);

    @Insert("insert into ent_sale_1(id,created_user) values(#{id},#{createdUser})")
    void insertOne(SalePO salePO);
}
