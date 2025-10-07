import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class p12_db1 extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Connection conn=null;
        Statement stmt=null;
        PrintWriter out = response.getWriter();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            //out.println("<h1> into class</h1>");
            //create a database connection using jdbc , port no used here is 3306
            // database name is college and username is root , there is no password
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/college","root", "");
            if(conn==null)
            {
                out.println("<h1> No issues in Connection</h1>");
            }
            stmt = conn.createStatement();
            String sql;
            //display all the students' marks
            sql = "SELECT * FROM marks";
            ResultSet rs = stmt.executeQuery(sql);
            // Extract data from result set
            while(rs.next())
            {
                //out.println("<h1> into while loop</h1>");
                //Retrieve by column name
                String reg = rs.getString("RegNo");
                String name = rs.getString("Name");
                String dept = rs.getString("Dept");
                Double cgpa = rs.getDouble("CGPA");
                String sch = rs.getString("Scholarship");
                //Display values
                out.println("<p> RegNo: " + reg + "<br>");
                out.println("Name: " + name + "<br>");
                out.println("Department: " + dept + "<br>");
                out.println("CGPA: " + cgpa + "<br>");
                out.println("Availing Scholarship: " + sch + "<br></p>");
            }
            out.println("</body></html>");
            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            System.out.print("Do not connect to DB - Error:"+e);
        }
    }
}