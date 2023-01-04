package com.frugalis.repository;

import com.frugalis.entity.ZIPCodes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipCOdeRepository  extends CrudRepository<ZIPCodes,Long> {
}
