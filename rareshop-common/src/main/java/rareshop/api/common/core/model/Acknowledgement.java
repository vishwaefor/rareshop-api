package rareshop.api.common.core.model;

import rareshop.api.common.core.constant.Params;

import java.util.HashMap;

public class Acknowledgement extends HashMap<String,Object> {

    public Acknowledgement() {

    }

    public Acknowledgement(String message, Object payload) {
        put(Params.MESSAGE, message);
        put(Params.PAYLOAD, payload);
    }
}
