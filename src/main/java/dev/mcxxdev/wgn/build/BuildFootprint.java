package dev.mcxxdev.wgn.build;

/**
 * Bounding box (relative to build origin) cleared before structure placement.
 */
record BuildFootprint(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
	static BuildFootprint forSpec(BuildSpec spec) {
		return switch (spec.structureType()) {
			case HOUSE -> box(0, spec.size().width() - 1, 0, spec.size().height() + 1, 0, spec.size().depth() - 1);
			case CASTLE -> {
				int radius = Math.max(4, spec.size().width() / 2);
				int height = spec.size().height() + 4;
				int tower = Math.max(2, BuildSize.SMALL.width() / 4);
				int towerH = BuildSize.SMALL.height() + 6;
				int minX = Math.min(-2 - tower, -radius - tower) - 1;
				int maxX = Math.max(-2 + radius * 2 + 4 + tower, radius + tower) + 1;
				int maxY = Math.max(height + 1, towerH + 1);
				yield new BuildFootprint(minX, maxX, -1, maxY, minX, maxX);
			}
			case TOWER -> {
				int r = Math.max(2, spec.size().width() / 4);
				int h = spec.size().height() + 6;
				yield box(-r - 1, r + 1, -1, h + 1, -r - 1, r + 1);
			}
			case BRIDGE -> {
				int length = spec.size().width() + 10;
				int width = 3 + spec.size().stories();
				yield box(-1, length, -2, 2, -1, width);
			}
			case WALL -> box(-1, spec.size().width() + 7, -1, spec.size().height(), -1, 1);
			case FARM -> {
				int size = spec.size().width();
				int houseW = spec.size().width();
				int houseH = spec.size().height() + 1;
				yield box(-1, size + 2 + houseW, -1, Math.max(2, houseH), -1, Math.max(size, spec.size().depth()) - 1);
			}
			case MARKET -> {
				int stalls = Math.max(3, spec.size().width() / 3);
				yield box(-1, stalls * 3 + 1, -1, 3, -1, 2);
			}
			case PALACE -> {
				int hugeW = BuildSize.HUGE.width();
				int largeW = BuildSize.LARGE.width();
				int height = BuildSize.HUGE.height() + 6;
				yield box(-hugeW, hugeW + largeW + 2, -1, height, -hugeW, hugeW + 2);
			}
			case TEMPLE -> {
				int w = spec.size().width() + 4;
				int d = spec.size().depth() + 4;
				yield box(-1, w, -1, spec.size().height() + 1, -1, d);
			}
			case DUNGEON -> {
				int r = Math.max(3, spec.size().width() / 3);
				yield box(-r - 1, r + 1, -1, 5, -r - 1, r + 1);
			}
			case RUIN -> box(-1, spec.size().width(), -1, spec.size().height() + 1, -1, spec.size().depth() - 1);
			case ARENA -> {
				int r = spec.size().width() / 2;
				yield box(-r - 1, r + 1, -1, 4, -r - 1, r + 1);
			}
			case WELL -> box(-1, 1, -1, 3, -1, 1);
		};
	}

	private static BuildFootprint box(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
		return new BuildFootprint(minX, maxX, minY, maxY, minZ, maxZ);
	}
}
