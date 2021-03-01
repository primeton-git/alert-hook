package com.gaoya.mdm.webhook;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class MdmDataModelWebhook {

    @WebMethod
    public String hook(String xmlContent) {
        System.out.println("Receive a data message:\n");
        System.out.println(xmlContent);
        return "OK";
    }

}
