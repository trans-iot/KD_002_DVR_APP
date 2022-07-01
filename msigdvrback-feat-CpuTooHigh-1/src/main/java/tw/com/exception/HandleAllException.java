package tw.com.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.CannotCreateTransactionException;

public class HandleAllException {
	protected final static Logger logger = LoggerFactory.getLogger(CustomDatabaseException.class);

	public static String getMessage(Exception ex, MessageSourceAccessor msgSource) {
		String msg = "";
		logger.error("執行發生錯誤:" + ex);
		if (ex instanceof CannotCreateTransactionException) {
			msg = msgSource.getMessage("sys.error.db.access");
		} else {
			msg += ex;
		}

		return msg;
	}

}
