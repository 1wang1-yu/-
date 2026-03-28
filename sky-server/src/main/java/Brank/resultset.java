package Brank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/// /////////在这里connection statement,这些都是jdbc提供的api,我们在这里有许多api需要去学习
public class resultset {
    public static  void main(String[] args)throws Exception{
        // 正确的 SQL Server 驱动类名
        //注册驱动名
        System.out.println("zzzzzzzzzzzzzzzzz");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //获取连接对象
        String url="Brank.jdbc:sqlserver://localhost;database=fuxi";
        String username="sa";
        String password="123456";
        Connection conn= DriverManager.getConnection(url,username,password);
        //定义执行sql的对象
        conn.setAutoCommit(true);
        String sql ="select * from Students";
        //获取执行sql的对象
        Statement stmt = conn.createStatement();
        //执行sql
        ResultSet rs = stmt.executeQuery(sql);
        //处理结果
        while(rs.next()){
            int id=rs.getInt(1);
            String name=rs.getString(2);
            double score=rs.getDouble(3);
            System.out.println(id);
            System.out.println(name);
            System.out.println(score);
        }
        rs.close();
        stmt.close();
        conn.commit();
        conn.close();
    }

}
