import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApiServlet
 */
public class ApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.33.77:3306/gfyh";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //private static final String DB_URL = "jdbc:mysql://192.168.33.77:3306/gfyh?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "root";

    private String fetchAddressFromMysql()
    {
        String output = "";

        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, consignee_name FROM address";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String consigneeName = rs.getString("consignee_name");
                // 输出数据
                output += "ID: " + id + ", 收货人: " + consigneeName + "\n";
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception se){
            // 处理 JDBC 错误
            se.printStackTrace();
        } finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return output;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getParameter("param");
        System.out.println(param);
        response.setHeader("content-type","application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String,Object> map = new HashMap<>();
        map.put("code",-1);
        map.put("data",param);

        map.put("output", fetchAddressFromMysql());

        JSONObject jsonObj = new JSONObject(map);
        String resJSON = jsonObj.toString();
        System.out.println(resJSON);

        out.print(resJSON);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}