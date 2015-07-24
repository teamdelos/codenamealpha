package com.cmu.delos.codenamealpha.Adapter;

import com.cmu.delos.codenamealpha.server.OrderServer;

/**
 * Created by felixamoruwa on 7/22/15.
 */
public abstract class BuildOrder extends ProxyOrder implements UpdateOrder, CreateOrder, FixOrder, OrderServer{
}
