package controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.KhachHangDAO;
import dao.UndoDAO;
import model.KhachHang;

@WebServlet("/Process")
public class ProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProcessController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("text/html;charset=utf-8");
		
		HttpSession session = request.getSession();
		session.getAttribute("");
		
		String chucNang = request.getParameter("chucNang");
		String maKH = request.getParameter("id");
		if(chucNang==null){
			
		}else
			//Chức năng xóa khách hàng
			if(chucNang.equals("Delete")){
			UndoDAO.undoCustomer.push(KhachHangDAO.mapKhachHang.get(maKH));
			new KhachHangDAO().del(maKH);
		}else
			//chức năng xóa toàn bộ khách hàng
			if(chucNang.equals("DelAll")){
			new KhachHangDAO().delAll();
		}else
			//chức năng undoAll
			if(chucNang.equals("UndoAll")){
			new KhachHangDAO().undo();
		}else
			//chức năng undoOne
			if(chucNang.equals("UndoOne")){
			new UndoDAO().restoreDeletedCustomer();
		}else
			//chức năng sửa khách hàng
			if(chucNang.equals("Edit")){
			String tenKH=request.getParameter("tenKH");
			String tenTKKH=request.getParameter("tkkh");
			String pass=request.getParameter("pass");
			String sdt=request.getParameter("sdt");
			String ma=request.getParameter("makh");
			KhachHang kh = new KhachHang(ma, tenKH, pass, sdt, tenTKKH);
			new KhachHangDAO().edit(kh);
		}else
			//chức năng thêm khách hàng
			if(chucNang.equals("Add")){
				String tenKH=request.getParameter("tenKH");
				String tenTKKH=request.getParameter("tkkh");
				String pass=request.getParameter("pass");
				String email=request.getParameter("email");
				String diaChi=request.getParameter("diachi");
				String sdt=request.getParameter("sdt");
				String ma="KH"+new KhachHangDAO().random(3000);
			KhachHang kh = new KhachHang(ma, tenKH, pass, sdt, tenTKKH);
			new KhachHangDAO().add( kh);
		}
		response.sendRedirect("showcustomer.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
