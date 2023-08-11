package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
		
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			List<Category> category = (List<Category>) categoryDao.findAll();
			response.getCategoryResponse().setCategory(category);
			response.setMetadata(HttpStatus.OK.toString(), "Respuesta Exitosa");
			
		} catch (Exception e) {
			response.setMetadata(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		
		try {
	
			Optional<Category> category = categoryDao.findById(id);
			
			if(category.isPresent()) {
				list.add(category.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata(HttpStatus.OK.toString(), "Categoria encontrada");
			} else {
				response.setMetadata(HttpStatus.NOT_FOUND.toString(), "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			response.setMetadata(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	
	@Override
	@Transactional(readOnly = false)
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		
		try {
	
			Category categorySaved = categoryDao.save(category);
			
			if(categorySaved != null) {
				list.add(categorySaved);
				response.getCategoryResponse().setCategory(list);
				response.setMetadata(HttpStatus.CREATED.toString(), "Categoria guardada con exito");
			} else {
				response.setMetadata(HttpStatus.BAD_REQUEST.toString(), "Categoria no guardada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
			}


			
		} catch (Exception e) {
			response.setMetadata(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error al guardar categoria");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
	
	
	@Override
	@Transactional(readOnly = false)
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		
		try {
			Optional<Category> categorySearch = categoryDao.findById(id);
			
			if(categorySearch.isPresent()) {
				
				categorySearch.get().setName(category.getName());
				categorySearch.get().setDescription(category.getDescription());
				
				Category categoryToUpdate = categoryDao.save(categorySearch.get());
				
				if(categoryToUpdate != null) {
					list.add(categoryToUpdate);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata(HttpStatus.OK.toString(), "Categoria actualizada con exito");
				} else {
					response.setMetadata(HttpStatus.BAD_REQUEST.toString(), "Categoria no actualizada");
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
				}

			} else {
				response.setMetadata(HttpStatus.BAD_REQUEST.toString(), "Categoria no actualizada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setMetadata(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error al actualizar categoria");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
	
	@Override
	@Transactional(readOnly = false)
	public ResponseEntity<CategoryResponseRest> delete(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		
		
		try {
	
			Optional<Category> categorySearch = categoryDao.findById(id);
			
			if(categorySearch != null) {
				categoryDao.deleteById(id);
				list.add(categorySearch.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata(HttpStatus.OK.toString(), "Categoria eliminada con exito");
			} else {
				response.setMetadata(HttpStatus.BAD_REQUEST.toString(), "Categoria no eliminada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
			}


			
		} catch (Exception e) {
			response.setMetadata(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error al guardar categoria");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
}
