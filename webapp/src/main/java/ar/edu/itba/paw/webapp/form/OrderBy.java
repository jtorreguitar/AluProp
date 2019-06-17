package ar.edu.itba.paw.webapp.form;

public class OrderBy {

    public static final int NOT_APPLICABLE_ORDER = -1;

    private int orderCriteria = NOT_APPLICABLE_ORDER;

    public int getOrderCriteria() {
        return orderCriteria;
    }

    public void setOrderCriteria(int orderCriteria) {
        this.orderCriteria = orderCriteria;
    }
}
