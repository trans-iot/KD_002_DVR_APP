/*
 *   version 1.0 
 *   
 */ 
package tw.com.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.CannotCreateTransactionException;

/**
 * 資料庫連線 Exception
 * 
 * @author derek_chang 
 * @since 2007/12/20 下午 5:55:10
 *
 */
public class CustomDatabaseException {
	protected final static Logger logger = LoggerFactory.getLogger( CustomDatabaseException.class ); 
	public static void saveError(Exception dae){
		if( dae instanceof CannotCreateTransactionException){			
			logger.error("資料庫連線發生錯誤");
		}
		
	}

}
