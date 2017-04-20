package com.example.repositories;

/**
 * Created by Eric on 4/19/17.
 */

import com.example.entities.CongressMessages;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<CongressMessages, Integer> {


}

