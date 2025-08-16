package edu.bbte.pmim2290.vrp.dao;

import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<M> {
    List<M> findAll() throws DatabaseException;

    Optional<M> findById(Long id) throws EntityNotFoundException, DatabaseException;

    M save(M entity) throws EntityNotFoundException, DatabaseException;

    void deleteById(Long id) throws EntityNotFoundException, DatabaseException;
}