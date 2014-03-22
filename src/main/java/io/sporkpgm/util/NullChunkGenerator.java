package io.sporkpgm.util;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class NullChunkGenerator extends ChunkGenerator {

	public byte[] generate(World world, Random random, int x, int z) {
		return new byte[65536];
	}

}
