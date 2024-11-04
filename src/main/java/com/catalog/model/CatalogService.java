package com.catalog.model;

import java.util.List;

public class CatalogService {
	
	private CatalogDAO_interface dao;
	
	public CatalogService() {
		dao =  new CatalogJDBCDAO();
	}
	
	public CatalogVO addCatalog(String name) {
		CatalogVO catalogVO = new CatalogVO();
		
		catalogVO.setName(name);
		dao.insert(catalogVO);
		
		return catalogVO;
	}
	
	public CatalogVO updateCatalog(Integer id, String name) {
		
		CatalogVO catalogVO = new CatalogVO();
		
		catalogVO.setId(id);
		catalogVO.setName(name);
		dao.update(catalogVO);
		
		return catalogVO;
	}
	
	public void deleteCatalog(Integer id) {
		dao.delete(id);
	}
	
	public CatalogVO getOneCatalog(Integer id) {
		return dao.findByPrimaryKey(id);
	}
	
	public List<CatalogVO> getAll(){
		return dao.getAll();
	}

}
