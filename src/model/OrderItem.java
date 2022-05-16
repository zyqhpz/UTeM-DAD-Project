package model;

import java.io.Serializable;
import java.util.Date;

/**
 * This is the model class for OrderItem.
 *
 */

public class OrderItem implements Serializable {
	private int orderItemId;
	private ItemProduct itemProduct;
	private int quantity;
	private double subTotalAmount;
	private int sequenceNumber;
	private String orderStatus;
	private Date readyTime;

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public ItemProduct getItemProduct() {
		return itemProduct;
	}

	public void setItemProduct(ItemProduct itemProduct) {
		this.itemProduct = itemProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getReadyTime() {
		return readyTime;
	}

	public void setReadyTime(Date readyTime) {
		this.readyTime = readyTime;
	}

}
