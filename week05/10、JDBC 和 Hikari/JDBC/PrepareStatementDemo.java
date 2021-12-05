package com.yunmu.geek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mfXing
 */
public class PrepareStatementDemo {
    private static String sqlStatement;
    
    public static void main(String[] args) throws SQLException {
        /** 通过工具类获取数据库连接对象 */
        Connection con = JdbcUtil.getConnection();
        /** 通过连接创建数据库执行对象 */
        PreparedStatement ps = null;
        /** 为查询的结果集准备接收对象 */
        ResultSet rs = null;

        /** 增加 */
        sqlStatement = "INSERT INTO test VALUES(?,?,?)";
        ps = con.prepareStatement(sqlStatement);
        ps.setObject(1, "1");
        ps.setObject(2, "test");
        ps.setObject(3, "test");
        System.out.println("插入执行结果:"+update(ps,sqlStatement));

        /** 查询 */
        sqlStatement = "SELECT * FROM test WHERE tno = ?";
        ps = con.prepareStatement(sqlStatement);
        ps.setObject(1, "1");
        qry(ps,rs);

        /** 更新 */
        sqlStatement = "UPDATE test SET tname=? WHERE tno = ?";
        ps = con.prepareStatement(sqlStatement);
        ps.setObject(1, "test1");
        ps.setObject(2, "1");
        System.out.println("更新执行结果:"+update(ps,sqlStatement));

        /** 删除 */
        sqlStatement = "DELETE FROM test WHERE tno = ?";
        ps = con.prepareStatement(sqlStatement);
        ps.setObject(1, "1");
        System.out.println("删除执行结果:"+update(ps,sqlStatement));

        JdbcUtil.closeResource(con, ps, rs);
    }
    /**
     * 查询
     * @param sta
     * @param rs
     * @throws SQLException
     */
    private static void qry(PreparedStatement sta,ResultSet rs) throws SQLException {
        rs = sta.executeQuery();
        while(rs.next()) {
            System.out.println(rs.getObject("tno"));
        }
    }
    /**
     * 增删改
     * @param sta
     * @param sql
     * @return
     * @throws SQLException
     */
    private static int update(PreparedStatement sta,String sql) throws SQLException {
        return sta.executeUpdate();
    }
}
