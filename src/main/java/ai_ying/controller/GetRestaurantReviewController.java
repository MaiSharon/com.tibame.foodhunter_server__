package ai_ying.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ai_ying.service.GroupService;
import ai_ying.service.impl.GroupServiceImpl;
import andysearch.vo.Restaurant;
import jessey.vo.Review;

@WebServlet("/group/review")
public class GetRestaurantReviewController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private GroupService service;

	@Override
	public void init() throws ServletException {
		try {
			service = new GroupServiceImpl();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		Review review = gson.fromJson(req.getReader(), Review.class);
		JsonObject result = new JsonObject();
		result.addProperty("result", gson.toJson(service.getRestaurantReview(review)));
		PrintWriter out = resp.getWriter();
		out.println(result.toString());
	}

}
