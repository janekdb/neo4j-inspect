/*
 * Copyright (c) 2002-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.bolt.v4;

import org.junit.jupiter.api.Test;

import org.neo4j.bolt.BoltChannel;
import org.neo4j.bolt.dbapi.CustomBookmarkFormatParser;
import org.neo4j.bolt.packstream.Neo4jPack;
import org.neo4j.bolt.packstream.Neo4jPackV2;
import org.neo4j.bolt.runtime.BoltConnection;
import org.neo4j.bolt.runtime.statemachine.BoltStateMachineFactory;
import org.neo4j.bolt.v4.messaging.BoltRequestMessageReaderV4;
import org.neo4j.bolt.v4.runtime.bookmarking.BookmarksParserV4;
import org.neo4j.kernel.database.TestDatabaseIdRepository;
import org.neo4j.logging.internal.NullLogService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BoltProtocolV4Test
{
    private final BookmarksParserV4 bookmarksParser = new BookmarksParserV4( new TestDatabaseIdRepository(), CustomBookmarkFormatParser.DEFAULT );

    @Test
    void shouldCreatePackForBoltV4() throws Throwable
    {
        BoltProtocolV4 protocolV4 =
                new BoltProtocolV4( mock( BoltChannel.class ), ( ch, st ) -> mock( BoltConnection.class ), mock( BoltStateMachineFactory.class ),
                        bookmarksParser, NullLogService.getInstance() );

        assertThat( protocolV4.createPack() ).isInstanceOf( Neo4jPackV2.class );
    }

    @Test
    void shouldVersionReturnBoltV4() throws Throwable
    {
        BoltProtocolV4 protocolV4 =
                new BoltProtocolV4( mock( BoltChannel.class ), ( ch, st ) -> mock( BoltConnection.class ), mock( BoltStateMachineFactory.class ),
                        bookmarksParser, NullLogService.getInstance() );

        assertThat( protocolV4.version() ).isEqualTo( 4L );
    }

    @Test
    void shouldCreateMessageReaderForBoltV4() throws Throwable
    {
        BoltProtocolV4 protocolV4 =
                new BoltProtocolV4( mock( BoltChannel.class ), ( ch, st ) -> mock( BoltConnection.class ), mock( BoltStateMachineFactory.class ),
                        bookmarksParser, NullLogService.getInstance() );

        assertThat( protocolV4.createMessageReader( mock( BoltChannel.class ), mock( Neo4jPack.class ), mock( BoltConnection.class ), bookmarksParser,
                NullLogService.getInstance() ) ).isInstanceOf( BoltRequestMessageReaderV4.class );
    }
}
