package org.tnt.pt.repository;

import java.util.List;
import java.util.Map;

import org.tnt.pt.vo.BaseVO;




public interface BaseDao<T> {
		
		public Integer insert(T t) ;
		
		public void update(T t) ;
		
		public void updateBySelective(T t) ;
		
		public void delete(Long ids) ;
		
		
		public T selectById(Long Id) ;
		
		
		public Integer selectByMapCount(Map<String,Object>  map) ;
		
		public Integer selectByVOCount(BaseVO  baseVO) ;
		
		public List<T> selectByMap(Map<String,Object>   map) ;
		
}
