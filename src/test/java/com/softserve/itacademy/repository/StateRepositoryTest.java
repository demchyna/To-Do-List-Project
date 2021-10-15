package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StateRepositoryTest {

    private final StateRepository stateRepository;

    @Autowired
    public StateRepositoryTest(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Test
    @DisplayName("Get the state by name when it is present")
    public void testGetByName_1() {
        State state2 = new State();
        state2.setName("state #1");

        State expected = stateRepository.save(state2);
        State actual = stateRepository.findByName("state #1");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get the state by name when it is absent")
    public void testGetByName_2() {
        State state1 = new State();
        state1.setName("state #1");
        stateRepository.save(state1);

        State actual = stateRepository.findByName("state #2");

        assertNull(actual);
    }

    @Test
    @DisplayName("Get all states")
    public void testGetAll() {
        State state1 = new State();
        state1.setName("state #1");
        stateRepository.save(state1);

        State state2 = new State();
        state2.setName("state #2");
        stateRepository.save(state2);

        State state3 = new State();
        state3.setName("state #3");
        stateRepository.save(state3);

        stateRepository.save(state1);

        List<State> expected = List.of(state1, state2, state3);
        List<State> actual = stateRepository.getAll();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
