//$Id: CallbackHandlerConsumer.java 11282 2007-03-14 22:05:59Z epbernard $
package org.hibernate.ejb.event;

/**
 * @author Emmanuel Bernard
 */
public interface CallbackHandlerConsumer {
	void setCallbackHandler(EntityCallbackHandler callbackHandler);
}
