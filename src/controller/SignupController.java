package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.KhachHangDAO;
import model.User;

@WebServlet("/Signup")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignupController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String action = request.getParameter("action");
		
		String taiKhoan = request.getParameter("username");
		String matKhau = request.getParameter("password");
		String tenKhachHang = request.getParameter("ten");
		String gioiTinh = request.getParameter("gioiTinh");
		String soDienThoai = request.getParameter("soDienThoai");
		String email = request.getParameter("email");

		String ngay = request.getParameter("ngay");
		String thang = request.getParameter("thang");
		String nam = request.getParameter("nam");

		String ngaySinh = nam + "-" + thang + "-" + ngay;
		String diaChi = request.getParameter("diaChi");
		String soLuongMua = request.getParameter("soLuongMua");
		String role = request.getParameter("role");
		
		if(action==null){
			System.out.println("Khong thuc hien gi het");
		}else if(action.equals("Login")){
			String userName = request.getParameter("username");
			String passWord = request.getParameter("password");
			if(new KhachHangDAO().checkLogin(userName, passWord)){
				HttpSession session = request.getSession();
				User kh = KhachHangDAO.mapKhachHang.get(userName);
				session.setAttribute("userlogin", kh);
			}
		}else if(action.equals("Res")){
			String quyen = "";
			if (role != null && role.equals("0107")) {
				quyen = "admin";
			} else {
				quyen = "Khach Hang";
			}
//			User kh = new User(taiKhoan, matKhau, tenKhachHang, gioiTinh, soDienThoai, email, ngaySinh,
//					diaChi, soLuongMua, quyen);
//			HttpSession session = request.getSession();
//			KhachHangDAO.mapKhachHang.put(kh.getUsername(), kh);
//			new KhachHangDAO().add(kh);
//			session.setAttribute("userlogin", kh);
		}else if(action.equals("Logout")){
			HttpSession session = request.getSession();
			session.invalidate();
		}
		

			
		response.sendRedirect("account.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String action = request.getParameter("action");
		
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		
		User kh = new User(userName, passWord);
		
		if(new KhachHangDAO().add(kh)){
			HttpSession session = request.getSession();
			if(kh.getRole())
				session.setAttribute("adminUser", kh);
			else {
				session.setAttribute("customerUser", kh);
			}
			response.sendRedirect(request.getParameter("from"));
		}
		else {
			response.sendRedirect("login.jsp");
		}
	}

}
