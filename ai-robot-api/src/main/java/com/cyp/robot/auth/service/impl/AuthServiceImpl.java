package com.cyp.robot.auth.service.impl;

import com.cyp.mapper.UserInfoDynamicSqlSupport;
import com.cyp.mapper.UserInfoMapper;
import com.cyp.po.UserInfo;
import com.cyp.robot.auth.service.AuthService;
import com.cyp.robot.api.common.Constants;
import com.cyp.robot.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.cyp.mapper.UserInfoDynamicSqlSupport.*;
import static com.cyp.mapper.UserInfoDynamicSqlSupport.userInfo;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    UserInfoMapper mapper;

    @Override
    public Boolean insert(UserInfo userInfo) {
        Boolean isExist = checkIsExist(userInfo);
        if (isExist) return false;
        String id = SnowFlakeUtils.generateId();
//        userInfo.setId("user_"+id);
        userInfo.setIsDeleted(Constants.IS_DELETED_0);
        userInfo.setCreatedBy(Constants.OPT_BY);
        userInfo.setCreatedDate(new Date());
        userInfo.setUpdatedBy(Constants.OPT_BY);
        userInfo.setUpdatedDate(new Date());
        mapper.insert(userInfo);
        return true;
    }

    @Override
    public Boolean login(UserInfo userInfo) {
        return checkIsExist(userInfo);
    }

    private Boolean checkIsExist(UserInfo userInfo) {

        SelectStatementProvider selectStatementProvider = new SelectStatementProvider() {
            @Override
            public Map<String, Object> getParameters() {

                SqlColumn<String> stringSqlColumn = UserInfoDynamicSqlSupport.username;
                return null;
            }

            @Override
            public String getSelectStatement() {
                return null;
            }
        };
        List<UserInfo> userInfos = mapper.selectMany(selectStatementProvider);



        List<UserInfo> execute = mapper.selectByExample()
                .where(UserInfoDynamicSqlSupport.username, isEqualTo(userInfo.getUsername()))
                .and(UserInfoDynamicSqlSupport.password, isEqualTo(userInfo.getPassword()))
                .build().execute();
        log.info("查询结果={}条", execute.size());
        return execute.size() > 0;
    }
}
