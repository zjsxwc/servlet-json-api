import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.io.PrintWriter;
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