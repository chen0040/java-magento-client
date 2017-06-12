package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
@Setter
@Getter
public class SearchCriteria {
   private List<FilterGroup> filter_groups = new ArrayList<>();
   private int page_size = 10;
   private int current_page = 0;
}
