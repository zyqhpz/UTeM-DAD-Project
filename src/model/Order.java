package model;

import java.util.Date;
import java.util.List;

public class Order {
	private int orderId;
	private int orderNumber;
	private Date transactionDate;
	private List<OrderItem> orderitems;
	private int totalOrderItem;
	private double subTotal;
	private double serviceTax;
	private double rounding;
	private double grandTotal;
	private double tenderedCash;
	private double change;
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public List<OrderItem> getOrderitems() {
		return orderitems;
	}
	
	public void setOrderitems(List<OrderItem> orderitems) {
		this.orderitems = orderitems;
	}
	
	public int getTotalOrderItem() {
		return totalOrderItem;
	}
	
	public void setTotalOrderItem(int totalOrderItem) {
		this.totalOrderItem = totalOrderItem;
	}
	
	public double getSubTotal() {
		return subTotal;
	}
	
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	public double getServiceTax() {
		return serviceTax;
	}
	
	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}
	
	public double getRounding() {
		return rounding;
	}
	
	public void setRounding(double rounding) {
		this.rounding = rounding;
	}
	
	public double getGrandTotal() {
		return grandTotal;
	}
	
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	public double getTenderedCash() {
		return tenderedCash;
	}
	
	public void setTenderedCash(double tenderedCash) {
		this.tenderedCash = tenderedCash;
	}
	
	public double getChange() {
		return change;
	}
	
	public void setChange(double change) {
		this.change = change;
	}
	
	
}
