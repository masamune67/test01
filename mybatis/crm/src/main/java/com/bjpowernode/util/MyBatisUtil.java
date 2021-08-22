package com.bjpowernode.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private  static  SqlSessionFactory factory = null;
    static {
        String config="mybatis.xml"; // 需要和你的项目中的文件名一样
        try {
            InputStream in = Resources.getResourceAsStream(config);
            //创建SqlSessionFactory对象，使用SqlSessionFactoryBuild
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ThreadLocal<SqlSession> t = new ThreadLocal<>();

    //获取SqlSession的方法
    public static SqlSession getSqlSession() {
        SqlSession sqlSession  = t.get();
        if( sqlSession == null){
            sqlSession = factory.openSession();// 非自动提交事务
            t.set(sqlSession);
        }
        return sqlSession;
    }

    public static void myClose(SqlSession session){
        if (session != null){
            session.close();
            t.remove();
        }
    }

}
