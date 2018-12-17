package com.g4pas.index.kafka;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by fawks on 07/02/2017.
 */
public class ZookeeperConfigTest {
  @Test
  public void getConnect() throws Exception {
    KafkaConfig.ZookeeperConfig cfg = new KafkaConfig.ZookeeperConfig();
    String tstConnect = "testConnect";
    assertEquals(tstConnect,
                 cfg.setConnect(tstConnect)
                    .getConnect());
  }

}