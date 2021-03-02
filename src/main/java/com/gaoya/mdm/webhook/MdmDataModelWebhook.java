package com.gaoya.mdm.webhook;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MdmDataModelWebhook {

    @WebMethod
    public String hook(String model, String message) {
        System.out.println("Receive a data model: " + model + " message:\n" + message);
        return "OK, " + model;
    }

}
