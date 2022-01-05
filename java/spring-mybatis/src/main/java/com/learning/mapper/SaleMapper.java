package com.learning.mapper;

import com.clearning.entity.po.SalePO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

public interface SaleMapper {

    @Select(value = "select * from ent_sale")
    @Options(fetchSize = Integer.MIN_VALUE, resultSetType = ResultSetType.SCROLL_INSENSITIVE)
    Cursor<SalePO> selectByCursor();

    @Update("update ent_sale es set es.created_user = #{createdUser} where es.id = #{id}")
    void updateBy(SalePO salePO);

    @Insert("insert into ent_sale_1(id,created_user) values(#{id},#{createdUser})")
    void insertOne(SalePO salePO);
}
