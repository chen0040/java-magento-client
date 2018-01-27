package com.github.chen0040.magento.models;

import lombok.Data;

@Data
public class TierPrices {

  private long customer_group_id = 1;
  private long qty = 10000;
  private double value = 10;
}
