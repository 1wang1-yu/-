package Brank;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.sun.org.apache.regexp.internal.RE;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static java.lang.Class.forName;
 class DBUtil {
    // 1. 定义静态数据源变量，整个程序只有一份
    private static DataSource dataSource;

    // 2. 静态代码块：类加载时执行一次，用于初始化连接池
    static {
        try {
            Properties prop = new Properties();
            // 加载配置文件是个更好的做法，这里先演示硬编码，稍后我会告诉你怎么优化
            prop.setProperty("driverClassName", "com.microsoft.sqlserver.Brank.jdbc.SQLServerDriver");
            prop.setProperty("url", "Brank.jdbc:sqlserver://localhost;database=fuxi");
            prop.setProperty("username", "sa");
            prop.setProperty("password", "123456");

            // 创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(prop);
            System.out.println("数据库连接池初始化成功！");
        } catch (Exception e) {
            throw new RuntimeException("数据库连接池初始化失败！", e);
        }
    }

    // 3. 提供公共静态方法，供其他类获取连接
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new RuntimeException("连接池未初始化");
        }
        return dataSource.getConnection();
    }

    // (可选) 提供一个关闭资源的方法，方便统一处理
    public static void closeResource(Connection conn, java.sql.Statement stmt, java.sql.ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        try {
            if (conn != null) conn.close(); // 对于连接池，close() 实际上是归还连接到池中
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
public class Remo {
    private Integer StudentID       ;
    private String Name            ;
    private Integer Age             ;
    private String Gender          ;
    private String ClassName       ;
    private Integer Score           ;

    public Remo() {
    }
    public Remo(Integer studentID, String name, Integer age, String gender, String className, Integer score) {
        this.StudentID = studentID;
        this.Name = name;
        this.Age = age;
        this.Gender = gender;
        this.ClassName = className;
        this.Score = score;
    }

    public Integer getStudentID() {
        return StudentID;
    }

    public void setStudentID(Integer studentID) {
        StudentID = studentID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public Integer getScore() {
        return Score;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    @Override
    public String toString() {
        return "Remo{" +
                "Score=" + Score +
                ", ClassName='" + ClassName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Age=" + Age +
                ", Name='" + Name + '\'' +
                ", StudentID=" + StudentID +
                '}';
    }

    public boolean update(Remo stu) {
        /// /进行数据库配置
        boolean i;
        try {
            Connection conn=DBUtil.getConnection();
            /// 定义sql语句
            String sql = "update Students set Name=? ,Age=? ,Gender=?, ClassName=?, Score=? where StudentID=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
//            Remo stu = new Remo();//创建一个Remo的实体类进行调用set方法
            /// 修改参数
            pstm.setInt(1, stu.getStudentID());
            pstm.setString(1, stu.getName());
            pstm.setInt(2, stu.getAge());
            pstm.setString(3, stu.getGender());
            pstm.setString(4, stu.getClassName());
            pstm.setInt(5, stu.getScore());
            pstm.setInt(6, stu.getStudentID());

            int count = pstm.executeUpdate();
            System.out.println(count > 0);
            i = count > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    //注册驱动名
    Remo stu=null;
    List<Remo> remo= new ArrayList<>();//创建一个名为remo的集合，专门用来存放Remo类型的数据
    public void  select(Remo stu)throws  Exception{
        Connection con=DBUtil.getConnection();
        String sql="select *from Students";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs= pstm.executeQuery();
        while(rs.next()){
            int id =    rs.getInt("StudentID");
            String name = rs.getString("Name");
            int age = rs.getInt("Age");
            String gender=rs.getString("Gender");
            String classname=rs.getString("ClassName");
            int Score=rs.getInt("Score");
            stu=new Remo();
            stu.setAge(id);
            stu.setGender(gender);
            stu.setName(name);
            stu.setClassName(classname);
            stu.setScore(Score);
            remo.add(stu);
        }
        System.out.println(remo);

    }
    public boolean delete(Integer StudentID)throws  Exception{
        Connection conn=DBUtil.getConnection();
        String sql="delete from Students where StudentID=?";
        PreparedStatement pstm=conn.prepareStatement(sql);
        pstm.setInt(1,StudentID);
        int count =pstm.executeUpdate();
        boolean i=count>0;
       return  i;

    }
    public static void main(String[] args) throws Exception{
        Remo stu = new Remo(1, "王五", 20, "男", "2026级软件工程1班", 98);
        List<Remo>student= Arrays.asList(
                new Remo(2, "张三", 22, "男", "计算机1班", 85),
                new Remo(3, "李四", 21, "女", "计算机2班", 92),
                new Remo(4, "赵六", 23, "男", "软件1班", 78)
        );
        for(Remo s:student){
            stu.update(s);
        }//批量进行更新
        stu.setStudentID(5);
        stu.setName("王六");
        stu.setAge(20);
        stu.setGender("男");
        stu.setClassName("2026级软件工程1班");
        stu.setScore(98);
        stu.select(stu);
        Integer j = 7;
        boolean z=stu.delete(j);
        System.out.println("数据库删除成功is"+z);
        boolean result = stu.update(stu);
        System.out.println("操作结果：" + result);
    }

}