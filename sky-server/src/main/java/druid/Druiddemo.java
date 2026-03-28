package druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/// //druid数据库连接池
public class Druiddemo {
    public static  void main(String[] args) throws Exception {
    //导入zjar包
        //定义配置文件
        //加载配置文件对象
        Properties prop=new Properties();

        //获取连接池对象
        DataSource  dataSource=DruidDataSourceFactory.createDataSource(prop);
        /// 获取数据库连接connection
        Connection conn=dataSource.getConnection();
        System.out.println(conn);
    System.out.println(System.getProperty("user.dir"));
    }

}
