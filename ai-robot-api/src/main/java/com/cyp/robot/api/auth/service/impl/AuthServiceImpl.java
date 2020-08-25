package com.cyp.robot.api.auth.service.impl;

import com.cyp.mapper.UserInfoMapper;
import com.cyp.po.UserInfo;
import com.cyp.robot.api.auth.service.AuthService;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    UserInfoMapper mapper;

    @Override
    public Boolean insert(UserInfo userInfo) {
        Boolean isExist = checkUserInfo(userInfo);
        if (isExist) return false;
        userInfo.setIsDeleted(1);
        mapper.insert(userInfo);
        return true;
    }

    @Override
    public Boolean login(UserInfo userInfo) {
        return false;
    }

    private Boolean checkUserInfo(UserInfo userInfo) {
        SelectStatementProvider selectStatementProvider = new SelectStatementProvider() {
            @Override
            public Map<String, Object> getParameters() {
                return null;
            }

            @Override
            public String getSelectStatement() {
                return null;
            }
        };
        List<UserInfo> userInfos = mapper.selectMany(selectStatementProvider);


        return false;
    }
}
