package com.rijks.museum

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.crossfade

class ImageLoaderBuilder : SingletonImageLoader.Factory {
    companion object {
        private const val MEMORY_CACHE_SIZE_PERCENT = 0.25
        private const val DISK_CACHE_SIZE_PERCENT = 0.02
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .crossfade(true)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, MEMORY_CACHE_SIZE_PERCENT)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(DISK_CACHE_SIZE_PERCENT)
                    .build()
            }
            .build()
}
