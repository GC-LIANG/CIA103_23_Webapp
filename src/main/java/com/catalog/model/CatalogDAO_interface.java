package com.catalog.model;

import java.util.List;

public interface CatalogDAO_interface {
	public void insert(CatalogVO catalogVO);
	public void update(CatalogVO catalogVO);
	public void delete(Integer id);
	public CatalogVO findByPrimaryKey(Integer id);
	public List<CatalogVO> getAll();

}
