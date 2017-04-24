package com.pesteryourrep.repositories;

/**
 * Created by Eric on 4/19/17.
 */

import com.pesteryourrep.entities.CongressMessage;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<CongressMessage, Integer> {


}

