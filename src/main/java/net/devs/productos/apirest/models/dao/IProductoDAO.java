package net.devs.productos.apirest.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.devs.productos.apirest.models.entities.Producto;

public interface IProductoDAO extends CrudRepository<Producto,Long> {

	List<Producto> findByNombre(String nombre);
}
