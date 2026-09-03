package com.faniherfeei.demo1dashboard.model;

import java.math.BigDecimal;
import java.util.List;
public record RowData(String name, List<BigDecimal> values) {}