package git.immutabled.sulf.api

import git.immutabled.connector.Connector

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 19:37
 */
class PlatformTransport(
    ) {

    private lateinit var connector: Connector

    fun setup() {
    }

    fun getConnector(): Connector {
        return this.connector
    }



}