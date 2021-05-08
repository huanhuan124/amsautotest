package com.car.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zenghuan on 2016/9/13.
 */
public class TestDB {
    public static void main(String args[]){
        System.out.println("start");
        String driver = "com.mysql.jdbc.Driver";
        //需要连接的数据库
        String url = "jdbc:mysql://localhost:3306/uc_db";
        String username = "root";
        String password = "123456";
        String sql = "delete from t_book_order where id = '1639'";
       // String sql2 = "select *  from t_book_order where id = '1639'";
        try{
            Class.forName(driver);//加载驱动程序
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            //创建链接对象
            Connection con = DriverManager.getConnection(url, username, password);
            //创建sql语句执行对象
            Statement st = con.createStatement();
            //执行sql语句
          //  ResultSet rs = st.executeQuery(sql); 针对查询的执行语句
             int rows = st.executeUpdate(sql);//针对更新执行的语句

            //rs = st.executeQuery(sql2);
            //对结果进行处理
           /* while (rs.next()){
                System.out.println("id:"+rs.getString("id"));
                System.out.println("id:"+rs.getString(1));
                System.out.println("book_order_no:"+rs.getString("book_order_no"));
            }*/

            //关闭相关的对象，逆向关闭
           /* if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            */
            if(st != null)
            {
                try
                {
                    st.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if(con !=null)
            {
                try
                {
                    con.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }


        }catch(SQLException e)
        {
            e.printStackTrace();
        }

    }
}
