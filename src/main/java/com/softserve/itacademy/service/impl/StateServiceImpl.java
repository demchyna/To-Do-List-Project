package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.controller.HomeController;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        if (state != null) {
            return stateRepository.save(state);
        }
        throw new NullEntityReferenceException("State cannot be 'null'");
    }

    @Override
    public State readById(long id) {
        return stateRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("State with id " + id + " not found"));
    }

    @Override
    public State update(State state) {
        if (state != null) {
            readById(state.getId());
            return stateRepository.save(state);
        }
        throw new NullEntityReferenceException("State cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        State state = readById(id);
        stateRepository.delete(state);
    }

    @Override
    public List<State> getAll() {
//        return stateRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return stateRepository.getAll();
    }

    @Override
    public State getByName(String name) {
        Optional<State> optional = Optional.ofNullable(stateRepository.findByName(name));
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("State with name '" + name + "' not found");
    }
}
