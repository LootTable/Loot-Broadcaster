package com.loottable.broadcasts;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BroadcastsPluginSmokeTest
{
	@Test
	public void pluginCanBeConstructed()
	{
		assertNotNull(new BroadcastsPlugin());
	}
}

