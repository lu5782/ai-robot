package com.cyp.mapper;

import static com.cyp.mapper.UserInfoDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.cyp.po.UserInfo;
import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

@Mapper
public interface UserInfoMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<UserInfo> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserInfoResult")
    UserInfo selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserInfoResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone_num", property="phoneNum", jdbcType=JdbcType.INTEGER),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="sex", property="sex", jdbcType=JdbcType.INTEGER),
        @Result(column="is_deleted", property="isDeleted", jdbcType=JdbcType.INTEGER),
        @Result(column="created_by", property="createdBy", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_date", property="createdDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated_by", property="updatedBy", jdbcType=JdbcType.VARCHAR),
        @Result(column="updated_date", property="updatedDate", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserInfo> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(userInfo);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, userInfo);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, userInfo)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(UserInfo record) {
        return insert(SqlBuilder.insert(record)
                .into(userInfo)
                .map(id).toProperty("id")
                .map(username).toProperty("username")
                .map(password).toProperty("password")
                .map(phoneNum).toProperty("phoneNum")
                .map(email).toProperty("email")
                .map(sex).toProperty("sex")
                .map(isDeleted).toProperty("isDeleted")
                .map(createdBy).toProperty("createdBy")
                .map(createdDate).toProperty("createdDate")
                .map(updatedBy).toProperty("updatedBy")
                .map(updatedDate).toProperty("updatedDate")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(UserInfo record) {
        return insert(SqlBuilder.insert(record)
                .into(userInfo)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(username).toPropertyWhenPresent("username", record::getUsername)
                .map(password).toPropertyWhenPresent("password", record::getPassword)
                .map(phoneNum).toPropertyWhenPresent("phoneNum", record::getPhoneNum)
                .map(email).toPropertyWhenPresent("email", record::getEmail)
                .map(sex).toPropertyWhenPresent("sex", record::getSex)
                .map(isDeleted).toPropertyWhenPresent("isDeleted", record::getIsDeleted)
                .map(createdBy).toPropertyWhenPresent("createdBy", record::getCreatedBy)
                .map(createdDate).toPropertyWhenPresent("createdDate", record::getCreatedDate)
                .map(updatedBy).toPropertyWhenPresent("updatedBy", record::getUpdatedBy)
                .map(updatedDate).toPropertyWhenPresent("updatedDate", record::getUpdatedDate)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<UserInfo>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, username, password, phoneNum, email, sex, isDeleted, createdBy, createdDate, updatedBy, updatedDate)
                .from(userInfo);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<UserInfo>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, username, password, phoneNum, email, sex, isDeleted, createdBy, createdDate, updatedBy, updatedDate)
                .from(userInfo);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UserInfo selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, username, password, phoneNum, email, sex, isDeleted, createdBy, createdDate, updatedBy, updatedDate)
                .from(userInfo)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(UserInfo record) {
        return UpdateDSL.updateWithMapper(this::update, userInfo)
                .set(id).equalTo(record::getId)
                .set(username).equalTo(record::getUsername)
                .set(password).equalTo(record::getPassword)
                .set(phoneNum).equalTo(record::getPhoneNum)
                .set(email).equalTo(record::getEmail)
                .set(sex).equalTo(record::getSex)
                .set(isDeleted).equalTo(record::getIsDeleted)
                .set(createdBy).equalTo(record::getCreatedBy)
                .set(createdDate).equalTo(record::getCreatedDate)
                .set(updatedBy).equalTo(record::getUpdatedBy)
                .set(updatedDate).equalTo(record::getUpdatedDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(UserInfo record) {
        return UpdateDSL.updateWithMapper(this::update, userInfo)
                .set(id).equalToWhenPresent(record::getId)
                .set(username).equalToWhenPresent(record::getUsername)
                .set(password).equalToWhenPresent(record::getPassword)
                .set(phoneNum).equalToWhenPresent(record::getPhoneNum)
                .set(email).equalToWhenPresent(record::getEmail)
                .set(sex).equalToWhenPresent(record::getSex)
                .set(isDeleted).equalToWhenPresent(record::getIsDeleted)
                .set(createdBy).equalToWhenPresent(record::getCreatedBy)
                .set(createdDate).equalToWhenPresent(record::getCreatedDate)
                .set(updatedBy).equalToWhenPresent(record::getUpdatedBy)
                .set(updatedDate).equalToWhenPresent(record::getUpdatedDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(UserInfo record) {
        return UpdateDSL.updateWithMapper(this::update, userInfo)
                .set(username).equalTo(record::getUsername)
                .set(password).equalTo(record::getPassword)
                .set(phoneNum).equalTo(record::getPhoneNum)
                .set(email).equalTo(record::getEmail)
                .set(sex).equalTo(record::getSex)
                .set(isDeleted).equalTo(record::getIsDeleted)
                .set(createdBy).equalTo(record::getCreatedBy)
                .set(createdDate).equalTo(record::getCreatedDate)
                .set(updatedBy).equalTo(record::getUpdatedBy)
                .set(updatedDate).equalTo(record::getUpdatedDate)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(UserInfo record) {
        return UpdateDSL.updateWithMapper(this::update, userInfo)
                .set(username).equalToWhenPresent(record::getUsername)
                .set(password).equalToWhenPresent(record::getPassword)
                .set(phoneNum).equalToWhenPresent(record::getPhoneNum)
                .set(email).equalToWhenPresent(record::getEmail)
                .set(sex).equalToWhenPresent(record::getSex)
                .set(isDeleted).equalToWhenPresent(record::getIsDeleted)
                .set(createdBy).equalToWhenPresent(record::getCreatedBy)
                .set(createdDate).equalToWhenPresent(record::getCreatedDate)
                .set(updatedBy).equalToWhenPresent(record::getUpdatedBy)
                .set(updatedDate).equalToWhenPresent(record::getUpdatedDate)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}