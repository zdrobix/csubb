package org.example.repository.interf;

import org.example.domain.Signup;
import org.example.domain.Tuple;

public interface ISignupRepository extends IRepository<Tuple<Integer, Integer>, Signup> {

    Iterable<Signup> GetAllByChildId (Integer id);
    Iterable<Signup> GetAllByEventId (Integer id);
}
