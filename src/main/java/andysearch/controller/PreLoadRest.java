package andysearch.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import andysearch.service.RestaurantService;
import andysearch.service.impl.RestaurantServiceImpl;
import andysearch.vo.Restaurant;


@WebServlet("/PreLoadRest")
public class PreLoadRest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private RestaurantService service;


	@Override
	public void init() throws ServletException {
		try {
			service = new RestaurantServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		List<Restaurant> restaurants = service.preLoadRestService();
		JsonArray restaurantArray = new JsonArray();
		try {
		    if (restaurants != null) {
		        for (Restaurant rest : restaurants) {
		            JsonObject respBody = new JsonObject();
		            respBody.addProperty("restaurant_id", rest.getRestaurantId());
		            respBody.addProperty("name", rest.getRestaurantName());
		            respBody.addProperty("address", rest.getAddress());
		            respBody.addProperty("total_scores", rest.getTotalScores());
		            respBody.addProperty("total_review", rest.getTotalReview());
		            respBody.addProperty("opening_hours", rest.getOpeningHours());
		            respBody.addProperty("home_phone", rest.getHomePhone());
		            respBody.addProperty("latitude", rest.getLatitude());
		            respBody.addProperty("longitude", rest.getLongitude());
		            respBody.addProperty("email", rest.getEmail());
		            restaurantArray.add(respBody);
		        }
		    } else {
		        JsonObject notFound = new JsonObject();
		        notFound.addProperty("NotFind", "查無此餐廳");
		        restaurantArray.add(notFound);
		    }
		} catch (Exception e) {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(restaurantArray.toString()); 
	        System.out.println(restaurantArray);
		    // 當捕捉到例外時，restaurantArray 將保持為空的 JsonArray
		    e.printStackTrace(); // 可選擇保留或移除這行，用於記錄錯誤
		}
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(restaurantArray.toString()); 
        System.out.println(restaurantArray);
	}

	
}
