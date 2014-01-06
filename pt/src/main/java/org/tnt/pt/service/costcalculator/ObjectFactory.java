
package org.tnt.pt.service.costcalculator;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.erry.samacs.services.costcalculator package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTotalCosts_QNAME = new QName("com/erry/samacs/services/CostCalculator", "getTotalCosts");
    private final static QName _GetTotalCostsResponse_QNAME = new QName("com/erry/samacs/services/CostCalculator", "getTotalCostsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.erry.samacs.services.costcalculator
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTotalCostsResponse }
     * 
     */
    public GetTotalCostsResponse createGetTotalCostsResponse() {
        return new GetTotalCostsResponse();
    }

    /**
     * Create an instance of {@link GetTotalCosts }
     * 
     */
    public GetTotalCosts createGetTotalCosts() {
        return new GetTotalCosts();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalCosts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com/erry/samacs/services/CostCalculator", name = "getTotalCosts")
    public JAXBElement<GetTotalCosts> createGetTotalCosts(GetTotalCosts value) {
        return new JAXBElement<GetTotalCosts>(_GetTotalCosts_QNAME, GetTotalCosts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalCostsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com/erry/samacs/services/CostCalculator", name = "getTotalCostsResponse")
    public JAXBElement<GetTotalCostsResponse> createGetTotalCostsResponse(GetTotalCostsResponse value) {
        return new JAXBElement<GetTotalCostsResponse>(_GetTotalCostsResponse_QNAME, GetTotalCostsResponse.class, null, value);
    }

}
