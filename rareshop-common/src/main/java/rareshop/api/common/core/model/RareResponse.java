/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RareResponse {

    private static final String SUCCESSFUL = "successful";
    private static final String UNSUCCESSFUL = "unsuccessful";

    private String status;
    private List<Object> data;
    private List<Object> errors;

    public RareResponse() {
        status = SUCCESSFUL;

    }

    public RareResponse(Object data) {
        this();
        this.data = new ArrayList<>();
        if (data instanceof Collection<?>) {
            this.data.addAll((Collection<?>) data);
        } else {
            this.data.add(data);
        }
    }

    public RareResponse(Object data, boolean successful) {
        this();
        if (successful) {
            this.data = new ArrayList<>();
            if (data instanceof Collection<?>) {
                this.data.addAll((Collection<?>) data);
            } else {
                this.data.add(data);
            }
        } else {
            status = UNSUCCESSFUL;
            this.errors = new ArrayList<>();
            if (data instanceof Collection<?>) {
                this.errors.addAll((Collection<?>) data);
            } else {
                this.errors.add(data);
            }
        }
    }

    public List<Object> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
