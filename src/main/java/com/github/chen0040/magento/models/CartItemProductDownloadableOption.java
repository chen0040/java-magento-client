package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 11/7/2017.
 */
@Getter
@Setter
public class CartItemProductDownloadableOption {
    private List<Integer> downloadable_links = new ArrayList<>();

}
