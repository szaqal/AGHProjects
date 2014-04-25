package grid.ws.impl;

import javax.xml.namespace.QName;

/**
 * Musi byc
 * @author malczyk
 *
 */
public interface BankAccountQNames {
	
	String VALUE = "Value";

	String LAST_OP = "LastOp";

	String ACCOUNT_LIST = "AccountList";

	
	String NS = "http://www.globus.org/namespaces/grid/ws/BankAccountService_instance";

	QName RP_VALUE = new QName(NS, VALUE);

	QName RP_LASTOP = new QName(NS, LAST_OP);
	
	QName RP_ACCOUNT_LIST = new QName(NS, ACCOUNT_LIST);

	QName RESOURCE_PROPERTIES = new QName(NS,"BankAccountResourceProperties");

}
