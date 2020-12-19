package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import model.*;


public class LoaiSanPhamDAO implements ObjectDAO {
	public static Map<String, Category> mapLoaiSanPham = layDuLieuTuDatabase();

	@Override
	public boolean add(Object obj) {
		Category sp = (Category) obj;
		mapLoaiSanPham.put(sp.getId(), sp);
		try {
			String sql = "insert into Category values (?)";
			Connection connect = ConnectDTB.connect();
			PreparedStatement ppstm = connect.prepareStatement(sql);
			ppstm.setString(1, sp.getName());
			ppstm.executeUpdate();
		} catch (Exception e) {
			System.out.println("Lỗi truy vấn db " + e.getMessage());
			return false;
		}
		return true;
	}
//phÆ°Æ¡ng thá»©c load dá»¯ liá»‡u tá»« database
	public static Map<String, Category> layDuLieuTuDatabase() {
		Map<String, Category> map = new HashMap<>();
		try {
			ResultSet rs = new ConnectDTB().chonDuLieu("select * from  Category");
			while (rs.next()) {
				String maLoai = rs.getString(1);
				String tenLoai = rs.getString(2);
				Category sp = new Category(maLoai, tenLoai);
				map.put(sp.getId(), sp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return map;
	}

	@Override
	public boolean edit(String id,Object obj) {
		Category sp = (Category) obj;
		mapLoaiSanPham.replace(sp.getId(), sp);
		String sql = "update Category set name=? where id=?";
		Connection connect;
		try {
			connect = ConnectDTB.connect();
			PreparedStatement ppstm = connect.prepareStatement(sql);
			ppstm.setString(1, sp.getName());
			ppstm.setString(4, id);
			ppstm.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean del(String id) {
		mapLoaiSanPham.remove(id);
		String sql = "delete from Category where id='" + id + "'";
		try {
			new ConnectDTB().thucThiSQL(sql);
		} catch (Exception e) {
			System.out.println("Lỗi truy vấn db: " + e.getMessage());
			return false;
		}
		return true;
	}
	

}
