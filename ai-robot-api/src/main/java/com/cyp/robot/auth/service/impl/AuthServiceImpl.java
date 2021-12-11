package com.cyp.robot.auth.service.impl;

import com.cyp.mapper.UserInfoDynamicSqlSupport;
import com.cyp.mapper.UserInfoMapper;
import com.cyp.robot.auth.service.AuthService;
import com.cyp.robot.api.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.cyp.po.UserInfo;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public Boolean insert(UserInfo userInfo) {
        Boolean isExist = checkIsExist(userInfo);
        if (isExist) return false;
        userInfo.setIsDeleted(Constants.IS_DELETED_1);
        userInfo.setCreateBy(Constants.OPT_BY);
        userInfo.setUpdateBy(Constants.OPT_BY);
        userInfoMapper.insertSelective(userInfo);
        return true;
    }

    @Override
    public Boolean login(UserInfo userInfo) {
        return checkIsExist(userInfo);
    }

    private Boolean checkIsExist(UserInfo userInfo) {

//        SelectStatementProvider selectStatementProvider = new SelectStatementProvider() {
//            @Override
//            public Map<String, Object> getParameters() {
//
//                SqlColumn<String> stringSqlColumn = UserInfoDynamicSqlSupport.username;
//                return null;
//            }
//
//            @Override
//            public String getSelectStatement() {
//                return null;
//            }
//        };
//        List<UserInfo> userInfos = mapper.selectMany(selectStatementProvider);


        List<UserInfo> execute = userInfoMapper.selectByExample()
                .where(UserInfoDynamicSqlSupport.username, isEqualTo(userInfo.getUsername()))
                .and(UserInfoDynamicSqlSupport.password, isEqualTo(userInfo.getPassword()))
                .build().execute();
        log.info("查询结果={}条", execute.size());
        return execute.size() > 0;
    }
}
