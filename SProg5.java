/*to set cookies*/
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class SProg5 extends HttpServlet {
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     try{
        response.setContentType("text/html");

        PrintWriter pwriter = response.getWriter();

        String in_data = request.getParameter("userin");
        
        Cookie c = new Cookie("Ucookie",in_data);      

        response.addCookie(c);

        pwriter.print("<h3>Hello - The set cookie is:</h3>"+in_data);

        pwriter.println("<br /> This is how cookie is SET");
     
        pwriter.close();
    }
     catch(Exception exp){
       System.out.println(exp);
     }
  }
}







/*
HTTP/1.1 200 OK
Date: Fri, 04 Feb 2000 21:03:38 GMT
Server: Apache/1.3.9 (UNIX) PHP/4.0b3
Set-Cookie: name = xyz; expires = Friday, 04-Feb-07 22:03:38 GMT; 
   path = /; domain = licet.ac.in
Connection: close
Content-Type: text/html
*/