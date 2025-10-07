import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class p12_db3 extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html");
        Connection conn=null;
        Statement stmt=null;
        PrintWriter out = response.getWriter();
        //String department = request.getParameter("course");
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            //create a database connection using jdbc , port no used here is 3306
            // database name is college and username is root , there is no password
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/college","root", "");
            stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement("insert into department values(?, ?, ?)");    
            pstmt.setString(1, request.getParameter("depid"));
              // Same for second parameter
            pstmt.setString(2, request.getParameter("depname"));
            pstmt.setInt(3, Integer.valueOf(request.getParameter("depnos")));
            // Execute the insert command using executeUpdate()
            // to make changes in database
            pstmt.executeUpdate();
            out.println("<html><body><p> Database Updated</p>");       
            //select data from table where dept matches the value given by user in form
            String sql = "SELECT * FROM department";
            pstmt = conn.prepareStatement(sql);
            //pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();
            // Extract data from result set
            while(rs.next())
            {
            //Retrieve by column name
            String did = rs.getString("DeptID");
            String dname = rs.getString("DeptName");
            int dnos = rs.getInt("NOS");
            
            //Display values
            out.println("<p> DeptID: " + did + "<br>");
            out.println("DeptName: " + dname + "<br>");
            out.println("Number of Students: " + dnos + "<br></p>");            
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