package com.cyp.robot.config.db;


public class DataSourceHolder {

    private static final ThreadLocal<DataSourceType> holder = new ThreadLocal<>();

    static void setDataSource(DataSourceType type) {
        holder.set(type);
    }

    static DataSourceType getDataSouce() {
        return holder.get();
    }
}
