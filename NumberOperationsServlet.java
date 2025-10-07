import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// ---------------- Interface ----------------
interface Operation {
    String perform(int num);
}

// ---------------- Individual Operations ----------------
class FactorialOperation implements Operation {
    public String perform(int num) {
        long result = 1;
        for (int i = 2; i <= num; i++) result *= i;
        return "Factorial: " + result;
    }
}

class PalindromeOperation implements Operation {
    public String perform(int num) {
        String s = Integer.toString(num);
        boolean isPal = s.equals(new StringBuilder(s).reverse().toString());
        return "Palindrome: " + (isPal ? "Yes" : "No");
    }
}

class FibonacciOperation implements Operation {
    public String perform(int num) {
        if (num <= 0) return "Fibonacci: Invalid input";
        List<Integer> list = new ArrayList<>();
        int a = 0, b = 1;
        for (int i = 0; i < num; i++) {
            list.add(a);
            int next = a + b;
            a = b;
            b = next;
        }
        return "Fibonacci: " + list;
    }
}

class PrimeOperation implements Operation {
    public String perform(int num) {
        if (num <= 1) return "Prime: No";
        for (int i = 2; i <= Math.sqrt(num); i++)
            if (num % i == 0) return "Prime: No";
        return "Prime: Yes";
    }
}

class CubeRootOperation implements Operation {
    public String perform(int num) {
        return "Cube Root: " + Math.cbrt(num);
    }
}

// ---------------- Main Servlet ----------------
public class NumberOperationsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the number entered by the user
        int num = Integer.parseInt(request.getParameter("number"));

        // Get the selected operations (checkbox values)
        String[] ops = request.getParameterValues("operation");

        out.println("<html><body>");
        out.println("<h2>Results for Number: " + num + "</h2>");

        if (ops != null) {
            for (String op : ops) {
                Operation operation = getOperation(op);
                if (operation != null) {
                    out.println("<p>" + operation.perform(num) + "</p>");
                } else {
                    out.println("<p>Unknown operation: " + op + "</p>");
                }
            }
        } else {
            out.println("<p>No operation selected.</p>");
        }

        out.println("</body></html>");
    }

    // Factory method to create correct Operation object
    private Operation getOperation(String op) {
        switch (op) {
            case "factorial": return new FactorialOperation();
            case "palindrome": return new PalindromeOperation();
            case "fibonacci": return new FibonacciOperation();
            case "prime":     return new PrimeOperation();
            case "cuberoot":  return new CubeRootOperation();
            default:          return null;
        }
    }
}
