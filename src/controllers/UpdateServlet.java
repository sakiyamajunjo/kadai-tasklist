package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	       String _token = (String)request.getParameter("_token");
	        if(_token != null && _token.equals(request.getSession().getId())) {
	            EntityManager em = DBUtil.createEntityManager();

	            int id = (Integer)(request.getSession().getAttribute("task_id"));
	            Task t = em.find(Task.class, id);

	            String content = request.getParameter("content");
	            t.setContent(content);

/*	            List<String> errors = MessageValidator.validate(m);
	            if(errors.size() > 0) {
	                em.close();

	                request.setAttribute("_token", request.getSession().getId());
	                request.setAttribute("message", m);
	                request.setAttribute("errors", errors);

	                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/edit.jsp");
	                rd.forward(request, response);
	            } else {*/
	                em.getTransaction().begin();
	                em.getTransaction().commit();
	                em.close();
	                request.getSession().setAttribute("flush", "更新が完了しました。");
	                request.getSession().removeAttribute("task_id");

	                response.sendRedirect(request.getContextPath() + "/index");
	            //}
	        }
	}

}
