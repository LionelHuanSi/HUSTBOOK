package com.hedspi.javalorant.dto;

import java.util.List;

public class SortInvoiceDTO {
    private String field;
    private String type;
    private List<Long> invoiceIDList;

    public SortInvoiceDTO() {
    }

    public SortInvoiceDTO(String field, String type, List<Long> invoiceIDList) {
        this.field = field;
        this.type = type;
        this.invoiceIDList = invoiceIDList;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getInvoiceIDList() {
        return invoiceIDList;
    }

    public void setInvoiceIDList(List<Long> invoiceIDList) {
        this.invoiceIDList = invoiceIDList;
    }

    @Override
    public String toString() {
        return "SortInvoiceDTO{" +
                "field='" + field + '\'' +
                ", type='" + type + '\'' +
                ", invoiceIDList=" + invoiceIDList +
                '}';
    }
}
