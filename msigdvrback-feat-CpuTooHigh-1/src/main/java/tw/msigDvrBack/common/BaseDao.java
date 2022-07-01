package tw.msigDvrBack.common;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import tw.com.aop.DBHelper;
import tw.com.core.SessionData;
import tw.spring.ComLogger;


@Configuration
@Lazy
@Repository
public class BaseDao {

	@Autowired
	protected SqlSessionFactory sqlSessionFactory;
	
	@ComLogger
	private Logger logger;
	
}
