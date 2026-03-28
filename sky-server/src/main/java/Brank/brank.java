package Brank;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.annotations.VisibleForTesting;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/// //学生表的增删改查操作
/// 这里是对学生表的查询操作
@RunWith(Enclosed.class)
public class brank {
    //在实体类中，基本数据类型使用
    private Integer StudentID       ;
    private String Name            ;
    private Integer Age             ;
    private String Gender          ;
    private String ClassName       ;
    private Integer Score           ;

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
        return "brank{" +
                "StudentID=" + StudentID +
                ", Name='" + Name + '\'' +
                ", Age=" + Age +
                ", Gender='" + Gender + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", Score=" + Score +
                '}';
    }

//    public brank(Integer studentID, String name, Integer age, String gender, String className, Integer score) {
//        StudentID = studentID;
//        Name = name;
//        Age = age;
//        Gender = gender;
//        ClassName = className;
//        Score = score;
//    }
    public static class branktest {

        /// sql
        /// 要不要参数
        /// 返回结果如何封装
        @Test
        public void selectall()throws Exception{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //获取连接对象
            String url="Brank.jdbc:sqlserver://localhost;database=fuxi";
            String username="sa";
            String password="123456";
            Properties prop=new Properties();
            prop.setProperty("driverClassName", "com.microsoft.sqlserver.Brank.jdbc.SQLServerDriver");
            prop.setProperty("url", "Brank.jdbc:sqlserver://localhost;database=fuxi");
            prop.setProperty("username", "sa");
            prop.setProperty("password", "123456");
            DataSource dataSource= DruidDataSourceFactory.createDataSource(prop);
            Connection conn= dataSource.getConnection();
            String sql="select *from Students";
            PreparedStatement pstm =conn.prepareStatement(sql);
          ResultSet rs= pstm.executeQuery();
          brank br =null;
          List<brank> brands= new ArrayList<>();//创建了一个名为brank的表，用来存放brank类型的数据
          while (rs.next())
          {
              //获取数据
           int id =    rs.getInt("StudentID");
           String name = rs.getString("Name");
           int age = rs.getInt("Age");
           String gender=rs.getString("Gender");
           String classname=rs.getString("ClassName");
           int Score=rs.getInt("Score");

              //封装brank这个对象
             br=new brank();
            br.setAge(id);
            br.setGender(gender);
            br.setName(name);
            br.setClassName(classname);
            br.setScore(Score);

              //装载集合
            brands.add(br);
          }
          System.out.println(brands);
          rs.close();
          pstm.close();
          conn.close();

        }
    }
//    public static void  main(String[] args){
//
//    }

}
