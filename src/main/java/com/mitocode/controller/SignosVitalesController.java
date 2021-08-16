package com.mitocode.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.SignosVitales;
import com.mitocode.service.ISignosVitalesService;

@RestController
@RequestMapping("/signos_vitales")
@Transactional
public class SignosVitalesController {

	@Autowired
	private ISignosVitalesService service;
	
	@GetMapping
	public ResponseEntity<List<SignosVitales>> listar() throws Exception{
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SignosVitales> listarPorId(@PathVariable("id") Integer id) throws Exception{
		SignosVitales obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK); 		
	}
	
	@PostMapping
	public ResponseEntity<SignosVitales> registrar(@Valid @RequestBody SignosVitales p) throws Exception {
		SignosVitales obj = service.registrar(p);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSignosVitales()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<SignosVitales> modificar(@Valid @RequestBody SignosVitales paciente) throws Exception {
		return new ResponseEntity<>(service.modificar(paciente), HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<SignosVitales>> listarPageable(Pageable pageable) throws Exception{
		Page<SignosVitales> signos = service.listarPageable(pageable);
		return new ResponseEntity<Page<SignosVitales>>(signos, HttpStatus.OK);
	}
	
	
}
