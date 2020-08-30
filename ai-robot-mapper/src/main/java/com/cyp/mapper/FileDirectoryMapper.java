package com.cyp.mapper;

import static com.cyp.mapper.FileDirectoryDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.cyp.po.FileDirectory;
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
public interface FileDirectoryMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<FileDirectory> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("FileDirectoryResult")
    FileDirectory selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="FileDirectoryResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="directory_name", property="directoryName", jdbcType=JdbcType.VARCHAR),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.VARCHAR),
        @Result(column="file_num", property="fileNum", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_by", property="createdBy", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_date", property="createdDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="updated_by", property="updatedBy", jdbcType=JdbcType.VARCHAR),
        @Result(column="updated_date", property="updatedDate", jdbcType=JdbcType.TIMESTAMP)
    })
    List<FileDirectory> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(fileDirectory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, fileDirectory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(String id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, fileDirectory)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(FileDirectory record) {
        return insert(SqlBuilder.insert(record)
                .into(fileDirectory)
                .map(id).toProperty("id")
                .map(directoryName).toProperty("directoryName")
                .map(parentId).toProperty("parentId")
                .map(fileNum).toProperty("fileNum")
                .map(createdBy).toProperty("createdBy")
                .map(createdDate).toProperty("createdDate")
                .map(updatedBy).toProperty("updatedBy")
                .map(updatedDate).toProperty("updatedDate")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(FileDirectory record) {
        return insert(SqlBuilder.insert(record)
                .into(fileDirectory)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(directoryName).toPropertyWhenPresent("directoryName", record::getDirectoryName)
                .map(parentId).toPropertyWhenPresent("parentId", record::getParentId)
                .map(fileNum).toPropertyWhenPresent("fileNum", record::getFileNum)
                .map(createdBy).toPropertyWhenPresent("createdBy", record::getCreatedBy)
                .map(createdDate).toPropertyWhenPresent("createdDate", record::getCreatedDate)
                .map(updatedBy).toPropertyWhenPresent("updatedBy", record::getUpdatedBy)
                .map(updatedDate).toPropertyWhenPresent("updatedDate", record::getUpdatedDate)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<FileDirectory>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, directoryName, parentId, fileNum, createdBy, createdDate, updatedBy, updatedDate)
                .from(fileDirectory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<FileDirectory>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, directoryName, parentId, fileNum, createdBy, createdDate, updatedBy, updatedDate)
                .from(fileDirectory);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default FileDirectory selectByPrimaryKey(String id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, directoryName, parentId, fileNum, createdBy, createdDate, updatedBy, updatedDate)
                .from(fileDirectory)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(FileDirectory record) {
        return UpdateDSL.updateWithMapper(this::update, fileDirectory)
                .set(id).equalTo(record::getId)
                .set(directoryName).equalTo(record::getDirectoryName)
                .set(parentId).equalTo(record::getParentId)
                .set(fileNum).equalTo(record::getFileNum)
                .set(createdBy).equalTo(record::getCreatedBy)
                .set(createdDate).equalTo(record::getCreatedDate)
                .set(updatedBy).equalTo(record::getUpdatedBy)
                .set(updatedDate).equalTo(record::getUpdatedDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(FileDirectory record) {
        return UpdateDSL.updateWithMapper(this::update, fileDirectory)
                .set(id).equalToWhenPresent(record::getId)
                .set(directoryName).equalToWhenPresent(record::getDirectoryName)
                .set(parentId).equalToWhenPresent(record::getParentId)
                .set(fileNum).equalToWhenPresent(record::getFileNum)
                .set(createdBy).equalToWhenPresent(record::getCreatedBy)
                .set(createdDate).equalToWhenPresent(record::getCreatedDate)
                .set(updatedBy).equalToWhenPresent(record::getUpdatedBy)
                .set(updatedDate).equalToWhenPresent(record::getUpdatedDate);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(FileDirectory record) {
        return UpdateDSL.updateWithMapper(this::update, fileDirectory)
                .set(directoryName).equalTo(record::getDirectoryName)
                .set(parentId).equalTo(record::getParentId)
                .set(fileNum).equalTo(record::getFileNum)
                .set(createdBy).equalTo(record::getCreatedBy)
                .set(createdDate).equalTo(record::getCreatedDate)
                .set(updatedBy).equalTo(record::getUpdatedBy)
                .set(updatedDate).equalTo(record::getUpdatedDate)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(FileDirectory record) {
        return UpdateDSL.updateWithMapper(this::update, fileDirectory)
                .set(directoryName).equalToWhenPresent(record::getDirectoryName)
                .set(parentId).equalToWhenPresent(record::getParentId)
                .set(fileNum).equalToWhenPresent(record::getFileNum)
                .set(createdBy).equalToWhenPresent(record::getCreatedBy)
                .set(createdDate).equalToWhenPresent(record::getCreatedDate)
                .set(updatedBy).equalToWhenPresent(record::getUpdatedBy)
                .set(updatedDate).equalToWhenPresent(record::getUpdatedDate)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}