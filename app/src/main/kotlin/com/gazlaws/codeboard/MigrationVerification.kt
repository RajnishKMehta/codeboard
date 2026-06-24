package com.gazlaws.codeboard

/**
 * Dummy Kotlin class to verify that the project successfully compiles Kotlin code
 * as part of the modernization migration.
 */
class MigrationVerification {
    fun checkInteroperability() {
        val activity = MainActivity()
        println("Successfully interacted with Java class: ${activity.javaClass.simpleName}")
    }
}
