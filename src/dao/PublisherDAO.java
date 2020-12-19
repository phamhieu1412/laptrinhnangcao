package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import model.*;

public class PublisherDAO implements ObjectDAO {
	public static Map<String, Publisher> mapPublisher = layDuLieuTuDatabase();
	

	@Override
	public boolean add(Object obj) {
		Publisher ncc = (Publisher) obj;
		mapPublisher.put(ncc.getId(), ncc);
		try {
			String sql = "insert into [dbo].[Publisher] values (?)";
			Connection connect = ConnectDTB.connect();
			PreparedStatement ppstm = connect.prepareStatement(sql);
			ppstm.setString(1, ncc.getName());
			ppstm.executeUpdate();
		} catch (Exception e) {
			System.out.println("Lỗi thêm publisher: " + e.getMessage());
			return false;
		}
		return true;
	}
//PhÆ°Æ¡ng thá»©c láº¥y dá»¯ liá»‡u tá»« database lÃªn sá»­ dá»¥ng
	public static Map<String, Publisher> layDuLieuTuDatabase() {
		Map<String, Publisher> map = new HashMap<>();
		try {
			ResultSet rs = new ConnectDTB().chonDuLieu("select * from  Publisher");
			while (rs.next()) {
				String maPublisher = rs.getString(1);
				String tenPublisher = rs.getString(2);
				Publisher ncc = new Publisher(maPublisher, tenPublisher);
				map.put(ncc.getId(), ncc);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return map;
	}

	@Override
	public boolean edit(String id,Object obj) {
		Publisher ncc = (Publisher) obj;
		mapPublisher.replace(ncc.getId(), ncc);
		String sql = "update Publisher set name=? where id=?";
		Connection connect;
		try {
			connect = ConnectDTB.connect();
			PreparedStatement ppstm = connect.prepareStatement(sql);
			ppstm.setString(1, ncc.getName());
			ppstm.setString(5, id);
			ppstm.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean del(String id) {
		mapPublisher.remove(id);
		String sql = "delete from Publisher where MaPublisher='" + id + "'";
		try {
			new ConnectDTB().thucThiSQL(sql);
		} catch (Exception e) {
			System.out.println("Lỗi truy vấn:" + e.getMessage());
			return false;
		}
		return true;
	}
}
