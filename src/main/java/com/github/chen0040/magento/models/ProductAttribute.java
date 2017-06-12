package com.github.chen0040.magento.models;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 12/6/2017.
 */
@Getter
@Setter
public class ProductAttribute {
   private boolean is_wysiwyg_enabled = false;
   private boolean is_html_allowed_on_front = false;
   private boolean used_for_sort_by = true;
   private boolean is_filterable = false;
   private boolean is_filterable_in_search = false;
   private boolean is_used_in_grid = false;
   private boolean is_visible_in_grid = false;
   private boolean is_filterable_in_grid = false;
   private int position = 0;
   private List<String> apply_to = new ArrayList<>();
   private String is_searchable = "1";
   private String is_visible_in_advanced_search = "1";
   private String is_comparable = "0";
   private String is_used_for_promo_rules = "0";
   private String is_visible_on_front = "0";
   private String used_in_product_listing = "1";
   private boolean is_visible = true;
   private String scope = "store";
   private long attribute_id = 73;
   private String attribute_code = "name";
   private String frontend_input = "text";
   private String entity_type_id = "4";
   private boolean is_required = true;
   private List<String> options = new ArrayList<>();
   private boolean is_user_defined = false;
   private String default_frontend_label = "Product Name";
   private String frontend_labels = null;
   private String backend_type = "varchar";
   private String is_unique = "0";
   private String frontend_class = "validate-length maximum-length-255";
   private List<String> validation_rules = new ArrayList<>();
}
