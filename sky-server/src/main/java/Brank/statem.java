package Brank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/// /////////在这里connection statement,这些都是jdbc提供的api,我们在这里有许多api需要去学习
public class statem {
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
        String sql ="update Students set Score=100 where StudentID=9";
        //获取执行sql的对象
        Statement stmt = conn.createStatement();
        //执行sql
        int count = stmt.executeUpdate(sql);//执行dml语句，受影响的行数
        //对于数据库的增删改都是使用exexuteUpdate 只有查是使用的另一个
        //处理结果
        if(count>0){
            System.out.println("修改成功");
        }
        else {
            System.out.println("修改失败");
        }
        System.out.println(count);
        stmt.close();
        conn.commit();
        conn.close();
    }

}
