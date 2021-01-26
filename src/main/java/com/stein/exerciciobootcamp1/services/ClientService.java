package com.stein.exerciciobootcamp1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stein.exerciciobootcamp1.dto.ClientDTO;
import com.stein.exerciciobootcamp1.entities.Client;
import com.stein.exerciciobootcamp1.repositories.ClientRepository;
import com.stein.exerciciobootcamp1.services.Exceptions.DataIntegrityException;
import com.stein.exerciciobootcamp1.services.Exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest request) {
		Page<Client> list = repository.findAll(request);

		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);

		return new ClientDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found")));

	}

	@Transactional
	public ClientDTO insertClient(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(entity, dto);

		entity = repository.save(entity);

		return new ClientDTO(entity);

	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
		Client entity = repository.getOne(id);
		copyDtoToEntity(entity, dto);		

		return new ClientDTO(repository.save(entity));
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("not found id");
		}

	}

	public void copyDtoToEntity(Client entity, ClientDTO dto) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());

	}
	
	public void delete(Long id) {
		
		try {
		repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Error. Cant find Entity");
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Error. Database Integrity Exception");
		}
	}

}
