package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.ConnectDTB;
import model.Order;
import model.Book;

public class OrderDAO implements ObjectDAO {

	public static Map<String, Order> mapOrder = getLoadOrderDTB();
	public static Map<String, Order> mapUndo = new HashMap<>();
	public static Set<String> setDateOrder =  getSetDateOrder();
	public OrderDAO() {

	}

	public static Set<String> getSetDateOrder() {
		Set<String> date = new HashSet<>();
		for (Order od : mapOrder.values()) {
			date.add(od.getOrder_date());
		}
		return date;
	}

	public boolean delAll() {
		mapUndo.putAll(mapOrder);
		mapOrder.clear();

		return true;
	}

	public boolean undo() {
		mapOrder.putAll(mapUndo);
		mapUndo.clear();
		return true;
	}

	public int random(int limit) {
		Random rd = new Random();
		int res = rd.nextInt(limit);
		while (mapOrder.containsKey("Order" + res)) {
			res = rd.nextInt(limit);
		}
		return res;
	}

	@Override
	public boolean add(Object obj) {
		Order order = (Order) obj;
		mapOrder.put(order.getId(), order);
		try {
			Connection connection = ConnectDTB.connect();
			PreparedStatement statement = connection.prepareStatement("insert into [Order] values(?,?,?,?,?,?,?,?)");
			statement.setString(1, order.getOrder_date().toString());
			statement.setString(2, order.getRequired_date().toString());
			statement.setString(3, order.getShipped_date().toString());
			statement.setString(4, order.getStatus());
			statement.setString(5, order.getComment());
			statement.setString(6, order.getTotal().toString());
			statement.setString(7, order.getUser_id());
			statement.setString(8, order.getCoupon_code());
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}

	public  Map<String, Order> getSelectOrderDate(String date) {
		Map<String, Order> mapSelectProduct = new HashMap<>();
		for (Order sp : mapOrder.values()) {
			if (sp.getOrder_date().equals(date)) {
				mapSelectProduct.put(sp.getId(), sp);
			}
		}
		return mapSelectProduct;
	}

	public static Map<String, Order> getLoadOrderDTB() {
		Map<String, Order> listOrder = new HashMap<String, Order>();
		try {
			ResultSet rs = new ConnectDTB().chonDuLieu("select * from [Order]");
			while (rs.next()) {
				String orderID = rs.getString(1);
				String customerName = rs.getString(2);
				String productName = rs.getString(3);
				String date = rs.getString(4);
				String totalPrice = rs.getString(5);
				listOrder.put(orderID, new Order(orderID, productName, customerName, date, totalPrice));
			}
		} catch (Exception e) {
			System.out.println("Lỗi ở load danh sách database " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listOrder;
	}

	@Override
	public boolean edit(String id, Object obj) {
		Order order = (Order) obj;
		mapOrder.replace(order.getId(), order);
		try {
			Connection connection = ConnectDTB.connect();
			String sql = "update orders set order_date=?,required_date=?,shipped_date=?,status=?,comment=?,total=?,user_id=?,coupon_code=? where id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, order.getOrder_date());
			preparedStatement.setString(2, order.getRequired_date());
			preparedStatement.setString(3, order.getShipped_date());
			preparedStatement.setString(4, order.getStatus());
			preparedStatement.setString(5, order.getComment());
			preparedStatement.setString(6, order.getTotal());
			preparedStatement.setString(7, order.getUser_id());
			preparedStatement.setString(8, order.getCoupon_code());
			preparedStatement.setString(9, order.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean del(String id) {
		mapOrder.remove(id);
		try {
			new ConnectDTB().thucThiSQL("delete from orders where id='" + id + "'");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	

}
