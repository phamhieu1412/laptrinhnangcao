package model;

import java.time.LocalDateTime;

public class Order {
    String id;
    LocalDateTime order_date;
    LocalDateTime required_date;
    LocalDateTime shipped_date;
    String status;
    String comment;
    Float total;
    String user_id;
    String coupon_code;
    
	public Order(String id, LocalDateTime order_date, LocalDateTime required_date, LocalDateTime shipped_date,
			String status, String comment, Float total, String user_id, String coupon_code) {
		super();
		this.id = id;
		this.order_date = order_date;
		this.required_date = required_date;
		this.shipped_date = shipped_date;
		this.status = status;
		this.comment = comment;
		this.total = total;
		this.user_id = user_id;
		this.coupon_code = coupon_code;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDateTime getOrder_date() {
		return order_date;
	}
	public void setOrder_date(LocalDateTime order_date) {
		this.order_date = order_date;
	}
	public LocalDateTime getRequired_date() {
		return required_date;
	}
	public void setRequired_date(LocalDateTime required_date) {
		this.required_date = required_date;
	}
	public LocalDateTime getShipped_date() {
		return shipped_date;
	}
	public void setShipped_date(LocalDateTime shipped_date) {
		this.shipped_date = shipped_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", order_date=" + order_date + ", required_date=" + required_date
				+ ", shipped_date=" + shipped_date + ", status=" + status + ", comment=" + comment + ", total=" + total
				+ ", user_id=" + user_id + ", coupon_code=" + coupon_code + "]";
	}
}
