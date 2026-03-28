package Brank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/// /////////在这里connection statement,这些都是jdbc提供的api,我们在这里有许多api需要去学习
public class connection {
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
        String sql ="update Students set Score=Score-50 where StudentID=1";
        String sql2 ="update Students set Score=Score-50 where StudentID=3";
        //获取执行sql的对象
        Statement stmt = conn.createStatement();
        //执行sql
        //执行事务
        try {
            //执行事务
           // conn.setAutoCommit(false);//默认的是自动提交，而在其中加入false则为手动提交
            //手动提交更为安全

            int count = stmt.executeUpdate(sql);
            //处理结果
            System.out.println(count);
            int i = 3/0;
            int count2 = stmt.executeUpdate(sql2);
            //处理结果
            System.out.println(count);
            //提交事务
           // conn.commit();
        } catch (SQLException e) {
           // conn.rollback();
            throw new RuntimeException(e);
            //if右exception则，回滚事务
            //所谓回滚就是回到事务开始也就是第一步，setautocommit的状态

        }
/// ///正因为进行了事务操作才实现了，一旦有异常，两个操作均不会执行，否则将会发生一个执行一个不执行的情况
        stmt.close();
        conn.commit();
        conn.close();
    }

}
