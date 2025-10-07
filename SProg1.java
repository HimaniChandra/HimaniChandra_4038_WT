import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class SProg1 extends HttpServlet {
 
   private String message;

   public void init() throws ServletException {
      // Do required initialization
      message = "Welcome you all to the Servlet Arena!!";
   }

   public void doGet(HttpServletRequest request, 
   HttpServletResponse response)
      throws ServletException, IOException {
      
      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.
      PrintWriter out = response.getWriter();
      out.println("<h1>" + message + "</h1>");
      out.println("<p><a href="+"'http://localhost:8080/himani_4038_tomcat/index.html'"+">Go Back</a></p>");
   }

   public void destroy() {
      // do nothing.
   }
}