package com.learning.web.service;

import com.learning.web.model.SimpleItemModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OptionService {

    Map<String, List<? extends SimpleItemModel<?>>> findByKeys(Set<String> keys);
    <T> List<SimpleItemModel<T>> findByKey(String key);
}
