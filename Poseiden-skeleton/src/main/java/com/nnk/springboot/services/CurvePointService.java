package com.nnk.springboot.services;

import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurvePointService {
    @Autowired
    private CurvePointRepository curvePointRepository;
}
