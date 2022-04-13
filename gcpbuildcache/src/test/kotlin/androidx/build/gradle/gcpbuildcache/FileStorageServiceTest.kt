package androidx.build.gradle.gcpbuildcache

import org.junit.Test

class FileStorageServiceTest {
    @Test
    fun testStoreBlob() {
        val storageService = FileSystemStorageService(
            projectId = PROJECT_ID,
            bucketName = BUCKET_NAME,
            isPush = true
        )
        storageService.use {
            val cacheKey = "test-store.txt"
            val contents = "The quick brown fox jumped over the lazy dog"
            val result = storageService.store(cacheKey, contents.toByteArray(Charsets.UTF_8))
            assert(result)
        }
    }

    @Test
    fun testLoadBlob() {
        val storageService = FileSystemStorageService(
            projectId = PROJECT_ID,
            bucketName = BUCKET_NAME,
            isPush = true
        )
        storageService.use {
            val cacheKey = "test-load.txt"
            val contents = "The quick brown fox jumped over the lazy dog"
            val bytes = contents.toByteArray(Charsets.UTF_8)
            storageService.store(cacheKey, bytes)
            val input = storageService.load(cacheKey)!!
            val result = String(input.readAllBytes(), Charsets.UTF_8)
            assert(result == contents)
        }
    }

    @Test
    fun testStoreBlob_noPushSupport() {
        val storageService = FileSystemStorageService(
            projectId = PROJECT_ID,
            bucketName = BUCKET_NAME,
            isPush = false
        )
        storageService.use {
            val cacheKey = "test-store.txt"
            val contents = "The quick brown fox jumped over the lazy dog"
            val result = storageService.store(cacheKey, contents.toByteArray(Charsets.UTF_8))
            assert(!result)
        }
    }

    companion object {
        // Project ID
        private const val PROJECT_ID = "androidx"

        // The Bucket Name
        private const val BUCKET_NAME = "cache"
    }
}